package org.docurest.queries.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.docurest.queries.types.BooleanValue;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = And.class, name = "AND"),
        @JsonSubTypes.Type(value = Eq.class, name = "EQ"),
        @JsonSubTypes.Type(value = BooleanValue.class, name = "BOOL")
})
public interface Filter {
    String toCouchbaseQuery();
}
