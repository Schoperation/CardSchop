package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

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

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {
        // Part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // What place?
            if (arg1.equals("blank") || arg1.equals("hand"))
                player.getPM().edit("Your hand: \n" + player.handToString());
            else if (arg1.equals("pile"))
                player.getPM().edit("Your personal pile: \n" + player.pileToString());
            else if (arg1.equals("infront") || arg1.equals("trick"))
                player.getPM().edit("Your cards in front of you: \n" + player.frontToString());
            else if (arg1.equals("middle"))
            {
                // Must be the dealer to reveal the middle.
                if (player.getTable().getDealer() == player)
                {
                    player.getPM().edit("Middle pile:\n" + player.getTable().middleToString());
                    IMessage msg = sender.getOrCreatePMChannel().sendMessage(Msges.PM_NOTIFICATION);
                    msg.delete();
                    return;
                }

                channel.sendMessage(Msges.NOT_DEALER);
                return;
            }
            else if (arg1.equals("deck"))
            {
                // Must be the dealer to reveal the deck.
                if (player.getTable().getDealer() == player)
                {
                    player.getPM().edit("Deck:\n" + player.getTable().getDeck().getCardsToString());
                    IMessage msg = sender.getOrCreatePMChannel().sendMessage(Msges.PM_NOTIFICATION);
                    msg.delete();
                    return;
                }

                channel.sendMessage(Msges.NOT_DEALER);
                return;
            }
            else
                player.getPM().edit("Invalid place. Either chose hand, pile, or infront (trick).");

            // Send a message for a notification
            IMessage msg = sender.getOrCreatePMChannel().sendMessage(Msges.PM_NOTIFICATION);
            msg.delete();
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }

    // This is a separate function so other commands can automatically do it.
    public static void seeHand(Player player)
    {
        player.getPM().edit("Your hand: \n" + player.handToString());

        // Send a message for a notification
        IMessage msg = player.getUser().getOrCreatePMChannel().sendMessage(Msges.PM_NOTIFICATION);
        msg.delete();
        return;
    }
}
