package org.docurest.queries.filter;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class And implements Filter {

    @NonNull
    private Filter leftMember;

    @NonNull
    private Filter rightMember;

    @Override
    public String toCouchbaseQuery() {
        return String.format("%s AND %s", leftMember.toCouchbaseQuery(), rightMember.toCouchbaseQuery());
    }
}
