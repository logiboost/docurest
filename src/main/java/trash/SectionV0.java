package trash;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextSectionV0.class, name = "Text"),
        @JsonSubTypes.Type(value = PointListSectionV0.class, name = "PointList")
})
@Data
sealed public abstract class SectionV0 permits TextSectionV0, PointListSectionV0 {

    @NotNull
    private String title;

}
