package org.docurest.security.tools;

import jakarta.annotation.security.PermitAll;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class EndpointScanner {

    public static Set<Endpoint> scanContext(ApplicationContext appContext) {
        Set<Endpoint> endpoints = new HashSet<>();
        String[] controllerBeans = appContext.getBeanNamesForAnnotation(RestController.class);

        for (String beanName : controllerBeans) {
            Class<?> controllerClass = appContext.getType(beanName);
            endpoints.addAll(scanController(controllerClass));
        }
        return endpoints;
    }

    private static boolean isClassPermitAll(Class<?> controllerClass) {
        while (controllerClass != null) {
            if (controllerClass.isAnnotationPresent(PermitAll.class)) {
                return true;
            }
            controllerClass = controllerClass.getSuperclass();  // Move to the superclass
        }
        return false;
    }

    private static Set<Endpoint> scanController(Class<?> controllerClass) {
        Set<Endpoint> endpoints = new HashSet<>();
        RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
        String basePath = (classMapping != null && classMapping.value().length == 1)
                ? classMapping.value()[0] : "";

        final var permitAll = isClassPermitAll(controllerClass);

        while (controllerClass != null) {
            // Scan methods for endpoint mappings
            for (Method method : controllerClass.getDeclaredMethods()) {
                processMethod(method, endpoints, basePath, permitAll);
            }
            controllerClass = controllerClass.getSuperclass();  // Move to the superclass
        }

        return endpoints;
    }

    private static String removeEdgeSlashes(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("^/+|/+$", "");
    }

    private static void processMethod(Method method, Set<Endpoint> endpoints, String basePath, boolean classPermitAll) {

        HttpMethod httpMethod = null;
        String methodMappingPath = "";
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            httpMethod = HttpMethod.GET;
            methodMappingPath = getMapping.value().length == 1 ? getMapping.value()[0] : "";
        }

        if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            httpMethod = HttpMethod.POST;
            methodMappingPath = postMapping.value().length == 1 ? postMapping.value()[0] : "";
        }

        String fullPath = "";
        if (!basePath.isEmpty()) {
            fullPath += "/" + removeEdgeSlashes(basePath);
        }

        if (!methodMappingPath.isEmpty()) {
            fullPath += "/" + removeEdgeSlashes(methodMappingPath);
        }

        // Check for PermitAll annotation
        boolean permitAll = classPermitAll || method.isAnnotationPresent(PermitAll.class);

        // Add endpoint to the set
        if (httpMethod != null && !fullPath.isEmpty()) {
            endpoints.add(Endpoint.builder()
                    .method(httpMethod)
                    .path(fullPath)
                    .permitAll(permitAll)
                    .build());
        }
    }
}
