package org.docurest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentRecord<T> implements Document<T> {
    private int storeVersion;
    private String docType;
    private int docTypeVersion;
    private String docId;
    private T content;
}
