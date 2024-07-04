package org.docurest.queries.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.docurest.queries.filter.Filter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BooleanValue implements Filter, Leaf {
    private boolean value;

    @Override
    public String toCouchbaseQuery() {
        return String.valueOf(value);
    }
}
