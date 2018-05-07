package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Objs;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.Iterator;

public class TableCommand implements ICommand {

    /*
        The first step to playing.

        table create [name] -> creates a new table named [name].
        table list -> lists all tables.
        table delete [name] -> deletes the specified table.
        table flip -> flips the current table out of anger.
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
            channel.sendMessage("Please use: ```\n\n" + Msges.PREFIX + "table create [name]\n" + Msges.PREFIX + "table list\n" + Msges.PREFIX + "table delete [name]\n" + Msges.PREFIX + "table flip```");
            return;
        }

        // Create a table.
        else if (arg1.toLowerCase().equals("create"))
        {
            // Check for second argument.
            if (arg2.equals("blank"))
            {
                channel.sendMessage("Please provide a name for the table. Ex. `" + Msges.PREFIX + "table create MyTable`");
                return;
            }
            else
            {
                Table table = new Table(arg2, channel);
                Objs.TABLES.add(table);
                channel.sendMessage("Successfully created table. Use `" + Msges.PREFIX + "join " + arg2 + "` to join the table.");
            }
        }

        // List tables
        else if (arg1.toLowerCase().equals("list"))
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

        // Delete table
        else if (arg1.toLowerCase().equals("delete"))
        {
            // Check for second argument.
            if (arg2.equals("blank"))
            {
                channel.sendMessage("Please provide a name for the table. Ex. `" + Msges.PREFIX + "table delete MyTable`");
                return;
            }
            else
            {
                // Find that table, delete its message, then the object itself.
                Iterator<Table> iterator = Objs.TABLES.iterator();

                while (iterator.hasNext())
                {
                    Table table = iterator.next();

                    if (table.getName().equals(arg2))
                    {
                        table.getTableMsg().delete();
                        table.getDivider().delete();
                        Objs.TABLES.remove(table);
                        channel.sendMessage("Deleted table " + arg2 + ".");
                        return;
                    }
                }
            }
        }

        // Flip table
        else if (arg1.toLowerCase().equals("flip"))
        {
            // Must be part of a table.
            if (Utils.isPartOfTable(sender))
            {
                channel.sendMessage(sender.getDisplayName(guild) + " has flipped the table out of ***pure rage!*** **(╯°□°）╯︵ ┻━┻** Luckily our elite team of beefalo has lifted the table back up before you even noticed!");
                return;
            }

            channel.sendMessage(Msges.NO_TABLE);
            return;
        }

        return;
    }
}