package me.anchore.ioc.impl;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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

    private String desc;

    public AnnotationInfoVisitor(String desc, Consumer<AnnotationInfo> annotationConsumer) {
        super(Opcodes.ASM5);
        this.desc = desc;
        this.annotationInfo = new AnnotationInfo(ReflectUtil.toTypeName(desc));
        this.annotationConsumer = annotationConsumer;
    }

    private void setDefaultValues() {
        String path = desc.substring("L".length(), desc.length() - ";".length()) + ClassPathBeanScanner.CLASS_FILE_SUFFIX;
        ClassPathBeanScanner.scan(path, new ClassVisitor(Opcodes.ASM5) {
            @Override
            public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
                if (annotationInfo.containsProperty(methodName)) {
                    return null;
                }
                return new MethodVisitor(Opcodes.ASM5) {
                    @Override
                    public AnnotationVisitor visitAnnotationDefault() {
                        return new AnnotationVisitor(Opcodes.ASM5) {
                            @Override
                            public void visit(String name, Object value) {
                                annotationInfo.setProperty(methodName, value);
                            }

                            @Override
                            public void visitEnum(String name, String desc, String value) {
                                annotationInfo.setProperty(methodName, value);
                            }

                            @Override
                            public AnnotationVisitor visitArray(String name) {
                                return new AnnotationVisitor(Opcodes.ASM5) {
                                    List<Object> values = new ArrayList<>();

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
                                            annotationInfo.setProperty(methodName, values);
                                        }
                                    }
                                };
                            }
                        };
                    }
                };
            }
        });
    }

    @Override
    public void visit(String name, Object value) {
        if (value != null) {
            annotationInfo.setProperty(name, value);
        }
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        if (value != null) {
            annotationInfo.setProperty(name, value);
        }
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        return new AnnotationVisitor(Opcodes.ASM5) {
            List<Object> values = new ArrayList<>();

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
                    annotationInfo.setProperty(name, values);
                }
            }
        };
    }

    @Override
    public void visitEnd() {
        setDefaultValues();
        annotationConsumer.accept(annotationInfo);
    }
}