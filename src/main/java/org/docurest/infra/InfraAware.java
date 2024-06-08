package org.docurest.infra;

import org.docurest.Infra;
import org.springframework.beans.factory.annotation.Autowired;

public class InfraAware {

    @Autowired
    protected Infra infra;

}
