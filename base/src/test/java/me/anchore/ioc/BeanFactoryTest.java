package me.anchore.ioc;

import org.junit.Before;

/**
 * @author anchore
 * @date 2019/2/13
 */
public class BeanFactoryTest {

    @Before
    public void setUp() throws Exception {
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
}