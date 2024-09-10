package org.docurest.queries;

import jakarta.validation.constraints.NotNull;

public record And(@NotNull Filter leftMember, @NotNull Filter rightMember) implements Filter {

    @Override
    public String toCouchbaseQuery() {
        return String.format("%s AND %s", leftMember.toCouchbaseQuery(), rightMember.toCouchbaseQuery());
    }
}
