package org.docurest.infra;

import org.docurest.Document;
import org.docurest.queries.filter.Filter;
import org.docurest.versioning.MigrationChain;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Parse/migrate raw string documents and pass them to the next selector.
 */
public class StringParserSelector<T> implements Selector<T> {

    private final Selector<String> selector;
    private final MigrationChain<T> migrationChain;

    public StringParserSelector(Selector<String> databaseDriver, MigrationChain<T> migrationChain) {
        this.selector = databaseDriver;
        this.migrationChain = migrationChain;
    }

    @Override
    public Optional<Document<T>> findById(String id) {
        return selector.findById(id).map(migrationChain::modernize);
    }

    @Override
    public Stream<Document<T>> select(Filter filter) {
        return selector.select(filter).map(migrationChain::modernize);
    }

}
