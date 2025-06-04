package org.fran.api.controllers;

import org.fran.dtos.LookupResponseDto;
import org.fran.services.BinLookupService;
import io.javalin.http.Handler;

public class BinLookupController {
    private BinLookupService binLookupService;

    public BinLookupController(BinLookupService binLookupService) {
        this.binLookupService = binLookupService;
    }

    public Handler lookup = ctx -> {
        LookupResponseDto responseDto = binLookupService.look(ctx.pathParam("cardNumber"));
        ctx.json(responseDto).status(200);
    };
}
