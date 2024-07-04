package org.docurest;

public abstract class UpdateActionHandler<D, ACT> extends ActionHandler<D, ACT> {

    protected UpdateActionHandler(Infra infra, Class<ACT> actionClass) {
        super(infra, actionClass);
    }

    public abstract Document<D> execute(ACT action, Document<D> existingDocument);
}
