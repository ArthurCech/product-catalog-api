package com.github.arthurcech.productcatalog.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String field;
    private final String message;

    public FieldMessage(
            String field,
            String message
    ) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
