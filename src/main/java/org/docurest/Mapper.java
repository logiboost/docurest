package org.docurest;

public interface Mapper<I, O> {
    O map(I i);
}
