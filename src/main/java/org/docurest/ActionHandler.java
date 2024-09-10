package org.docurest;

import lombok.Getter;

public abstract class ActionHandler<ACT> {

    @Getter
    protected final Class<ACT> actionClass;

    protected final Infra infra;

    protected ActionHandler(Infra infra, Class<ACT> actionClass) {
        this.actionClass = actionClass;
        this.infra = infra;

        infra.getBaseInfra().registerActionHandler(this);
    }
}
