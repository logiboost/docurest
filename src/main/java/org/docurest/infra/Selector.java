package org.docurest.infra;

import org.docurest.Document;
import org.docurest.queries.Filter;

import java.util.Optional;
import java.util.stream.Stream;

public interface Selector<T> {

    Optional<Document<T>> findById(String id);
    Stream<Document<T>> select(Filter filter);
}
