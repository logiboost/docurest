package org.docurest.versioning;

import org.docurest.Document;
import org.docurest.DocumentRecord;

public record CurrentVersion<CURR> (int versionNumber, Class<CURR> versionClass) implements Version<CURR, CURR> {

    @Override
    public Version<CURR, ?> getVersionByNumber(int versionNumber2) {
        if (versionNumber2 != versionNumber) {
            throw new RuntimeException(versionNumber2 + " did not match any version of the chain");
        }
        return this;
    }

    @Override
    public Document<CURR> migrate(Document<Object> document) {
        return new DocumentRecord<>(
                document.getStoreVersion(),
                document.getDocType(),
                document.getDocTypeVersion(),
                document.getDocId(),
                (CURR)document.getContent()
        );
    }
}
