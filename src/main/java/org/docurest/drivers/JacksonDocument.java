package org.docurest.drivers;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.docurest.Document;

@RequiredArgsConstructor
public class JacksonDocument implements Document<String> {

    private final JsonNode node;

    @Override
    public int getStoreVersion() {
        return node.get("storeVersion").asInt();
    }

    @Override
    public String getDocType() {
        return node.get("docType").asText();
    }

    @Override
    public int getDocTypeVersion() {
        return node.get("docTypeVersion").asInt();
    }

    @Override
    public String getDocId() {
        return node.get("docId").asText();
    }

    @Override
    public String getContent() {
        return node.get("content").toString();
    }
}
