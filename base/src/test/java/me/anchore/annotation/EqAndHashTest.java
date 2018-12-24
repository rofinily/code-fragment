package me.anchore.annotation;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author anchore
 * @date 2018/10/10
 */
public class EqAndHashTest {

    @Test
    public void testEqualsAndHashCode() {
        A a1 = new A(1, -1, '2');
        A a2 = new A(1, -2, '3');
        A a3 = new A(2, -1, '2');

        Assert.assertEquals(a1.equals(a2), EqAndHashBuilder.equals(a1, a2));
        Assert.assertEquals(a1.equals(a3), EqAndHashBuilder.equals(a1, a3));

        Assert.assertEquals(a1.hashCode(), EqAndHashBuilder.hashCode(a1));
        Assert.assertEquals(a2.hashCode(), EqAndHashBuilder.hashCode(a2));
        Assert.assertEquals(a3.hashCode(), EqAndHashBuilder.hashCode(a3));


        B b1 = new B(1, -1, '2', 10, "asdf");
        B b2 = new B(1, -2, '3', 10, "asdf");
        B b3 = new B(1, -9, '2', 100, "asdf");

        Assert.assertEquals(b1.equals(b2), EqAndHashBuilder.equals(b1, b2));
        Assert.assertEquals(b1.equals(b3), EqAndHashBuilder.equals(b1, b3));

        Assert.assertEquals(b1.hashCode(), EqAndHashBuilder.hashCode(b1));
        Assert.assertEquals(b2.hashCode(), EqAndHashBuilder.hashCode(b2));
        Assert.assertEquals(b3.hashCode(), EqAndHashBuilder.hashCode(b3));
    }

    class A {
        long l;

        @IgnoreEqAndHash
        double d;

        @IgnoreEqAndHash
        char c;

        public A(long l, double d, char c) {
            this.l = l;
            this.d = d;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            A a = (A) o;

            return l == a.l;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[]{l});
        }
    }

    class B extends A {
        int i;

        String s;

        public B(long l, double d, char c, int i, String s) {
            super(l, d, c);
            this.i = i;
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }

            B b = (B) o;

            if (i != b.i) {
                return false;
            }
            return s != null ? s.equals(b.s) : b.s == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + i;
            result = 31 * result + (s != null ? s.hashCode() : 0);
            return result;
        }
    }
}