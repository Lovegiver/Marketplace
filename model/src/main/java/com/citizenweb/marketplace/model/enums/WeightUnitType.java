package com.citizenweb.marketplace.model.enums;

import com.citizenweb.marketplace.model.model.Unit;

public enum WeightUnitType implements Unit {

    KG ("Kilogram(s)"),
    GR ("Grams"),
    MG ("Milligrams")
    ;

    private final String unit;

    WeightUnitType(String unit) {
        this.unit = unit;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

}
