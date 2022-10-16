package com.citizenweb.marketplace.model.jpa_converter;

import com.citizenweb.marketplace.model.enums.WeightUnitType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class WeightUnitConverter implements AttributeConverter<WeightUnitType, String> {
    /**
     * @param unit
     * @return
     */
    @Override
    public String convertToDatabaseColumn(WeightUnitType unit) {
        if (unit == null) { return null; }
        return unit.getUnit();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public WeightUnitType convertToEntityAttribute(String s) {
        if (s == null) { return null; }
        return Stream.of(WeightUnitType.values())
                .filter(weightUnitType -> s.equals(weightUnitType.getUnit()))
                .findFirst()
                .orElseThrow();
    }
}
