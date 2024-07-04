package org.docurest.infra;

import lombok.extern.slf4j.Slf4j;
import org.docurest.DocumentMapper;

import java.util.*;

@Slf4j
public class SelectorProvider {

    private final List<DocumentMapper<?, ?>> mappers = new ArrayList<>();

    public void registerMapper(DocumentMapper<?, ?> mapper) {
        mappers.add(mapper);
    }

    private final Map<Class<?>, Selector<?>> selectors = new HashMap<>();

    private void deriveSelectors() {
        Set<Class<?>> availableSelectors = selectors.keySet();
        mappers.stream()
                .filter(mapper -> availableSelectors.contains(mapper.getFrom()) && !availableSelectors.contains(mapper.getTo()))
                .forEach(mapper -> {
                    log.info("Create path from {} to {}", mapper.getFrom().getSimpleName(), mapper.getTo().getSimpleName());
                    registerSelector(
                            mapper.getTo(),
                            new MappedSelector(
                                    getSelector(mapper.getFrom()),
                                    mapper
                            )
                    );
                });
    }

    public <T> void registerSelector(Class<T> clazz, Selector<T> selector) {
        selectors.put(clazz, selector);
    }

    public <T> Selector<T> getSelector(Class<T> clazz) {
        Selector<T> selector = (Selector<T>) selectors.get(clazz);

        if (selector==null) {
            deriveSelectors();
            selector = (Selector<T>)selectors.get(clazz);

            if (selector==null) {
                throw new RuntimeException();
            }
        }

        return selector;
    }

}
