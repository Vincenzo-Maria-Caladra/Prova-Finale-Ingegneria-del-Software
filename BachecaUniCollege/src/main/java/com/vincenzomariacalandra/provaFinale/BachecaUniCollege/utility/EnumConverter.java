package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EnumConverter implements AttributeConverter<Activity_credits, Double> {
 
    @Override
    public Double convertToDatabaseColumn(Activity_credits activity_credits) {
        if (activity_credits == null) {
            return null;
        }
        return activity_credits.getVal();
    }

    @Override
    public Activity_credits convertToEntityAttribute(Double val) {
        if (val == null) {
            return null;
        }

        return Stream.of(Activity_credits.values())
          .filter(c -> c.getVal() == val)
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
