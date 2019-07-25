package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

public class SetDealerCommand implements ICommand {

    /*
        Sets the dealer.

        setdealer [name] -> sets the dealer to [name] (display name, not default), or just the person executing the command.
     */

    private final String command = "setdealer";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {
        // Make sure they are part of a table.
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // If no argument, make the sender the dealer.
            if (arg1.equals("blank"))
            {
                // First guy to join a table becomes dealer, and since only the dealer (and near-admins) can change the dealer, why let the dealer become the dealer again
                if (player.getUser().getPermissionsForGuild(guild).contains(Permissions.MANAGE_SERVER))
                {
                    player.getTable().setDealer(player);
                    channel.sendMessage(player.getUser().getDisplayName(guild) + " is now the dealer.");
                }
                else
                    channel.sendMessage(Msges.NOT_DEALER);

                return;
            }
            else
            {
                // Is this guy the dealer or a guy with Manage Server settings?
                if (player.getTable().getDealer() != player || !player.getUser().getPermissionsForGuild(guild).contains(Permissions.MANAGE_SERVER))
                {
                    channel.sendMessage(Msges.NOT_DEALER);
                    return;
                }

                // Find the other player in the table.
                // First, get the actual user.
                arg1 = arg1.replaceAll("[<>@!]", "");
                IUser userFromString = guild.getUserByID(Long.parseLong(arg1));

                for (Player p : player.getTable().getPlayers())
                {
                    if (p.getUser().equals(userFromString))
                    {
                        player.getTable().setDealer(p);
                        channel.sendMessage(p.getUser().getDisplayName(guild) + " is now the dealer.");
                        return;
                    }
                }

                channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of the table!");
                return;
            }
        }
        else
            channel.sendMessage(Msges.NO_TABLE);

        return;
    }
}
