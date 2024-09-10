package org.docurest.queries;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        discriminatorProperty = "filterType",
        oneOf = { And.class, Or.class, Eq.class, Contains.class, BooleanValue.class },
        discriminatorMapping = {
                @DiscriminatorMapping(value = "AND", schema = And.class),
                @DiscriminatorMapping(value = "OR", schema = Or.class),
                @DiscriminatorMapping(value = "EQ", schema = Eq.class),
                @DiscriminatorMapping(value = "CONTAINS", schema = Contains.class),
                @DiscriminatorMapping(value = "BOOLEAN", schema = BooleanValue.class)
        }
)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "filterType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = And.class, name = "AND"),
        @JsonSubTypes.Type(value = Or.class, name = "OR"),
        @JsonSubTypes.Type(value = Eq.class, name = "EQ"),
        @JsonSubTypes.Type(value = Contains.class, name = "CONTAINS"),
        @JsonSubTypes.Type(value = BooleanValue.class, name = "BOOLEAN")
})
public sealed interface Filter permits BooleanValue, And, Eq, Or, Contains {
    String toCouchbaseQuery();
}
