package me.anchore.ioc.impl;

import me.anchore.ioc.Bean;
import me.anchore.ioc.BeanScanner;
import me.anchore.ioc.DuplicateBeanException;
import me.anchore.log.Loggers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author anchore
 * @date 2019/2/22
 */
public class ClassPathBeanScanner implements BeanScanner {

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String JAR_FILE_SUFFIX = ".jar";

    @Override
    public Set<BeanInfo> scan(String packageName) {
        Set<BeanInfo> beanInfos = new HashSet<>();
        try {
            Enumeration<URL> urls = getClass().getClassLoader().getResources(packageName.replace('.', '/'));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("jar".equals(protocol)) {
                    beanInfos.addAll(ofJar(url.getFile()));
                } else if ("file".equals(protocol)) {
                    beanInfos.addAll(ofClassFile(new File(url.getFile()).getPath(), packageName));
                }
            }
        } catch (IOException e) {
            Loggers.getLogger().error(e);
        }
        return beanInfos;
    }

    private Set<BeanInfo> ofClassFile(String path, String packageName) throws IOException {
        return Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(s -> s.endsWith(CLASS_FILE_SUFFIX))
                .map(s -> s.substring(path.length() - packageName.length(), s.length() - CLASS_FILE_SUFFIX.length()))
                .map(s -> s.replace(File.separatorChar, '.'))
                .map(className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(c -> c.isAnnotationPresent(Bean.class))
                .map(Class::getName)
                .map(BeanInfo::ofClassName)
                .collect(Collectors.toSet());
    }

    private Set<BeanInfo> ofJar(String path) throws IOException {
        Set<BeanInfo> classes = new HashSet<>();
        String jarPath = path.substring(0, path.indexOf(JAR_FILE_SUFFIX + "!/") + JAR_FILE_SUFFIX.length());
        try (JarFile jar = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entry.isDirectory() || !entryName.endsWith(CLASS_FILE_SUFFIX)) {
                    continue;
                }
                try {
                    Class<?> c = Class.forName(entryName.replace('/', '.').substring(0, entryName.length() - CLASS_FILE_SUFFIX.length()));
                    Bean bean = c.getAnnotation(Bean.class);
                    if (bean != null) {
                        String beanId = bean.value();
                        if (!classes.add(new BeanInfo("".equals(beanId) ? c.getName() : beanId, c.getName()))) {
                            throw DuplicateBeanException.ofBeanId(beanId);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    Loggers.getLogger().error(e);
                }
            }
        }

        return classes;
    }
}