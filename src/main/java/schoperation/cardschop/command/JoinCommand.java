package schoperation.cardschop.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Tables;
import schoperation.cardschop.util.Utils;

public class JoinCommand implements ICommand {


    /*
        Allows a user to join a table.

        join [table] -> joins that table, if it exists.
     */

    private final String command = "join";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {

        if (arg1.equals("blank"))
        {
            channel.createMessage("Please provide a table name. Ex. `" + Msges.PREFIX + "join MyTable`");
            return;
        }

        // Are they already part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            channel.createMessage("You are already at the table named " + Utils.getPlayerObj(sender, guild).getTable().getName() + ".");
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
                channel.createMessage(newPlayer.getDisplayName() + " has joined Table " + table.getName() + ".");

                // If this is the first player to join, make them the dealer.
                if (table.getPlayers().size() == 1)
                {
                    table.setDealer(newPlayer);
                    channel.createMessage("As they are the first to join, they are the dealer.");
                }

                table.update(guild);
                return;
            }
        }

        channel.createMessage(Msges.TABLE_NOT_FOUND);
        return;
    }
}
