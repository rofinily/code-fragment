package me.anchore.draft.annoproc;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import java.util.Set;

/**
 * @author anchore
 * @date 2019/6/14
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("me.anchore.draft.annoproc.A")
public class AP extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            annotation.getSimpleName();
        }
        processingEnv.getMessager().printMessage(Kind.NOTE, annotations.toString());
        processingEnv.getMessager().printMessage(Kind.NOTE, roundEnv.getElementsAnnotatedWith(A.class).toString());
        return false;
    }
}

