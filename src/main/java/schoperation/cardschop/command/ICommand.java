package schoperation.cardschop.command;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;

public interface ICommand {

    /*
        Command interface
     */

    public String getCommand();

    public void execute(User sender, Channel channel, Guild guild, String arg1, String arg2, String arg3);
}
