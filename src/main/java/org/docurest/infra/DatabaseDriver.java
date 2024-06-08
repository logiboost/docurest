package org.docurest.infra;

import org.docurest.infra.Mutator;
import org.docurest.infra.Selector;

public interface DatabaseDriver<T> extends Selector<String>, Mutator<T> {

}
