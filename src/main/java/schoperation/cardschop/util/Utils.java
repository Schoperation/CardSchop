package schoperation.cardschop.util;

import discord4j.core.object.entity.*;
import discord4j.core.spec.MessageEditSpec;
import reactor.core.publisher.Mono;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;

import java.util.function.Consumer;

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
                if (player.getUser().getId().equals(user.getId()))
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
                if (player.getUser().getId().equals(user.getId()))
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
