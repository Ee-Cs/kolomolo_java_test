package kp.models;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * The day of the week
 */
public enum DayOfTheWeek {
    Weekday,
    Weekend;

    /**
     * Returns a day of the week enumeration.
     *
     * @param name the name
     * @return the day of the week
     */
    public static DayOfTheWeek of(String name) {

        return Optional.ofNullable(name)
                .filter(Predicate.not(String::isBlank))
                .map(DayOfTheWeek::valueOf).orElse(Weekday);
    }
}
