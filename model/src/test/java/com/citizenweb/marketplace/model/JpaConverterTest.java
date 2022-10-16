package com.citizenweb.marketplace.model;

import com.citizenweb.marketplace.model.enums.VolumeUnitType;
import com.citizenweb.marketplace.model.enums.WeightUnitType;
import com.citizenweb.marketplace.model.jpa_converter.VolumeUnitConverter;
import com.citizenweb.marketplace.model.jpa_converter.WeightUnitConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class JpaConverterTest {

    private final VolumeUnitConverter volumeUnitConverter = new VolumeUnitConverter();
    private final WeightUnitConverter weightUnitConverter = new WeightUnitConverter();

    @Test
    public void convertVolume() {
        List<VolumeUnitType> volumeTypes = Arrays.asList(VolumeUnitType.L, VolumeUnitType.CL, VolumeUnitType.ML);
        volumeTypes.forEach(type -> {
            String value = volumeUnitConverter.convertToDatabaseColumn(type);
            Assertions.assertNotNull(value);
            VolumeUnitType unitType = volumeUnitConverter.convertToEntityAttribute(value);
            Assertions.assertNotNull(unitType);
            Assertions.assertEquals(type, unitType);
        });
    }

    @Test
    public void convertWeight() {
        List<WeightUnitType> weightTypes = Arrays.asList(WeightUnitType.KG, WeightUnitType.GR, WeightUnitType.MG);
        weightTypes.forEach(type -> {
            String value = weightUnitConverter.convertToDatabaseColumn(type);
            Assertions.assertNotNull(value);
            WeightUnitType unitType = weightUnitConverter.convertToEntityAttribute(value);
            Assertions.assertNotNull(unitType);
            Assertions.assertEquals(type, unitType);
        });
    }

}
