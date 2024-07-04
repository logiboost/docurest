package org.docurest.versioning;

import org.docurest.Document;
import org.docurest.DocumentRecord;

public record VersionPredecessor<CURR, THIS, NEXT> (
        int versionNumber,
        Class<THIS> versionClass,
        MigrationMapper<THIS, NEXT> mapper,
        Version<CURR, NEXT> nextVersion
)
        implements Version<CURR, THIS> {

    public Version<CURR, ?> getVersionByNumber(int number) {
        if (versionNumber==number)
            return this;

        return nextVersion.getVersionByNumber(number);
    }

    public Document<CURR> migrate(Document<Object> document) {
        if (document.getContent().getClass().equals(versionClass)) {
            return nextVersion.migrate(new DocumentRecord<>(
                    document.getStoreVersion(),
                    document.getDocType(),
                    nextVersion.versionNumber(),
                    document.getDocId(),
                    mapper.upgrade((THIS)document.getContent())
            ));
        }

        return nextVersion.migrate(document);
    }
}

