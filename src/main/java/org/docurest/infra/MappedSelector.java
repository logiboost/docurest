package org.docurest.infra;

import lombok.RequiredArgsConstructor;
import org.docurest.Document;
import org.docurest.DocumentMapper;
import org.docurest.queries.filter.Filter;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
class MappedSelector<I, O> implements Selector<O> {

    private final Selector<I> input;
    private final DocumentMapper<I, O> mapper;

    @Override
    public Optional<Document<O>> findById(String id) {
        return input.findById(id).map(mapper::mapDoc);
    }

    @Override
    public Stream<Document<O>> select(Filter filter) {
        return input.select(filter).map(mapper::mapDoc);
    }
}
