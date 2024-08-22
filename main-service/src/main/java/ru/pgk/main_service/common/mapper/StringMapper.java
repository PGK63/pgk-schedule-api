package ru.pgk.main_service.common.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class StringMapper {

    @Named("emptyStringToNull")
    public String emptyStringToNull(String value) {
        return (value != null && value.trim().isEmpty()) ? null : value;
    }
}
