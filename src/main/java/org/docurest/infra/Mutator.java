package org.docurest.infra;

import org.docurest.Document;

import java.util.stream.Stream;

public interface Mutator<T> {
    void upsert(Stream<? extends Document<T>> documentStream);
}
