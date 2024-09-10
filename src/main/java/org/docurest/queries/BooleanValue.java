package org.docurest.queries;

import jakarta.validation.constraints.NotNull;

public record BooleanValue(@NotNull boolean value) implements Filter, Leaf {

    @Override
    public String toCouchbaseQuery() {
        return String.valueOf(value);
    }
}
