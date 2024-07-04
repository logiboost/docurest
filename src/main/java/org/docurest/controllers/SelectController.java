package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.infra.InfraAware;
import org.docurest.Document;
import org.docurest.queries.filter.Filter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public abstract class SelectController<S> extends InfraAware {

    private final Class<S> docClass;

    @PostMapping
    public List<Document<S>> query(@RequestBody Filter filter) {
        return infra.select(docClass, filter).collect(Collectors.toList());
    }
}
