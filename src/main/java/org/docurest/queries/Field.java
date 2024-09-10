package org.docurest.queries;

import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public record Field(@NotNull String path) implements Leaf {

    @Override
    public String toCouchbaseQuery() {
        return Arrays.stream(path.split(Pattern.quote(".")))
                .map(name -> "`" + name + "`")
                .collect(Collectors.joining("."));
    }
}
