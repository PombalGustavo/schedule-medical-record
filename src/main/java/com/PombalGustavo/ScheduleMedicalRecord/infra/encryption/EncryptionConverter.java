package com.PombalGustavo.ScheduleMedicalRecord.infra.encryption;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {


    @Override
    public String convertToDatabaseColumn(String s) {
        return "";
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return "";
    }
}
