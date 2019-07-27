package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class SortCommand implements ICommand {

    /*
        Sorting a hand.

        sort [bywhat] -> Sorts your hand by either rank or suit.

        Options:
            -byrank
            -bysuit
     */

    private final String command = "sort";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // Sort by rank by default.
            if (arg1.equals("blank") || arg1.equals("byrank"))
                player.sortHand(1);
            else if (arg1.equals("bysuit"))
                player.sortHand(2);
            else
                PostalService.sendMessage(channel, "Valid sorting methods are byrank and bysuit.");

            SeeCommand.seeHand(player);
            return;
        }

        PostalService.sendMessage(channel, Msges.NO_TABLE);
        return;
    }
}
