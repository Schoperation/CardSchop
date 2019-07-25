package schoperation.cardschop.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;

public interface ICommand {

    /*
        Command interface
     */

    String getCommand();

    void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3);
}
