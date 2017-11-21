package anc.tool.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.IOException;

/**
 * @author anchore
 * @date 2017/11/13
 */
public class Demo {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get("com.fr.fs.FSModule");
        CtMethod m = clazz.getDeclaredMethod("deleteObsoleteJARs");
        m.setBody("return;");
        clazz.writeFile("d:/");
    }
}
