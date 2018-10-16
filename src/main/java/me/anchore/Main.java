package me.anchore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;

/**
 * @author anchore
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Final aFinal = new Final();
        Field sfs = aFinal.getClass().getDeclaredField("sfs");
        Field fs = aFinal.getClass().getDeclaredField("fs");
//        sfs.setAccessible(true);
//        sfs.set(null, "not static final");
        fs.setAccessible(true);
        fs.set(aFinal, "not final");
    }
}

@Entity
@Table
class A {
    @Column
    int al;
}

class Final {
    private static final String sfs = "static final";

    private final String fs = "final";
}