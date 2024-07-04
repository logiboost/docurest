package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.infra.InfraAware;
import org.docurest.Document;
import org.docurest.queries.filter.Filter;
import org.docurest.queries.types.BooleanValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class ListController<S> extends InfraAware {

    private final Class<S> docClass;

    @GetMapping
    public List<Document<S>> list() {
        return infra.select(docClass, getFilter())
                .collect(Collectors.toList());
    }

    protected Filter getFilter() {
        return new BooleanValue(true);
    }
}
