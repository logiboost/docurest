package org.docurest.queries.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Field implements Leaf {

    private String path;

    @Override
    public String toCouchbaseQuery() {
        return Arrays.stream(path.split(Pattern.quote(".")))
                .map(name -> "`" + name + "`")
                .collect(Collectors.joining("."));
    }
}
