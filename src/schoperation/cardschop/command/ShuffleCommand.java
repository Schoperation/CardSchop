package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.core.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class ShuffleCommand implements ICommand {

    /*
        Shuffles the deck.

        shuffle -> shuffles the deck.
     */

    private String command = "shuffle";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if(Utils.isPartOfTable(sender))
        {
            // Check if dealer
            Player player = Utils.getPlayerClass(sender);

            if (player.getTable().getDealer().equals(player))
            {
                player.getTable().getDeck().shuffle();
                channel.sendMessage("Shuffled the deck.");
                return;
            }
            else
            {
                channel.sendMessage("You are not the dealer! Use &setdealer.");
                return;
            }
        }

        channel.sendMessage("You must be part of a table.");
        return;
    }
}
