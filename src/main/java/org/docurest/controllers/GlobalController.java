package org.docurest.controllers;

import lombok.RequiredArgsConstructor;
import org.docurest.GlobalActionHandler;
import org.docurest.infra.InfraAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public abstract class GlobalController<A> extends InfraAware {
    @PostMapping
    public void execute(@RequestBody A action) {
        final GlobalActionHandler<A> actionHandler = (GlobalActionHandler<A>)infra.getActionHandler(action.getClass());
        actionHandler.execute(action);
    }
}
