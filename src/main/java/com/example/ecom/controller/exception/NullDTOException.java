package com.example.ecom.controller.exception;

import com.example.ecom.model.Entity;

public class NullDTOException extends BadRequestException{
    public NullDTOException(Entity entity) {
        super("Passed" + entity.name().toLowerCase() + "DTO is null", entity, "DTO_null");
    }
}
