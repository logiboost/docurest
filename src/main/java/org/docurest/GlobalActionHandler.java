package org.docurest;

public abstract class GlobalActionHandler<ACT> extends ActionHandler<ACT> {

    protected GlobalActionHandler(Infra infra, Class<ACT> actionClass) {
        super(infra, actionClass);
    }

    public abstract void execute(ACT action);
}
