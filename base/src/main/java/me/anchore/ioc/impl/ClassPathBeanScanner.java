package me.anchore.ioc.impl;

import jdk.internal.org.objectweb.asm.ClassReader;
import me.anchore.ioc.Bean;
import me.anchore.ioc.BeanException;
import me.anchore.ioc.BeanScanner;
import me.anchore.ioc.DuplicateBeanException;
import me.anchore.log.Loggers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
                    beanInfos.addAll(ofJar(new URL(url.getFile()).getPath()));
                } else if ("file".equals(protocol)) {
                    beanInfos.addAll(ofClassFile(new File(url.getFile()).getPath()));
                }
            }
        } catch (IOException e) {
            Loggers.getLogger().error(e);
        }
        return beanInfos;
    }

    private Set<BeanInfo> ofClassFile(String path) throws IOException {
        return Files.walk(new File(path).toPath())
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(s -> s.endsWith(CLASS_FILE_SUFFIX))
                .map(p -> {
                    try {
                        return getClassInfoInClassFile(p);
                    } catch (IOException e) {
                        throw new BeanException(e);
                    }
                })
                .reduce(new HashSet<>(), (beanInfos, classInfo) -> {
                    addBeanInfo(classInfo, beanInfos);
                    return beanInfos;
                }, (c1, c2) -> {
                    c1.addAll(c2);
                    return c1;
                });

    }

    private Set<BeanInfo> ofJar(String path) throws IOException {
        Set<BeanInfo> beanInfos = new HashSet<>();
        String jarPath = path.substring(0, path.indexOf(JAR_FILE_SUFFIX + "!/") + JAR_FILE_SUFFIX.length());
        try (JarFile jar = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entry.isDirectory() || !entryName.endsWith(CLASS_FILE_SUFFIX)) {
                    continue;
                }
                ClassInfo classInfo = getClassInfoInJar(jar, entry);
                addBeanInfo(classInfo, beanInfos);
            }
        }

        return beanInfos;
    }

    private static ClassInfo getClassInfoInClassFile(String path) throws IOException {
        try (InputStream input = new FileInputStream(path)) {
            ClassInfoVisitor classInfoVisitor = new ClassInfoVisitor();
            new ClassReader(input).accept(classInfoVisitor, ClassReader.SKIP_CODE);
            return classInfoVisitor.getClassInfo();
        }
    }

    private ClassInfo getClassInfoInJar(JarFile jar, JarEntry entry) throws IOException {
        try (InputStream input = jar.getInputStream(entry)) {
            ClassInfoVisitor classInfoVisitor = new ClassInfoVisitor();
            new ClassReader(input).accept(classInfoVisitor, ClassReader.SKIP_CODE);
            return classInfoVisitor.getClassInfo();
        }
    }

    private void addBeanInfo(ClassInfo classInfo, Set<BeanInfo> beanInfos) {
        AnnotationInfo bean = classInfo.getAnnotationInfo(Bean.class);
        if (bean != null) {
            String beanId = bean.getProperty("value");
            if (!beanInfos.add(new BeanInfo("".equals(beanId) ? classInfo.getName() : beanId, classInfo))) {
                throw DuplicateBeanException.ofBeanId(beanId);
            }
        }
    }
}