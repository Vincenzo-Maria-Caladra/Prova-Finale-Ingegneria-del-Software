package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EnumConverter implements AttributeConverter<ActivityCredits, Double> {
 
    @Override
    public Double convertToDatabaseColumn(ActivityCredits activity_credits) {
        if (activity_credits == null) {
            return null;
        }
        return activity_credits.getVal();
    }

    @Override
    public ActivityCredits convertToEntityAttribute(Double val) {
        if (val == null) {
            return null;
        }

        return Stream.of(ActivityCredits.values())
          .filter(c -> c.getVal() == val)
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
