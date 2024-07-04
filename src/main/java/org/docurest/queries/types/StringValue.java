package org.docurest.queries.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StringValue implements Leaf {

    private String value;

    @Override
    public String toCouchbaseQuery() {
        return String.format("'%s'", value);
    }
}
