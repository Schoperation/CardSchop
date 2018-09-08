package schoperation.cardschop.command;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public interface ICommand {

    /*
        Command interface
     */

    public String getCommand();

    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3);
}
