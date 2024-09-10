package org.docurest.queries;

import jakarta.validation.constraints.NotNull;

public record Contains(@NotNull Leaf leftMember, @NotNull Leaf rightMember) implements Filter {

    @Override
    public String toCouchbaseQuery() {
        return String.format("CONTAINS(%s, %s)", leftMember.toCouchbaseQuery(), rightMember.toCouchbaseQuery());
    }
}
