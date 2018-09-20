package me.anchore.util;

import java.io.IOException;
import java.nio.file.Path;

public class Command {
    private Path path;
    private String[] cmdArray;
    private String[] env;

    public static Command create() {
        return new Command();
    }

    public Command path(Path path) {
        this.path = path;
        return this;
    }

    public Command cmdArray(String... cmdArray) {
        this.cmdArray = cmdArray;
        return this;
    }

    public Command env(String... env) {
        this.env = env;
        return this;
    }

    public Process exec() throws IOException {
        return Runtime.getRuntime().exec(cmdArray, env, path != null ? path.toFile() : null);
    }

}