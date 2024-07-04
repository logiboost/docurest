package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.Document;
import org.docurest.InsertActionHandler;
import org.docurest.infra.InfraAware;
import org.docurest.infra.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class InsertController<S, A> extends InfraAware {

    @Autowired
    Validator validator;

    private final Class<S> docClass;

    @PostMapping
    public Optional<Document<S>> insert(@RequestBody A action) {
        final InsertActionHandler<S, A> actionHandler = (InsertActionHandler<S, A>)infra.getActionHandler(action.getClass());
        final var newDoc = actionHandler.execute(action);
        validator.assertValid(newDoc.getContent());
        infra.upsert(docClass, newDoc);
        return Optional.of(newDoc);
    }
}
