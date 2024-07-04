package org.docurest.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docurest.ActionHandler;
import org.docurest.Document;
import org.docurest.DocumentConfig;
import org.docurest.DocumentMapper;
import org.docurest.queries.filter.Filter;
import org.docurest.security.UserAuthDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class BaseInfra implements InfraContract {
    private final Map<Class<?>, DocumentConfig<?>> configs = new HashMap<>();
    private final SelectorProvider selectorProvider = new SelectorProvider();
    private final Map<Class<?>, StringParserMutator<?>> stringImportMutator = new HashMap<>();
    private final Map<Class<?>, Mutator<?>> regularMutator = new HashMap<>();
    private final Map<Class<?>, ActionHandler<?, ?>> actionHandlers = new HashMap<>();

    // Registration
    // ------------

    public void registerMapper(DocumentMapper<?, ?> mapper) {
        selectorProvider.registerMapper(mapper);
    }

    public void registerActionHandler(ActionHandler<?, ?> handler) {
        actionHandlers.put(handler.getActionClass(), handler);
    }

    public <T> void registerConfig(DocumentConfig<T> config) {
        final Class<T> clazz = config.getDocClass();
        configs.put(clazz, config);
        selectorProvider.registerSelector(clazz, new StringParserSelector<>(config.getDriver(), config.getMigrationChain()));
        stringImportMutator.put(clazz, new StringParserMutator<>(config.getDriver(), config.getMigrationChain()));
        regularMutator.put(clazz, config.getDriver());
    }

    // Retrievers
    // ------------

    @Override
    public <T> DocumentConfig<T> getConfig(Class<T> clazz) {
        return (DocumentConfig<T>)configs.get(clazz);
    }

    @Override
    public <T> Selector<T> getSelector(Class<T> clazz) {
        return selectorProvider.getSelector(clazz);
    }

    @Override
    public <T> ActionHandler<?, ?> getActionHandler(Class<T> clazz) {
        return actionHandlers.get(clazz);
    }

    @Override
    public Mutator<String> getStringImportMutator(Class<?> clazz) {
        return stringImportMutator.get(clazz);
    }

    @Override
    public <T> Mutator<T> getMutator(Class<T> clazz) {
        return ((Mutator<T>) regularMutator.get(clazz));
    }

    public static UserAuthDetails requireUserAuthDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException();
        }

        return (UserAuthDetails)authentication.getPrincipal();
    }
}
