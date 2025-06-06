package org.fran.api.configs;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import org.fran.dataAccess.InMemoryStorage;
import org.fran.dataAccess.interfaces.IStorage;
import org.fran.exeptions.*;
import org.fran.services.BinLookupService;
import org.fran.api.controllers.BinLookupController;
import org.fran.api.controllers.CostMatrixController;
import org.fran.services.CostMatrixService;

import java.io.IOException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinConfig {
    public static Javalin configureJavalin() {
    // Initialize dependencies
        IStorage storage = new InMemoryStorage();
        BinLookupService binLookupService = new BinLookupService(storage);
        CostMatrixService costMatrixService = new CostMatrixService(storage);

        // Create controller instance
        BinLookupController binLookupController = new BinLookupController(binLookupService);
        CostMatrixController costMatrixController = new CostMatrixController(costMatrixService);

        // Configure Javalin
            return Javalin.create(config -> {
            config.plugins.register(new RouteOverviewPlugin("/routes"));
            config.plugins.enableDevLogging();
        }).routes(() -> {
            // Route definitions
            path("/api/clearingcost", () -> {
                get("/{cardNumber}", binLookupController.lookup);
            });
            path("/api/costmatrices", () -> {
                post(costMatrixController.createCost);
                get(costMatrixController.getAllCosts);
                path("/{id}", () -> {
                    get(costMatrixController.getCostByCountryCode);
                    put(costMatrixController.updateCostMatrixItem);
                    delete(costMatrixController.deleteCostMatrixItem);
                });
            });
        }).exception(BinNotFoundExeption.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result(e.getMessage());
        }).exception(InvalidCreditCardException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result(e.getMessage());
        }).exception(CountryAlreadyExistsException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result(e.getMessage());
        }).exception(InvalidCountryException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result(e.getMessage());
        }).exception(TooManyRequestsExeption.class, (e, ctx) -> {
            ctx.status(503);
            ctx.result("The maximum number of requests exceeded");
        }).exception(IOException.class, (e, ctx) -> {
            ctx.status(500);
            ctx.result("Unexpected error");
        }).exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.result("Unexpected error");
        });
    }
}
