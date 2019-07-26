package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;

public class ShuffleCommand implements ICommand {

    /*
        Shuffles the deck.

        shuffle -> shuffles the deck.
     */

    private final String command = "shuffle";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            // Check if dealer
            Player player = Utils.getPlayerObj(sender, guild);

            if (player.getTable().getDealer() == player)
            {
                player.getTable().getDeck().shuffle();
                channel.createMessage("Shuffled the deck.");
                return;
            }
            else
            {
                channel.createMessage(Msges.NOT_DEALER);
                return;
            }
        }

        channel.createMessage(Msges.NO_TABLE);
        return;
    }
}
