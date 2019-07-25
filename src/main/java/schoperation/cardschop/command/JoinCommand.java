package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Tables;
import schoperation.cardschop.util.Utils;
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
            channel.sendMessage("Please provide a table name. Ex. `" + Msges.PREFIX + "join MyTable`");
            return;
        }

        // Are they already part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            channel.sendMessage("You are already at the table named " + Utils.getPlayerObj(sender, guild).getTable().getName() + ".");
            return;
        }

        // Does that table exist?
        for (Table table : Tables.list.get(guild))
        {
            if (table.getName().equals(arg1))
            {
                // This table exists. Make the user into a player.
                Player newPlayer = new Player(sender, table);
                table.getPlayers().add(newPlayer);
                channel.sendMessage(sender.getDisplayName(guild) + " has joined Table " + table.getName() + ".");

                // If this is the first player to join, make them the dealer.
                if (table.getPlayers().size() == 1)
                {
                    table.setDealer(newPlayer);
                    channel.sendMessage("As they are the first to join, they are the dealer.");
                }

                table.update(guild);
                return;
            }
        }

        channel.sendMessage(Msges.TABLE_NOT_FOUND);
        return;
    }
}
