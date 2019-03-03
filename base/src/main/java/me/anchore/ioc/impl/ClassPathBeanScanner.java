package me.anchore.ioc.impl;

import me.anchore.ioc.Bean;
import me.anchore.ioc.BeanException;
import me.anchore.ioc.BeanScanner;
import me.anchore.ioc.DuplicateBeanException;
import me.anchore.log.Loggers;
import me.anchore.util.Strings;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

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

    public static final String CLASS_FILE_SUFFIX = ".class";

    private static final String JAR_FILE_SUFFIX = ".jar";

    @Override
    public Set<BeanInfo> scan(String packageName) {
        Set<BeanInfo> beanInfos = new HashSet<>();
        scan(packageName, new ClassInfoVisitor(classInfo -> addBeanInfo(classInfo, beanInfos)));
        return beanInfos;
    }

    static void scan(String path, ClassVisitor visitor) {
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("jar".equals(protocol)) {
                    visitJar(new URL(url.getFile()).getPath(), visitor);
                } else if ("file".equals(protocol)) {
                    visitClassFile(new File(url.getFile()).getPath(), visitor);
                }
            }
        } catch (IOException e) {
            Loggers.getLogger().error(e);
        }
    }

    private static void visitClassFile(String path, ClassVisitor visitor) throws IOException {
        Files.walk(new File(path).toPath())
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(s -> s.endsWith(CLASS_FILE_SUFFIX))
                .forEach(p -> {
                    try (InputStream input = new FileInputStream(p)) {
                        new ClassReader(input).accept(visitor, ClassReader.SKIP_CODE);
                    } catch (IOException e) {
                        throw new BeanException(e);
                    }
                });
    }

    private static void visitJar(String path, ClassVisitor visitor) throws IOException {
        String jarPath = path.substring(0, path.indexOf(JAR_FILE_SUFFIX + "!/") + JAR_FILE_SUFFIX.length());
        try (JarFile jar = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entry.isDirectory() || !path.endsWith(entryName)) {
                    continue;
                }
                try (InputStream input = jar.getInputStream(entry)) {
                    new ClassReader(input).accept(visitor, ClassReader.SKIP_CODE);
                }
            }
        }
    }

    private void addBeanInfo(ClassInfo classInfo, Set<BeanInfo> beanInfos) {
        AnnotationInfo bean = classInfo.getAnnotationInfo(Bean.class);
        if (bean != null) {
            String beanId = bean.getProperty("value");
            if (!beanInfos.add(new BeanInfo(Strings.isEmpty(beanId) ? classInfo.getName() : beanId, classInfo))) {
                throw DuplicateBeanException.ofBeanId(beanId);
            }
        }
    }
}