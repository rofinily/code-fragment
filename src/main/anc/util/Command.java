package anc.util;

import java.io.File;
import java.io.IOException;

public class Command {
    private File dir;
    private String[] cmdArray;
    private String[] env;

    private Command(File dir, String cmd, String[] env) {
        this(dir, new String[]{cmd}, env);
    }

    private Command(File dir, String[] cmdArray, String[] env) {
        this.dir = dir;
        this.cmdArray = cmdArray;
        this.env = env;
    }

    public static Command of(File dir, String cmd, String[] env) {
        return new Command(dir, cmd, env);
    }

    public static Command of(File dir, String[] cmdArray, String[] env) {
        return new Command(dir, cmdArray, env);
    }

    public static Command of(String cmd) {
        return of(null, cmd, null);
    }

    public Process exec() throws IOException {
        return Runtime.getRuntime().exec(cmdArray, env, dir);
    }
}