package org.docurest;

public abstract class DocumentMapper<I, O> implements Mapper<Document<I>, Document<O>> {

    public Document<O> map(Document<I> document) {
        return new DocumentRecord<>(
                storeVersion(document),
                docType(document),
                docTypeVersion(document),
                docId(document),
                content(document)
        );
    }

    protected int docTypeVersion(Document<I> document) {
        return document.getDocTypeVersion();
    }

    protected String docId(Document<I> document) {
        return document.getDocId();
    }

    protected String docType(Document<I> document) {
        return document.getDocType();
    }

    protected int storeVersion(Document<I> document) {
        return document.getStoreVersion();
    }

    protected abstract O content(Document<I> document);

}
