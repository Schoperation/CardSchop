package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class LeaveCommand implements ICommand {

    /*
        Leave a table

        leave -> leaves the current table.
     */

    private final String command = "leave";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {
        // Make sure they are part of a table
        if (Utils.isPartOfTable(sender))
        {
            // Good. Put their cards back into the deck, and get them outta there.
            Player player = Utils.getPlayerClass(sender);

            if (player.getNumOfCards() != 0)
            {
                player.getTable().getDeck().getCards().addAll(player.getHand());
                player.emptyHand();
            }

            player.getTable().getPlayers().remove(player);
            channel.sendMessage(sender.getDisplayName(guild) + " has left the table.");
            return;
        }


        channel.sendMessage(Msges.TABLE_NOT_FOUND);
        return;
    }
}
