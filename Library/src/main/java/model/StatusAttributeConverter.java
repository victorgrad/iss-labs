package model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusAttributeConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status attribute) {
        switch (attribute) {
            case USER -> {
                return "user";
            }
            case LIBRARIAN -> {
                return "librarian";
            }
            case ADMIN -> {
                return "admin";
            }
        }
        return "";
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "user" -> {
                return Status.USER;
            }
            case "librarian" -> {
                return Status.LIBRARIAN;
            }
            case "admin" -> {
                return Status.ADMIN;
            }
        }
        return Status.ERROR;
    }
}
