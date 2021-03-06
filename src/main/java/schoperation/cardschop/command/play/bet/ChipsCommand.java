package schoperation.cardschop.command.play.bet;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class ChipsCommand implements ICommand {

    /*
        Allows the dealer to mess with players' chips.

        chips [player] [operator] [amount] -> Does something to a player's chips.

        -Player should be mentioned. Can use "all"
        -Operators include:
            -add
            -subtract/sub
            -set

        MUST be the dealer to use this command.
     */

    private final String command = "chips";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // Is this player the dealer?
            if (player.getTable().getDealer() == player)
            {
                // All three arguments must be fulfilled.
                if (arg1.equals("blank") || arg2.equals("blank") || arg3.equals("blank"))
                {
                    PostalService.sendMessage(channel, "Usage: `" + Msges.PREFIX + "chips [player] [operator] [amount]`. All three arguments must be fulfilled.");
                    return;
                }
                else
                {
                    // Find the player they specified.

                    /*
                        Everyone at the table
                     */
                    if (arg1.equals("all") || arg1.equals("@everyone") || arg1.equals("everyone"))
                    {
                        // Found them. Parse amount, and figure out the operator.
                        if (!Utils.isInt(arg3))
                        {
                            PostalService.sendMessage(channel, Msges.NAN);
                            return;
                        }

                        int amount = Integer.parseInt(arg3);

                        // Add chips
                        if (arg2.equals("add"))
                        {
                            for (Player p : player.getTable().getPlayers())
                                p.addChips(amount);

                            PostalService.sendMessage(channel, "The dealer has given everyone " + amount + " chips.");
                        }

                        // Subtract chips
                        else if (arg2.equals("subtract") || arg2.equals("sub"))
                        {
                            for (Player p : player.getTable().getPlayers())
                                p.subtractChips(amount);

                            PostalService.sendMessage(channel, "The dealer has taken " + amount + " chips from everyone.");
                        }

                        // Set chips
                        else if (arg2.equals("set"))
                        {
                            for (Player p : player.getTable().getPlayers())
                                p.setChips(amount);

                            PostalService.sendMessage(channel, "The dealer has set everyone's chips to " + amount + ".");
                        }

                        else
                            PostalService.sendMessage(channel, "Invalid operator. Valid operators are add, subtract (sub), and set.");

                        // Update table
                        player.getTable().update(guild);
                        return;
                    }

                    /*
                        Specific player
                      */

                    // Make sure they are part of a table first.
                    arg1 = arg1.replaceAll("[<>@!]", "");
                    Member userFromString = guild.getMemberById(Snowflake.of(Long.parseLong(arg1))).block();

                    for (Player receivingPlayer : player.getTable().getPlayers())
                    {
                        if (receivingPlayer.getUser().getId().equals(userFromString.getId()))
                        {
                            // Found them. Parse amount, and figure out the operator.
                            if (!Utils.isInt(arg3))
                            {
                                PostalService.sendMessage(channel, Msges.NAN);
                                return;
                            }

                            int amount = Integer.parseInt(arg3);

                            // Add chips
                            if (arg2.equals("add"))
                            {
                                receivingPlayer.addChips(amount);
                                PostalService.sendMessage(channel, "The dealer has given " + receivingPlayer.getDisplayName() + " " + amount + " chips.");
                            }

                            // Subtract chips
                            else if (arg2.equals("subtract") || arg2.equals("sub"))
                            {
                                receivingPlayer.subtractChips(amount);
                                PostalService.sendMessage(channel, "The dealer has taken " + amount + " chips from " + receivingPlayer.getDisplayName() + ".");
                            }

                            // Set chips
                            else if (arg2.equals("set"))
                            {
                                receivingPlayer.setChips(amount);
                                PostalService.sendMessage(channel, "The dealer has set " + receivingPlayer.getDisplayName() + "'s chips to " + amount + ".");
                            }

                            else
                                PostalService.sendMessage(channel, "Invalid operator. Valid operators are add, subtract (sub), and set.");

                            // Update table
                            player.getTable().update(guild);
                            return;
                        }
                    }

                    PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
                    return;
                }
            }

            PostalService.sendMessage(channel, Msges.NOT_DEALER);
            return;
        }

        PostalService.sendMessage(channel, Msges.NO_TABLE);
        return;
    }
}
