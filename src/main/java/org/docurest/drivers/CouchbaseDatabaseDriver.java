package org.docurest.drivers;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.UpsertOptions;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import lombok.RequiredArgsConstructor;
import org.docurest.infra.DatabaseDriver;
import org.docurest.Document;
import org.docurest.queries.filter.And;
import org.docurest.queries.filter.Eq;
import org.docurest.queries.filter.Filter;
import org.docurest.queries.types.Field;
import org.docurest.queries.types.StringValue;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class CouchbaseDatabaseDriver<T> implements DatabaseDriver<T> {

    private final Bucket couchbaseBucket;
    private final String docType;

    @Override
    public void upsert(Stream<? extends Document<T>> documentStream) {
        Collection collection = couchbaseBucket.defaultCollection();
        documentStream
                .forEach(doc -> collection.upsert(doc.getDocId(), doc, UpsertOptions.upsertOptions()));
    }

    @Override
    public Stream<Document<String>> select(Filter filter) {
        final var withTypeFilter = And.builder()
                .leftMember(Eq.builder()
                        .leftMember(new Field("docType"))
                        .rightMember(new StringValue(docType))
                        .build())
                .rightMember(filter)
                .build();

        String query = String.format("SELECT META().id AS docId, _default.* FROM `_default` WHERE %s", withTypeFilter.toCouchbaseQuery());
        System.out.println("Final query: " + query);
        QueryResult result = couchbaseBucket.defaultScope().query(query, QueryOptions.queryOptions());
        return result.rowsAsObject().stream().map(CouchbaseDocument::new);
    }

    @Override
    public Optional<Document<String>> findById(String id) {
        Collection collection = couchbaseBucket.defaultCollection();
        GetResult result = collection.get(id);
        return Optional.of(new CouchbaseDocument(result.contentAsObject()));
    }

}
