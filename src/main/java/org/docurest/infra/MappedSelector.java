package org.docurest.infra;

import lombok.RequiredArgsConstructor;
import org.docurest.Document;
import org.docurest.RegisteredDocumentMapper;
import org.docurest.queries.Filter;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
class MappedSelector<I, O> implements Selector<O> {

    private final Selector<I> input;
    private final RegisteredDocumentMapper<I, O> mapper;

    @Override
    public Optional<Document<O>> findById(String id) {
        return input.findById(id).map(mapper::map);
    }

    @Override
    public Stream<Document<O>> select(Filter filter) {
        return input.select(filter).map(mapper::map);
    }
}
