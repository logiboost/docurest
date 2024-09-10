package org.docurest.queries;

import jakarta.validation.constraints.NotNull;

public record Eq(@NotNull Leaf leftMember, @NotNull Leaf rightMember) implements Filter {

    @Override
    public String toCouchbaseQuery() {
        return String.format("%s = %s", leftMember.toCouchbaseQuery(), rightMember.toCouchbaseQuery());
    }
}
