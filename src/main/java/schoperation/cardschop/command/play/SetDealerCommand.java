package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.Snowflake;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class SetDealerCommand implements ICommand {

    /*
        Sets the dealer.

        setdealer [name] -> sets the dealer to [name] (display name, not default), or just the person executing the command.
     */

    private final String command = "setdealer";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Make sure they are part of a table.
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // If no argument, make the sender the dealer.
            if (arg1.equals("blank"))
            {
                // First guy to join a table becomes dealer, and since only the dealer (and near-admins) can change the dealer, why let the dealer become the dealer again
                if (player.getUser().asMember(guild.getId()).block().getBasePermissions().block().contains(Permission.MANAGE_GUILD))
                {
                    player.getTable().setDealer(player);
                    PostalService.sendMessage(channel, player.getDisplayName() + " is now the dealer.");
                }
                else
                    PostalService.sendMessage(channel, Msges.NOT_DEALER);

                return;
            }
            else
            {
                // Is this guy the dealer or a guy with Manage Server settings?
                if (player.getTable().getDealer() != player || !player.getUser().asMember(guild.getId()).block().getBasePermissions().block().contains(Permission.MANAGE_GUILD))
                {
                    PostalService.sendMessage(channel, Msges.NOT_DEALER);
                    return;
                }

                // Find the other player in the table.
                // First, get the actual user.
                arg1 = arg1.replaceAll("[<>@!]", "");
                Member userFromString = guild.getMemberById(Snowflake.of(Long.parseLong(arg1))).block();

                for (Player p : player.getTable().getPlayers())
                {
                    if (p.getUser().getId().equals(userFromString.getId()))
                    {
                        player.getTable().setDealer(p);
                        PostalService.sendMessage(channel, p.getDisplayName() + " is now the dealer.");
                        return;
                    }
                }

                PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of the table!");
                return;
            }
        }
        else
            PostalService.sendMessage(channel, Msges.NO_TABLE);

        return;
    }
}
