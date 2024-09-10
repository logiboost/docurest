package org.docurest;

public abstract class InsertActionHandler<D, ACT> extends ActionHandler<ACT> {

    protected InsertActionHandler(Infra infra, Class<ACT> actionClass) {
        super(infra, actionClass);
    }

    public abstract Document<D> execute(ACT action, String id);
}
