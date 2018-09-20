package me.anchore;

import org.hibernate.cfg.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author anchore
 */
public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(A.class);
        cfg.configure("hibernate-cfg.xml");
    }
}

@Entity
@Table
class A {
    @Column
    int al;
}