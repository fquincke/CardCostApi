package org.fran.api;
import io.javalin.Javalin;
import org.fran.api.configs.JavalinConfig;

public class Main {
    public static void main(String[] args) {
        Javalin app = JavalinConfig.configureJavalin();
        app.start(7070);
    }
}