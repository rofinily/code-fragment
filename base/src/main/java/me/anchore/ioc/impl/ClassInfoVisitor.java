package me.anchore.ioc.impl;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * @author anchore
 * @date 2019/2/26
 */
public class ClassInfoVisitor extends ClassVisitor {

    private ClassInfo classInfo = new ClassInfo();

    public ClassInfoVisitor() {
        super(Opcodes.ASM5);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        classInfo.setName(name.replace('/', '.'));
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return new AnnotationInfoVisitor(desc, classInfo::addAnnotationInfo);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return new FieldInfoVisitor(name, desc, classInfo::addFieldInfo);
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }
}