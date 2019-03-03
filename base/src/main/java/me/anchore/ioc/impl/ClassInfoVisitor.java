package me.anchore.ioc.impl;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Consumer;

/**
 * @author anchore
 * @date 2019/2/26
 */
public class ClassInfoVisitor extends ClassVisitor {

    private ClassInfo classInfo = new ClassInfo();

    private Consumer<ClassInfo> classInfoConsumer;

    ClassInfoVisitor(Consumer<ClassInfo> classInfoConsumer) {
        super(Opcodes.ASM5);
        this.classInfoConsumer = classInfoConsumer;
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

    @Override
    public void visitEnd() {
        classInfoConsumer.accept(classInfo);
        classInfo = new ClassInfo();
    }
}