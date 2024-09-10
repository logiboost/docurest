package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.infra.InfraAware;
import org.docurest.Document;
import org.docurest.queries.BooleanValue;
import org.docurest.queries.Filter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public abstract class SelectController<S> extends InfraAware {

    private final Class<S> docClass;

    @PostMapping
    public List<Document<S>> query(@RequestBody(required = false) Filter filter) {
        return infra.select(docClass,
                Objects.requireNonNullElseGet(filter, () -> new BooleanValue(true)))
                .collect(Collectors.toList());
    }
}
