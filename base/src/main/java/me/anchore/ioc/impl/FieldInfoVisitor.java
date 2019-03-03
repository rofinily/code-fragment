package me.anchore.ioc.impl;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Consumer;

/**
 * @author anchore
 * @date 2019/2/26
 */
public class FieldInfoVisitor extends FieldVisitor {

    private FieldInfo fieldInfo;

    private Consumer<FieldInfo> fieldInfoConsumer;

    public FieldInfoVisitor(String name, String desc, Consumer<FieldInfo> fieldInfoConsumer) {
        super(Opcodes.ASM5);
        this.fieldInfo = new FieldInfo(name, ReflectUtil.toTypeName(desc));
        this.fieldInfoConsumer = fieldInfoConsumer;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return new AnnotationInfoVisitor(desc, fieldInfo::addAnnotationInfo);
    }

    @Override
    public void visitEnd() {
        fieldInfoConsumer.accept(fieldInfo);
    }
}