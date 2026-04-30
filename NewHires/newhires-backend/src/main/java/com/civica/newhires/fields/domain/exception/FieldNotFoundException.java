
package com.civica.newhires.fields.domain.exception;

import java.util.UUID;

public class FieldNotFoundException extends RuntimeException {
    public FieldNotFoundException(UUID id) {
        super("Campo no encontrado: " + id);
    }
}