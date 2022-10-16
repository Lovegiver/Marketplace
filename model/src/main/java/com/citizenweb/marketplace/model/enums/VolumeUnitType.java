package com.citizenweb.marketplace.model.enums;

import com.citizenweb.marketplace.model.model.Unit;

public enum VolumeUnitType implements Unit {

    L ("Litre(s)"),
    CL ("Centiliters"),
    ML ("Milliliters")
    ;

    private final String unit;

    VolumeUnitType(String unit) {
        this.unit = unit;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

}
