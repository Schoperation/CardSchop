package schoperation.cardschop.util;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;

public class Utils {

    /*
        Some utility methods.
     */

    // Figure out whether user is a player or not.
    public static boolean isPartOfTable(User user, Guild guild)
    {
        // Go through tables
        for (Table table : Tables.list.get(guild))
        {
            // Go through the table's players
            for (Player player : table.getPlayers())
            {
                // Is this the player?
                if (player.getUser() == user)
                    return true;
            }
        }

        // Found nothing.
        return false;
    }

    // After finding out that ARE a player, get that object.
    public static Player getPlayerObj(User user, Guild guild)
    {
        // Go through tables
        for (Table table : Tables.list.get(guild))
        {
            // Go through the table's players
            for (Player player : table.getPlayers())
            {
                // Is this the player?
                if (player.getUser() == user)
                    return player;
            }
        }

        // Nothing.
        return null;
    }

    // Is this an integer? Used in commands before parsing them.
    public static boolean isInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        // NullPointerEx is not needed, as in every command's execute any blank arguments are passed as "blank".
    }
}
