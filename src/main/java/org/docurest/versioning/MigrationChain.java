package org.docurest.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.docurest.Document;
import org.docurest.DocumentRecord;
import org.docurest.infra.Validator;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@Slf4j
public class MigrationChain<T> {

    private final Version<T, ?> versionChain;

    @Autowired
    Validator validator;

    @SneakyThrows
    public Document<T> modernize(Document<String> rawDocument) {
        int typeVersionNumber = rawDocument.getDocTypeVersion();
        try {
            final Class<?> versionClass = versionChain.getVersionByNumber(typeVersionNumber).versionClass();
            Object content = new ObjectMapper().readValue(rawDocument.getContent(), versionClass);

            validator.assertValid(content);

            return versionChain.migrate(new DocumentRecord<>(
                    rawDocument.getStoreVersion(),
                    rawDocument.getDocType(),
                    rawDocument.getDocTypeVersion(),
                    rawDocument.getDocId(),
                    content
            ));
        } catch (Exception e) {
            log.error("Validation failed for {} #{}", rawDocument.getDocType(), rawDocument.getDocId());
            System.out.println(getClass().getName());
            throw e;
        }
    }
}
