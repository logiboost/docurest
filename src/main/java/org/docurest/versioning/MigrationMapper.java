package org.docurest.versioning;

public interface MigrationMapper<PV, CV> {

    CV upgrade(PV document);

}
