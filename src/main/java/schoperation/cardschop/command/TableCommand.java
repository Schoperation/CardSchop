package schoperation.cardschop.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Tables;
import schoperation.cardschop.util.Utils;

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

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {

        // Stuff with tables.
        // Show some help for tables. TODO eventually forward this to the help command?
        if (arg1.equals("blank"))
        {
            PostalService.sendMessage(channel, "Please use: ```\n\n" + Msges.PREFIX + "table create [name]\n" + Msges.PREFIX + "table list\n" + Msges.PREFIX + "table delete [name]\n" + Msges.PREFIX + "table flip```");
            return;
        }

        // Create a table.
        else if (arg1.equals("create"))
        {
            // Check for second argument.
            if (arg2.equals("blank"))
            {
                PostalService.sendMessage(channel, "Please provide a name for the table. Ex. `" + Msges.PREFIX + "table create MyTable`");
                return;
            }
            else
            {
                // Does this table already exist?
                for (Table t : Tables.list.get(guild))
                {
                    if (t.getName().equals(arg2))
                    {
                        PostalService.sendMessage(channel, "A table named " + arg2 + " already exists.");
                        return;
                    }
                }

                // Does NOT exist already. Sweet, make it.
                Table table = new Table(arg2, channel);
                Tables.list.get(guild).add(table);
                PostalService.sendMessage(channel, "Successfully created table. Use `" + Msges.PREFIX + "join " + arg2 + "` to join the table.");
            }
        }

        // List tables
        else if (arg1.equals("list"))
        {
            Iterator<Table> iterator = Tables.list.get(guild).iterator();
            StringBuilder sb = new StringBuilder();
            sb.append("Tables: \n\n");

            while (iterator.hasNext())
            {
                Table table = iterator.next();
                sb.append(table.getName());
                sb.append("\n");
            }

            PostalService.sendMessage(channel, sb.toString());
        }

        // Delete table
        else if (arg1.equals("delete"))
        {
            // Check for second argument.
            if (arg2.equals("blank"))
            {
                PostalService.sendMessage(channel, "Please provide a name for the table. Ex. `" + Msges.PREFIX + "table delete MyTable`");
                return;
            }
            else
            {
                // Find that table, delete its message, then the object itself.
                Iterator<Table> iterator = Tables.list.get(guild).iterator();

                while (iterator.hasNext())
                {
                    Table table = iterator.next();

                    if (table.getName().equals(arg2))
                    {
                        table.getTableMsg().delete().subscribe();

                        // If they are part of the table, clear the log.
                        if (Utils.isPartOfTable(sender, guild))
                        {
                            if (Utils.getPlayerObj(sender, guild).getTable() == table)
                            {
                                ClearCommand clear = new ClearCommand();
                                clear.execute(sender, channel, guild, "blank", "blank", "blank");
                            }
                        }

                        table.getDivider().delete().subscribe();

                        Tables.list.get(guild).remove(table);
                        //channel.sendMessage("Deleted table " + arg2 + ".");
                        return;
                    }
                }
            }
        }

        // Flip table
        else if (arg1.equals("flip"))
        {
            // Must be part of a table.
            if (Utils.isPartOfTable(sender, guild))
            {
                Player p = Utils.getPlayerObj(sender, guild);

                PostalService.sendMessage(channel, p.getDisplayName() + " has flipped the table out of ***pure rage!*** **(╯°□°）╯︵ ┻━┻** Luckily our elite team of beefalo has lifted the table back up before you even noticed!");
                return;
            }

            PostalService.sendMessage(channel, Msges.NO_TABLE);
            return;
        }

        return;
    }
}