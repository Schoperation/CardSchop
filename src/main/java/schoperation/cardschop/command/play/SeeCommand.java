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
        if (Utils.isPartOfTable(sender))
        {
            Player player = Utils.getPlayerClass(sender);

            // What place?
            if (arg1.equals("blank") || arg1.toLowerCase().equals("hand"))
                player.getPM().edit("Your hand: \n" + player.handToString());
            else if (arg1.toLowerCase().equals("pile"))
                player.getPM().edit("Your side pile: \n" + player.pileToString());
            else if (arg1.toLowerCase().equals("infront"))
                player.getPM().edit("Your cards in front of you: \n" + player.frontToString());
            else
                player.getPM().edit("Invalid place. Either chose hand, pile, or infront.");

            // Send a message for a notification
            IMessage msg = sender.getOrCreatePMChannel().sendMessage("Your hand has updated.");
            msg.delete();
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
