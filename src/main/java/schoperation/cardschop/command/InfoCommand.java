package schoperation.cardschop.command;

import schoperation.cardschop.core.BotMain;
import schoperation.cardschop.util.Msges;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class InfoCommand implements ICommand {

    /*
        Get information about the bot.

        info -> Spit out information about the bot.
     */

    private final String command = "info";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {
        // Let's show it to them!
        StringBuilder sb = new StringBuilder();

        sb.append("```");

        sb.append("CardSchop v" + Msges.VERSION + "\n\n");

        sb.append("Made by Schoperation.\n");
        sb.append("CardSchop is currently on " + BotMain.bot.getGuilds().size() + " servers.\n\n");

        sb.append("Submit issues, new stuff, or stalk the code at...\n");
        sb.append("GitHub: https://github.com/Schoperation/CardSchop\n\n");

        sb.append("```");

        channel.sendMessage(sb.toString());
    }
}
