package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.spec.MessageEditSpec;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;

import java.util.function.Consumer;

public class SeeCommand implements ICommand {

    /*
        See something in private.

        see [place] -> reveals something to YOURSELF ONLY.

        Options include:
            -hand -> your hand. This is default, if no arguments are provided.
            -pile -> your personal pile.
            -infront -> the cards in front of you.
     */

    private final String command = "see";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);
            String newMsg;

            // What place?
            if (arg1.equals("blank") || arg1.equals("hand"))
                newMsg = "Your hand: \n" + player.handToString();
            else if (arg1.equals("pile"))
                newMsg = "Your personal pile: \n" + player.pileToString();
            else if (arg1.equals("infront") || arg1.equals("trick"))
                newMsg = "Your cards in front of you: \n" + player.frontToString();
            else if (arg1.equals("middle"))
            {
                // Must be the dealer to reveal the middle.
                if (player.getTable().getDealer() == player)
                    newMsg = "Middle pile:\n" + player.getTable().middleToString();
                else
                {
                    channel.createMessage(Msges.NOT_DEALER);
                    return;
                }
            }
            else if (arg1.equals("deck"))
            {
                // Must be the dealer to reveal the deck.
                if (player.getTable().getDealer() == player)
                    newMsg = "Deck:\n" + player.getTable().getDeck().getCardsToString();
                else
                {
                    channel.createMessage(Msges.NOT_DEALER);
                    return;
                }
            }
            else
                newMsg = "Invalid place. Either chose hand, pile, or infront (trick).";

            // For editing the msg
            Consumer<? super MessageEditSpec> consumer = (Consumer<MessageEditSpec>) messageEditSpec -> messageEditSpec.setContent(newMsg);

            // Actually edit the PM messages
            player.getPM().block().edit(consumer);

            // Send a message for a notification
            Message msg = sender.getPrivateChannel().block().createMessage(Msges.PM_NOTIFICATION).block();
            msg.delete();
            return;
        }

        channel.createMessage(Msges.NO_TABLE);
        return;
    }

    // This is a separate function so other commands can automatically do it.
    public static void seeHand(Player player)
    {
        // For editing the msg
        Consumer<? super MessageEditSpec> consumer = (Consumer<MessageEditSpec>) messageEditSpec -> messageEditSpec.setContent("Your hand: \n" + player.handToString());

        player.getPM().block().edit(consumer);

        // Send a message for a notification
        Message msg = player.getUser().getPrivateChannel().block().createMessage(Msges.PM_NOTIFICATION).block();
        msg.delete();
        return;
    }
}
