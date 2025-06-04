package org.fran.api.controllers;

import io.javalin.http.Handler;
import org.fran.dtos.CountryCostItemDto;
import org.fran.dtos.UpdateCountryCostItemDto;
import org.fran.services.CostMatrixService;

public class CostMatrixController {

    private CostMatrixService costMatrixService;

    public CostMatrixController(CostMatrixService costMatrixService) {
        this.costMatrixService = costMatrixService;
    }
    public Handler createCost = ctx -> {
        costMatrixService.createCost(ctx.bodyAsClass(CountryCostItemDto.class));
        ctx.status(201);
    };

    public Handler getCostByCountryCode = ctx -> {
        String code = ctx.pathParam("id");
        CountryCostItemDto result = costMatrixService.getCost(code);
        ctx.json(result);
        ctx.status(200);
    };

    public Handler getAllCosts = ctx -> {

        ctx.json(costMatrixService.getAllCosts());
        ctx.status(200);
    };

    public Handler updateCostMatrixItem = ctx -> {
        String id = ctx.pathParam("id");
        UpdateCountryCostItemDto request = ctx.bodyAsClass(UpdateCountryCostItemDto.class);

        CountryCostItemDto toUpdate = new CountryCostItemDto(id, request.getNewValue());
        costMatrixService.updateCost(toUpdate);
        ctx.status(204);
    };

    public Handler deleteCostMatrixItem = ctx -> {
        String id = ctx.pathParam("id");
        costMatrixService.deleteCost(id);
        ctx.status(204);
    };
}
