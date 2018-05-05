package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class SeeCommand implements ICommand {

    /*
        See your hand.

        see -> reveals your hand to YOURSELF ONLY.
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
            sender.getOrCreatePMChannel().sendMessage("Your hand: \n" + player.handToString());
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
