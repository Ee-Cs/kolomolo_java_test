package kp.models;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * The cuisine type.
 */
public enum CuisineType {
    American,
    Chinese,
    French,
    Indian,
    Italian,
    Japanese,
    Korean,
    Mediterranean,
    Mexican,
    MiddleEastern,
    Southern,
    Spanish,
    Thai,
    Vietnamese;

    /**
     * Returns a cuisine type enumeration.
     *
     * @param name the name
     * @return the cuisine type
     */
    public static CuisineType of(String name) {

        return Optional.ofNullable(name)
                .map(str -> str.replaceAll("\\s+", ""))
                .filter(Predicate.not(String::isBlank))
                .map(CuisineType::valueOf).orElse(American);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.equals(MiddleEastern) ? "Middle Eastern" : this.name();
    }
}
