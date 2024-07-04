package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.infra.InfraAware;
import org.docurest.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class ResolveController<S> extends InfraAware {

    private final Class<S> docClass;

    @GetMapping
    public Optional<Document<S>> findById(@PathVariable String id) {
        return infra.findById(docClass, id);
    }
}
