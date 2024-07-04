package trash;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class TypescriptGenerator {

    private final Set<Class<?>> done = new HashSet<>();
    private final Stack<Class<?>> stack = new Stack<>();
    private StringBuilder builder = new StringBuilder();

    public TypescriptGenerator() {

    }

    public static String inferFieldNameFromGetter(String getterName) {
        if (getterName.startsWith("get")) {
            return decapitalize(getterName.substring(3));
        } else if (getterName.startsWith("is")) {
            return decapitalize(getterName.substring(2));
        }
        throw new IllegalArgumentException("Invalid getter method name: " + getterName);
    }

    private static String decapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        // Convert the first letter to lower case as per Java bean standard
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    private static final Map<Class<?>, String> typeMap = Map.ofEntries(
            Map.entry(byte.class, "number"),
            Map.entry(short.class, "number"),
            Map.entry(int.class, "number"),
            Map.entry(long.class, "number"),
            Map.entry(float.class, "number"),
            Map.entry(double.class, "number"),
            Map.entry(boolean.class, "boolean"),
            Map.entry(char.class, "string"),
            Map.entry(String.class, "string"),
            Map.entry(BigDecimal.class, "number"),
            Map.entry(LocalDate.class, "Date"),
            Map.entry(LocalDateTime.class, "Date"),
            Map.entry(Instant.class, "Date"),
            Map.entry(BigInteger.class, "number"),
            Map.entry(ZonedDateTime.class, "Date"),
            Map.entry(OffsetDateTime.class, "Date"),
            Map.entry(UUID.class, "string")
    );

    private String convertType(Class<?> clazz, Type type) {
        if (typeMap.containsKey(clazz)) {
            return typeMap.get(clazz);
        }

        if (clazz.equals(List.class)) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)type;
                Type pType = parameterizedType.getActualTypeArguments()[0];
                Class<?> pClass = (Class<?>)pType;

                if (typeMap.containsKey(pClass)) {
                    return typeMap.get(clazz) + "[]";
                }
                if (!done.contains(pClass)) {
                    stack.push(pClass);
                }
                return pClass.getSimpleName() + "[]";
            }
        }

        throw new RuntimeException();
    }

    private String toTypescriptProperty(Method method) {
        return String.format("\t%s: %s;", inferFieldNameFromGetter(method.getName()), convertType(method.getReturnType(), method.getGenericReturnType()));
    }

    public String generate(Class<?> startClass) {
        stack.push(startClass);

        while (!stack.isEmpty()) {
            final var currentClass = stack.pop();
            done.add(currentClass);

            String body = Arrays.stream(currentClass.getMethods())
                    .filter(method -> !method.getName().equals("getClass"))
                    .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
                    .filter(method -> method.getParameterCount() == 0 && !void.class.equals(method.getReturnType()))
                    .map(this::toTypescriptProperty)
                    .collect(Collectors.joining("\n"));

            builder.append(String.format("export interface %s {\n%s\n}\n\n", currentClass.getSimpleName(), body));
        }

        return builder.toString();
    }

}
