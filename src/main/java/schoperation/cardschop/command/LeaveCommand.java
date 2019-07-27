package schoperation.cardschop.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class LeaveCommand implements ICommand {

    /*
        Leave a table

        leave -> leaves the current table.
     */

    private final String command = "leave";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Make sure they are part of a table
        if (Utils.isPartOfTable(sender, guild))
        {
            // Good. Put their cards back into the deck, and get them outta there.
            Player player = Utils.getPlayerObj(sender, guild);

            if (player.getNumOfCards() != 0)
            {
                player.getTable().getDeck().getCards().addAll(player.getHand());
                player.clearHand();
                player.getTable().getDeck().getCards().addAll(player.getFront());
                player.getFront().clear();
                player.getTable().getDeck().getCards().addAll(player.getPile());
                player.getPile().clear();
            }

            // Delete PM
            player.getPM().delete().subscribe();

            player.getTable().getPlayers().remove(player);
            PostalService.sendMessage(channel, player.getDisplayName() + " has left the table.");
            player.getTable().update(guild);
            return;
        }

        PostalService.sendMessage(channel, Msges.TABLE_NOT_FOUND);
        return;
    }
}
