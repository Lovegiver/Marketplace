package com.citizenweb.marketplace.model.jpa_converter;

import com.citizenweb.marketplace.model.enums.VolumeUnitType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class VolumeUnitConverter implements AttributeConverter<VolumeUnitType, String> {
    /**
     * @param unit
     * @return
     */
    @Override
    public String convertToDatabaseColumn(VolumeUnitType unit) {
        if (unit == null) { return null; }
        return unit.getUnit();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public VolumeUnitType convertToEntityAttribute(String s) {
        if (s == null) { return null; }
        return Stream.of(VolumeUnitType.values())
                .filter(volumeUnitType -> s.equals(volumeUnitType.getUnit()))
                .findFirst()
                .orElseThrow();
    }
}
