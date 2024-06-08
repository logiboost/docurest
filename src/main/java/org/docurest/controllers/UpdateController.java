package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.Document;
import org.docurest.UpdateActionHandler;
import org.docurest.infra.InfraAware;
import org.docurest.infra.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class UpdateController<S, A> extends InfraAware {

    @Autowired
    Validator validator;

    private final Class<S> docClass;

    @PostMapping
    public Optional<Document<S>> updateById(@PathVariable String id, @RequestBody A action) {
        final var documentOpt = infra.findById(docClass, id);

        final UpdateActionHandler<S, A> actionHandler = (UpdateActionHandler<S, A>)infra.getActionHandler(action.getClass());

        return documentOpt.map(document -> {
                final var newDoc = actionHandler.execute(action, document);
                validator.assertValid(newDoc.getContent());
                infra.upsert(docClass, newDoc);
                return newDoc;
        });
    }
}
