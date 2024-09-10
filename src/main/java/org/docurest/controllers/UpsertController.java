package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.Document;
import org.docurest.UpsertActionHandler;
import org.docurest.infra.InfraAware;
import org.docurest.infra.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class UpsertController<S, A> extends InfraAware {

    @Autowired
    Validator validator;

    private final Class<S> docClass;

    @PostMapping
    public Optional<Document<S>> updateById(@PathVariable String id, @RequestBody A action) {
        final var documentOpt = infra.findById(docClass, id);

        final UpsertActionHandler<S, A> actionHandler = (UpsertActionHandler<S, A>)infra.getActionHandler(action.getClass());

        final var newDoc = documentOpt
                .map(document -> actionHandler.execute(action, document))
                .orElseGet(() -> actionHandler.execute(action, id));

        validator.assertValid(newDoc.getContent());
        infra.upsert(docClass, newDoc);
        return Optional.of(newDoc);
    }
}
