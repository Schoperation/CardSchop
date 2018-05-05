package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Objs;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class JoinCommand implements ICommand {


    /*
        Allows a user to join a table.

        join [table] -> joins that table, if it exists.
     */

    private final String command = "join";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        if (arg1.equals("blank"))
        {
            channel.sendMessage("Please provide a table name. Ex. &join MyTable");
            return;
        }

        // Does that table exist?
        for (Table table : Objs.TABLES)
        {
            if (table.getName().equals(arg1))
            {
                // This table exists. Make the user into a player.
                table.getPlayers().add(new Player(sender, table));
                channel.sendMessage(sender.getDisplayName(guild) + " has joined Table " + table.getName() + ".");
                return;
            }
        }

        channel.sendMessage(Msges.TABLE_NOT_FOUND);
        return;
    }
}
