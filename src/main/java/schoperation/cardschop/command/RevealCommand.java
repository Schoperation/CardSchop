package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class RevealCommand implements ICommand {

    /*
        Reveal your hand.

        reveal -> reveals your hand to EVERYONE.
     */

    private final String command = "reveal";

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
            channel.sendMessage(sender.getDisplayName(guild) + "'s hand: \n" + player.handToString());
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
