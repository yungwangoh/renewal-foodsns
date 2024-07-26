package mubex.renewal_foodsns.infrastructure.persistance.generator;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SqlDSL {

    private final Class<?> clazz;

    private SqlDSL(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static Generator generator(Class<?> clazz) {
        SqlDSL sqlDSL = new SqlDSL(clazz);
        return new Generator(sqlDSL.clazz);
    }

    public static class Generator {

        private final Class<?> clazz;
        private final StringBuilder sb = new StringBuilder();

        private Generator(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Insert insertInto() {
            Insert insert = new Insert(clazz, sb);
            insert.insertInto();
            return insert;
        }

        public Select selectFrom(final String... projections) {
            Select select = new Select(clazz, sb);
            select.selectFrom(projections);
            return select;
        }

        public static class Insert {

            private final Class<?> clazz;
            private final StringBuilder sb; // 인스턴스 필드

            private Insert(final Class<?> clazz, final StringBuilder sb) { // 생성자에서 받기
                this.clazz = clazz;
                this.sb = sb;
            }

            public Insert insertInto() {
                sb.append("insert into")
                        .append(" ")
                        .append(clazz.getSimpleName().toLowerCase())
                        .append(" ")
                        .append(joinField(clazz.getDeclaredFields()));

                return this;
            }

            public Insert values() {
                sb.append(setValue(clazz.getDeclaredFields()));
                return this;
            }

            public String getSql() {
                return sb.toString().trim();
            }

            private String joinField(final Field[] fields) {
                StringBuilder sb = new StringBuilder();
                sb.append("(");

                String collect = getFields(fields)
                        .stream()
                        .filter(field -> !Objects.equals(field.getName(), "id"))
                        .map(field -> {
                            Column column = field.getAnnotation(Column.class);
                            if (column != null) {
                                return column.name();
                            }

                            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                            if (joinColumn != null) {
                                return joinColumn.name();
                            }

                            return field.getName();
                        })
                        .collect(Collectors.joining(", "));

                sb.append(collect).append(")");
                return sb.toString();
            }

            private List<Field> getFields(final Field[] fields) {
                List<Field> fieldList = new ArrayList<>();
                fieldList.addAll(Arrays.asList(fields));
                fieldList.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
                return fieldList;
            }

            private String setValue(final Field[] fields) {
                StringBuilder sb = new StringBuilder();
                sb.append(" values ").append("(");

                String collect = getFields(fields)
                        .stream()
                        .filter(field -> !Objects.equals(field.getName(), "id"))
                        .map(field -> "?")
                        .collect(Collectors.joining(", "));

                sb.append(collect).append(")");
                return sb.toString();
            }
        }

        public static class Select {

            private final Class<?> clazz;
            private final StringBuilder sb; // 인스턴스 필드

            public Select(final Class<?> clazz, final StringBuilder sb) { // 생성자에서 받기
                this.clazz = clazz;
                this.sb = sb;
            }

            public Select selectFrom(final String... projections) {
                sb.append("select").append(" ");

                String collect;
                if (projections.length == 1) {
                    collect = projections[0];
                } else {
                    collect = String.join(", ", projections);
                }

                sb.append(collect)
                        .append(" ")
                        .append("from")
                        .append(" ")
                        .append(clazz.getSimpleName().toLowerCase())
                        .append(" ");

                return this;
            }

            public Select where(final String val, final Object obj) {
                sb.append("where")
                        .append(" ")
                        .append(val)
                        .append(" = ")
                        .append(obj)
                        .append(" ");

                return this;
            }

            public Select and(final String val, final Object obj) {
                sb.append("and")
                        .append(" ")
                        .append(val)
                        .append(" = ")
                        .append(obj)
                        .append(" ");

                return this;
            }

            public Select or(final String val, final Object obj) {
                sb.append("or")
                        .append(" ")
                        .append(val)
                        .append(" = ")
                        .append(obj)
                        .append(" ");

                return this;
            }

            public String getSql() {
                return sb.toString().trim();
            }
        }
    }
}
