package org.docurest.drivers;

import com.couchbase.client.java.json.JsonObject;
import lombok.RequiredArgsConstructor;
import org.docurest.Document;

@RequiredArgsConstructor
public class CouchbaseDocument implements Document<String> {

    private final JsonObject object;

    @Override
    public String getDocId() {
        return object.getString("docId");
    }

    @Override
    public int getStoreVersion() {
        return object.getInt("storeVersion");
    }

    @Override
    public String getDocType() {
        return object.getString("docType");
    }

    @Override
    public int getDocTypeVersion() {
        return object.getInt("docTypeVersion");
    }

    @Override
    public String getContent() {
        return object.getObject("content").toString();
    }

}
