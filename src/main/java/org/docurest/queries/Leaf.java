package org.docurest.queries;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        discriminatorProperty = "leafType",
        oneOf = { StringValue.class, Field.class, BooleanValue.class },
        discriminatorMapping = {
                @DiscriminatorMapping(value = "STRING", schema = StringValue.class),
                @DiscriminatorMapping(value = "FIELD", schema = Field.class),
                @DiscriminatorMapping(value = "BOOLEAN", schema = BooleanValue.class)
        }
)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "leafType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringValue.class, name = "STRING"),
        @JsonSubTypes.Type(value = Field.class, name = "FIELD"),
        @JsonSubTypes.Type(value = BooleanValue.class, name = "BOOLEAN")
})
public sealed interface Leaf permits BooleanValue, Field, StringValue {
    String toCouchbaseQuery();
}
