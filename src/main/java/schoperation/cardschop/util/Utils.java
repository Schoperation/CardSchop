package schoperation.cardschop.util;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import sx.blah.discord.handle.obj.IUser;

public class Utils {

    /*
        Some utility methods.
     */

    // Figure out whether user is a player or not.
    public static boolean isPartOfTable(IUser user)
    {
        // Go through tables
        for (Table table : Objs.TABLES)
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
    public static Player getPlayerClass(IUser user)
    {
        // Go through tables
        for (Table table : Objs.TABLES)
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
}
