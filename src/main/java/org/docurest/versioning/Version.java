package org.docurest.versioning;

import org.docurest.Document;

public sealed interface Version<CURR, THIS> permits CurrentVersion, VersionPredecessor {
    Version<CURR, ?> getVersionByNumber(int number);
    Class<THIS> versionClass();
    Document<CURR> migrate(Document<Object> document);
    int versionNumber();

}
