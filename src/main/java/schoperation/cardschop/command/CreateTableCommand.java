package schoperation.cardschop.command;

import schoperation.cardschop.card.Table;
import schoperation.cardschop.core.Objs;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class CreateTableCommand implements ICommand {

    /*
        The first step to playing.

        createtable [name] -> creates a new table named [name].
     */

    private String command = "createtable";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Add a table to the list of tables.
        if (arg1.equals("blank"))
        {
            channel.sendMessage("Please provide a name for the table. Ex. &createtable MyTable");
            return;
        }

        Objs.TABLES.add(new Table(arg1, channel));
        channel.sendMessage("Successfully created table. Type &join " + arg1 + "\n to join the table.");
        return;
    }
}
