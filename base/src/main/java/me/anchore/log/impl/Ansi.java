package me.anchore.log.impl;

/**
 * todo ANSI Color
 *
 * @author anchore
 * @date 2018/12/30
 */
class Ansi {
    public enum Color {
        //
        BLACK(0),
        RED(1),
        GREEN(2),
        YELLOW(3),
        BLUE(4),
        MAGENTA(5),
        CYAN(6),
        WHITE(7),
        DEFAULT(9);

        private final int index;

        Color(int index) {
            this.index = index;
        }

        private int fg() {
            return index + 30;
        }

        private int bg() {
            return index + 40;
        }

        private int fgBright() {
            return index + 90;
        }

        private int bgBright() {
            return index + 100;
        }

        public String highlightForeground(String s) {
            return "\33[" + fg() + "m" + s + "\33[0m";
        }
    }
}