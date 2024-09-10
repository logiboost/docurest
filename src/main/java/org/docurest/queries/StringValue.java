package org.docurest.queries;

import jakarta.validation.constraints.NotNull;

public record StringValue(@NotNull String value) implements Leaf {

    @Override
    public String toCouchbaseQuery() {
        return String.format("'%s'", value);
    }
}
