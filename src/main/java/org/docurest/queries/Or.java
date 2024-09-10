package org.docurest.queries;

import jakarta.validation.constraints.NotNull;

public record Or(@NotNull Filter leftMember, @NotNull Filter rightMember) implements Filter {

    @Override
    public String toCouchbaseQuery() {
        return String.format("%s OR %s", leftMember.toCouchbaseQuery(), rightMember.toCouchbaseQuery());
    }
}
