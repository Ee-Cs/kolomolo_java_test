package kp.models;

/**
 * The rating.
 */
public enum Rating {
    One,
    Two,
    Three,
    Four,
    Five,
    NotGiven;

    /**
     * Returns a rating enumeration.
     *
     * @param name the name
     * @return the day of the week
     */
    public static Rating of(String name) {

        return switch (name) {
            case "1" -> One;
            case "2" -> Two;
            case "3" -> Three;
            case "4" -> Four;
            case "5" -> Five;
            default -> NotGiven;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.equals(NotGiven) ? "Not Given" : this.name();
    }

}
