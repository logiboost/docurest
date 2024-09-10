package org.docurest;

import lombok.Getter;

public abstract class RegisteredDocumentMapper<I, O> extends DocumentMapper<I, O> {

    protected final Infra infra;

    @Getter
    private final Class<I> from;

    @Getter
    private final Class<O> to;

    public RegisteredDocumentMapper(Infra infra, Class<I> from, Class<O> to) {
        this.infra = infra;
        this.from = from;
        this.to = to;

        infra.getBaseInfra().registerMapper(this);
    }
}
