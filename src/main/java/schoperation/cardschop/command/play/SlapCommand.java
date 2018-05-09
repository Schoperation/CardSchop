package schoperation.cardschop.command.play;

import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class SlapCommand implements ICommand {

    /*
        Slap the middle pile. Your hand doesn't hurt after this, fortunately/unfortunately.

        slap -> Slap the middle pile.
     */

    private final String command = "slap";

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
            channel.sendMessage(sender.getDisplayName(guild) + " has slapped the middle!");
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
