package me.anchore.ioc.impl;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author anchore
 * @date 2019/2/27
 */
public class AnnotationInfoVisitor extends AnnotationVisitor {

    private Consumer<AnnotationInfo> annotationConsumer;

    private AnnotationInfo annotationInfo;

    public AnnotationInfoVisitor(String desc, Consumer<AnnotationInfo> annotationConsumer) {
        super(Opcodes.ASM5);
        this.annotationInfo = new AnnotationInfo(ReflectUtil.toTypeName(desc));
        this.annotationConsumer = annotationConsumer;
    }

    @Override
    public void visit(String name, Object value) {
        annotationInfo.setProperty(name, value);
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        annotationInfo.setProperty(name, value);
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        return new AnnotationVisitor(Opcodes.ASM5) {
            List<Object> values = new ArrayList<>();

            @Override
            public void visit(String name, Object value) {
                values.add(value);
            }

            @Override
            public void visitEnum(String name, String desc, String value) {
                values.add(value);
            }

            @Override
            public void visitEnd() {
                annotationInfo.setProperty(name, values);
            }
        };
    }

    @Override
    public void visitEnd() {
        annotationConsumer.accept(annotationInfo);
    }

}