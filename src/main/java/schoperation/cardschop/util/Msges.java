package schoperation.cardschop.util;

public class Msges {

    /*
        Some repeated messages that I'll keep in a central place.
     */

    // Prefix. It's here because it's part of a message. May not be final later on.
    public static final String PREFIX = new String("&");

    public static final String NOT_DEALER = new String("You are not the dealer! Use " + PREFIX + "setdealer.");
    public static final String NO_TABLE = new String("You must be part of a table. Use " + PREFIX + "join [tablename] to join one.");
    public static final String TABLE_NOT_FOUND = new String("Could not find that table.");
}
