package schoperation.cardschop.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.core.BotMain;
import schoperation.cardschop.util.Msges;

public class InfoCommand implements ICommand {

    /*
        Get information about the bot.

        info -> Spit out information about the bot.
     */

    private final String command = "info";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Let's show it to them!
        StringBuilder sb = new StringBuilder();

        sb.append("```");

        sb.append("CardSchop v" + Msges.VERSION + "\n\n");

        sb.append("Made by Schoperation.\n");
        sb.append("CardSchop is currently on " + BotMain.bot.getGuilds().count().block().intValue() + " servers.\n\n");

        sb.append("Submit issues, new stuff, or stalk the code at...\n");
        sb.append("GitHub: https://github.com/Schoperation/CardSchop\n\n");

        sb.append("```");

        channel.createMessage(sb.toString());
    }
}
