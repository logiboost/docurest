package org.docurest;

import jakarta.validation.constraints.NotNull;

public interface Document<T> {

    @NotNull
    int getStoreVersion();

    @NotNull
    String getDocType();

    @NotNull
    int getDocTypeVersion();

    @NotNull
    String getDocId();

    @NotNull
    T getContent();

}
