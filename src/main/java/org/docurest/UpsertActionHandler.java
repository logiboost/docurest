package org.docurest;

import java.util.Optional;

public abstract class UpsertActionHandler<D, ACT> extends ActionHandler<ACT> {

    protected UpsertActionHandler(Infra infra, Class<ACT> actionClass) {
        super(infra, actionClass);
    }

    public abstract Document<D> execute(ACT action, Document<D> existingDocument);

    public abstract Document<D> execute(ACT action, String id);
}
