package me.anchore.ioc.impl;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author anchore
 * @date 2019/3/6
 */
class AnnotationArrayValueVisitor extends AnnotationVisitor {

    private Consumer<List<Object>> annotationArrayValueConsumer;

    private List<Object> values = new ArrayList<>();

    public AnnotationArrayValueVisitor(Consumer<List<Object>> annotationArrayValueConsumer) {
        super(Opcodes.ASM5);
        this.annotationArrayValueConsumer = annotationArrayValueConsumer;
    }

    @Override
    public void visit(String name, Object value) {
        if (value != null) {
            values.add(value);
        }
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        if (value != null) {
            values.add(value);
        }
    }

    @Override
    public void visitEnd() {
        if (!values.isEmpty()) {
            annotationArrayValueConsumer.accept(values);
        }
    }
}