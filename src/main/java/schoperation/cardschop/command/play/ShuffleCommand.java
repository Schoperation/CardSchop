package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class ShuffleCommand implements ICommand {

    /*
        Shuffles the deck.

        shuffle -> shuffles the deck.
     */

    private final String command = "shuffle";

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
            // Check if dealer
            Player player = Utils.getPlayerObj(sender, guild);

            if (player.getTable().getDealer() == player)
            {
                player.getTable().getDeck().shuffle();
                channel.sendMessage("Shuffled the deck.");
                return;
            }
            else
            {
                channel.sendMessage(Msges.NOT_DEALER);
                return;
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
