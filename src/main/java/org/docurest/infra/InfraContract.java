package org.docurest.infra;

import org.docurest.ActionHandler;
import org.docurest.DocumentConfig;

public interface InfraContract {

    <T> DocumentConfig<T> getConfig(Class<T> clazz);

    <T> Selector<T> getSelector(Class<T> clazz);

    <T> ActionHandler<?, ?> getActionHandler(Class<T> clazz);

    Mutator<String> getStringImportMutator(Class<?> clazz);

    <T> Mutator<T> getMutator(Class<T> clazz);

}
