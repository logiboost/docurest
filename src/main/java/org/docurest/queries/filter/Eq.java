package org.docurest.queries.filter;

import lombok.*;
import org.docurest.queries.types.Leaf;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Eq implements Filter {
    private Leaf leftMember;
    private Leaf rightMember;

    @Override
    public String toCouchbaseQuery() {
        return String.format("%s = %s", leftMember.toCouchbaseQuery(), rightMember.toCouchbaseQuery());
    }
}
