package org.docurest.infra;

import org.docurest.Document;
import org.docurest.versioning.MigrationChain;

import java.util.stream.Stream;

/**
 * Parse/migrate raw string documents and pass them to the next mutator.
 */
public class StringParserMutator<S> implements Mutator<String> {

    private final Mutator<S> mutator;
    private final MigrationChain<S> migrationChain;

    public StringParserMutator(Mutator<S> mutator, MigrationChain<S> migrationChain) {
        this.mutator = mutator;
        this.migrationChain = migrationChain;
    }

    @Override
    public void upsert(Stream<? extends Document<String>> documentStream) {
        mutator.upsert(documentStream
                .map(migrationChain::modernize));
    }

}
