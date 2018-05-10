package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class RevealCommand implements ICommand {

    /*
        Reveals something of yours.

        reveal -> reveals something to EVERYONE.

        Options include:
            -hand -> your hand. This is default, if no arguments are provided.
            -pile -> your personal pile.
            -infront -> the cards in front of you.
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
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // What place?
            if (arg1.equals("blank") || arg1.equals("hand"))
                channel.sendMessage(sender.getDisplayName(guild) + "'s hand:\n" + player.handToString());
            else if (arg1.equals("pile"))
                channel.sendMessage(sender.getDisplayName(guild) + "'s side pile:\n" + player.pileToString());
            else if (arg1.equals("infront") || arg1.equals("trick"))
                channel.sendMessage(sender.getDisplayName(guild) + "'s front cards:\n" + player.frontToString());
            else
                channel.sendMessage("Invalid place. Either chose hand, pile, or infront/trick.");
            
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
