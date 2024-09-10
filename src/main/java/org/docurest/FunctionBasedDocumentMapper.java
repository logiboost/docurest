package org.docurest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FunctionBasedDocumentMapper<T, U> extends DocumentMapper<U, T> {

    private final Mapper<U, T> contentMapper;

    public T content(Document<U> document) {
        return contentMapper.map(document.getContent());
    }

}
