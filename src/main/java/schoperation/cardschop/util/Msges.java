package schoperation.cardschop.util;

public class Msges {

    /*
        Some repeated messages that I'll keep in a central place.
     */

    // Prefix. It's here because it's part of a message. May not be final later on.
    public static final String PREFIX = "&";

    public static final String VERSION = "0.1.0";

    public static final String NOT_DEALER = "You are not the dealer! Use `" + PREFIX + "setdealer`.";
    public static final String NO_TABLE = "You must be part of a table. Use `" + PREFIX + "join [tablename]` to join one.";
    public static final String TABLE_NOT_FOUND = "Could not find that table.";
    public static final String INVALID_CARD = "Invalid card number.";
    public static final String EMPTY_PILE = "This pile is empty.";
    public static final String NAN = "At least one of your arguments is not a number. Please provide an integer.";

    public static final String INVALID_PLACE_PLACE = "Invalid place. Your options are: underdeck, middle, pile, and infront.";
    public static final String INVALID_PLACE_DRAW = "Invalid place. Your options are: deck, middle, pile, and infront.";

    public static final String COLLECT_ARGUMENT = "Please provide a correct argument. Your options are: cards, trick, middle, and pot.";

    public static final String PM_NOTIFICATION = "PM has updated.";
}
