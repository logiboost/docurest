package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.infra.InfraAware;
import org.docurest.Document;
import org.docurest.queries.Filter;
import org.docurest.queries.BooleanValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class ListController<S> extends InfraAware {

    private final Class<S> docClass;

    @GetMapping
    public List<Document<S>> list() {
        return alterStream(infra.select(docClass, getFilter()))
                .collect(Collectors.toList());
    }

    protected Stream<Document<S>> alterStream(Stream<Document<S>> stream) {
        return stream;
    }

    protected Filter getFilter() {
        return new BooleanValue(true);
    }
}
