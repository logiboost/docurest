package org.docurest;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentRoot<S> {

    private final Infra infra;
    protected final DocumentConfig<S> documentConfig;
    protected final Class<S> docClass;

    public DocumentRoot(Infra infra, DocumentConfig<S> documentConfig) {
        this.infra = infra;
        this.documentConfig = documentConfig;
        this.docClass = documentConfig.getDocClass();
    }

    @PostConstruct
    private void postConstruct() {
        infra.getBaseInfra().registerConfig(documentConfig);
    }


    protected GroupedOpenApi groupedOpenApi() {
        String packageName = infra.getConfig((Class<?>) docClass).getRootClass().getPackage().getName();

        String result;
        if (!packageName.contains(".")) {
            result = packageName;
        } else {
            result = packageName.substring(packageName.lastIndexOf(".") + 1);
        }

        return GroupedOpenApi.builder()
                .group(result)
                .packagesToScan(infra.getConfig((Class<?>) docClass).getRootClass().getPackage().getName())
                .addOperationCustomizer((operation, handlerMethod) ->
                        operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth")))
                .build();
    }

}
