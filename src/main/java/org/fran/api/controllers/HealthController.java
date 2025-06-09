package org.fran.api.controllers;

import io.javalin.http.Handler;

public class HealthController {
    public HealthController() {
    }

    public Handler healthCheck = ctx -> {
        ctx.result("OK");
        ctx.status(200);
    };
}
