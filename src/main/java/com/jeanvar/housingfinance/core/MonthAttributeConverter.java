package com.jeanvar.housingfinance.core;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Month;

@Converter(autoApply = true)
public class MonthAttributeConverter implements AttributeConverter<Month, Short> {
    @Override
    public Short convertToDatabaseColumn(Month month) {
        return (short) month.getValue();
    }

    @Override
    public Month convertToEntityAttribute(Short aShort) {
        return Month.of(aShort);
    }
}
