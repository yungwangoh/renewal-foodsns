package mubex.renewal_foodsns.infrastructure.persistance.nosql;

public interface KeyValueRepository {

    void set(String key, String value);

    String get(String key);
}
