package org.docurest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.docurest.infra.DatabaseDriver;
import org.docurest.versioning.MigrationChain;

@Getter
@AllArgsConstructor
public class DocumentConfig<T> {

    private final Class<T> docClass;
    private final Class<? extends DocumentRoot<T>> rootClass;
    private final DatabaseDriver<T> driver;
    private final MigrationChain<T> migrationChain;

}
