package schoperation.cardschop.command;

import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Objs;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.Iterator;

public class TableCommand implements ICommand {

    /*
        The first step to playing.

        table create [name] -> creates a new table named [name].
        table list -> lists all tables.
     */

    private final String command = "table";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Stuff with tables.
        // Show some help for tables. TODO eventually forward this to the help command?
        if (arg1.equals("blank"))
        {
            channel.sendMessage("Use: \n\n" + Msges.PREFIX + "table create [name]\n" + Msges.PREFIX + "table list");
            return;
        }

        // Create a table.
        if (arg1.equals("create"))
        {
            // Check for second argument.
            if (arg2.equals("blank"))
            {
                channel.sendMessage("Please provide a name for the table. Ex. " + Msges.PREFIX + "table create MyTable");
                return;
            }
            else
            {
                Table table = new Table(arg2, channel);
                Objs.TABLES.add(table);
                channel.sendMessage("Successfully created table. Use \"" + Msges.PREFIX + "join " + arg2 + "\" (without quotes) to join the table.");
            }
        }

        // List tables
        else if (arg1.equals("list"))
        {
            Iterator<Table> iterator = Objs.TABLES.iterator();
            StringBuilder sb = new StringBuilder();
            sb.append("Tables: \n\n");

            while (iterator.hasNext())
            {
                Table table = iterator.next();
                sb.append(table.getName());
                sb.append("\n");
            }

            channel.sendMessage(sb.toString());
        }

        return;
    }
}