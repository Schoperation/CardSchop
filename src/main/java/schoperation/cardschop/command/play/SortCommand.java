package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class SortCommand implements ICommand {

    /*
        Sorting a hand.

        sort [bywhat] -> Sorts your hand by either rank or suit.

        Options:
            -byrank
            -bysuit
     */

    private final String command = "sort";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
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
                channel.sendMessage("Valid sorting methods are byrank and bysuit.");

            SeeCommand.seeHand(player);
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
