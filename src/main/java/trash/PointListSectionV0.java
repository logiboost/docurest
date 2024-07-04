package trash;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("PointList")
final public class PointListSectionV0 extends SectionV0 {

    @NotNull
    private List<String> points;

}
