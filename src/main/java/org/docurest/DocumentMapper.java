package org.docurest;

import lombok.Getter;

public abstract class DocumentMapper<I, O> {

    protected final Infra infra;

    @Getter
    private final Class<I> from;

    @Getter
    private final Class<O> to;

    public DocumentMapper(Infra infra, Class<I> from, Class<O> to) {
        this.infra = infra;
        this.from = from;
        this.to = to;

        infra.getBaseInfra().registerMapper(this);
    }


    public abstract Document<O> mapDoc(Document<I> incomingDoc);



//    @SneakyThrows
//    protected <T, U> U isoMap(T source, MapperObject<T, U> mapperObject) {
//        return mapperObject.map(source);
//    }
//
//    protected <T, U> U isoMap(T source, Class<U> targetClass) {
//        return isoMap(source, new MapperObject<T, U>((Class<T>)source.getClass(), targetClass));
//    }
//
//    protected static class MapperObject<T, U> {
//
//        @RequiredArgsConstructor
//        static class Layout {
//            private final Method setter;
//            private final Function<?, ?> getter;
//        }
//
//        private final Class<U> targetClass;
//        private final Map<String, Layout> layoutMap = new HashMap<>();
//
//
//        public MapperObject(final Class<T> sourceClass, final Class<U> targetClass) {
//            this.targetClass = targetClass;
//            Arrays.stream(targetClass.getMethods())
//                    .filter(method -> method.getName().startsWith("set") && method.getName().length() >= 4)
//                    .forEach(method -> {
//
//                        final String getterName = "get" + method.getName().substring(3);
//                        Function<?, ?> getter;
//
//                        try {
//                            final Method mapperMethod = this.getClass().getMethod(getterName, sourceClass);
//                            getter = (Object object) -> {
//                                try {
//                                    return mapperMethod.invoke(this, object);
//                                } catch (IllegalAccessException e) {
//                                    throw new RuntimeException(e);
//                                } catch (InvocationTargetException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            };
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        layoutMap.put(
//                                method.getName(),
//                                new Layout(
//                                        method,
//                                        object -> object
//                                )
//                        );
//                    });
//        }
//
//        @SneakyThrows
//        public U map(T object) {
//            final var instance = targetClass.getDeclaredConstructor().newInstance();
//
//            return instance;
//        }
//
//    }
//
//    final MapperObject<ProjectV0, Project> mapper = new MapperObject<ProjectV0, Project>(ProjectV0.class, Project.class) {
//        String id() {
//            return null;
//        }
//
//        List<PersonLabel> owners() {
//            return null;
//        }
//
//        List<ProjectVersion> versions() {
//            return null;
//        }
//    };


}
