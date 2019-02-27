package me.anchore.ioc;

import me.anchore.ioc.impl.Beans;
import org.junit.Before;
import org.junit.Test;

/**
 * @author anchore
 * @date 2019/2/13
 */
public class BeanFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        Beans.get();
    }
}

@Bean
class D {
    @Inject
    A a;
    @Inject
    B b;
    @Inject
    C c;
}

@Bean
class B {
}

@Bean
class C {
    @Inject
    A c;
}

@Bean
class A {
    @Inject
    B b;
}

@Bean
class E {
    @Inject
    B b;
    @Inject
    D d;
}