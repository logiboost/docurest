package org.docurest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docurest.infra.BaseInfra;
import org.docurest.infra.InfraContract;
import org.docurest.infra.Mutator;
import org.docurest.infra.Selector;
import org.docurest.queries.Filter;
import org.docurest.security.UserAuthDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class Infra implements InfraContract {

    public final static int VERSION = 0;

    private final BaseInfra baseInfra = new BaseInfra();

    @Override
    public <T> DocumentConfig<T> getConfig(Class<T> clazz) {
        return baseInfra.getConfig(clazz);
    }

    @Override
    public <T> Selector<T> getSelector(Class<T> clazz) {
        return baseInfra.getSelector(clazz);
    }

    @Override
    public <T> ActionHandler<?> getActionHandler(Class<T> clazz) {
        return baseInfra.getActionHandler(clazz);
    }

    @Override
    public Mutator<String> getStringImportMutator(Class<?> clazz) {
        return baseInfra.getStringImportMutator(clazz);
    }

    @Override
    public <T> Mutator<T> getMutator(Class<T> clazz) {
        return baseInfra.getMutator(clazz);
    }

    // Convenient methods
    // ------------------

    public <T> Stream<Document<T>> select(Class<T> clazz, Filter filter) {
        return getSelector(clazz).select(filter);
    }

    public <T> Function<String, Document<T>> resolver(Class<T> clazz) {
        return (id) -> getSelector(clazz).findById(id).orElseThrow();
    }

    public <T> Document<T> requireById(Class<T> clazz, String id) {
        return getSelector(clazz).findById(id).orElseThrow();
    }

    public <T> Optional<Document<T>> findById(Class<T> clazz, String id) {
        return getSelector(clazz).findById(id);
    }

    public <T> void upsert(Class<T> clazz, Document<T> document) {
        getMutator(clazz).upsert(Stream.of(document));
    }

    public <T> Consumer<Document<T>> upserter(Class<T> clazz) {
        return (Document<T> doc) -> upsert(clazz, doc);
    }

    public UserAuthDetails requireUserAuthDetails() {
        return BaseInfra.requireUserAuthDetails();
    }
}
