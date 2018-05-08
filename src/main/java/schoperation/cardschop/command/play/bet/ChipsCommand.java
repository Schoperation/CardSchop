package schoperation.cardschop.command.play.bet;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

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

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if (Utils.isPartOfTable(sender))
        {
            Player player = Utils.getPlayerObj(sender);

            // Is this player the dealer?
            if (player.getTable().getDealer() == player)
            {
                // All three arguments must be fulfilled.
                if (arg1.equals("blank") || arg2.equals("blank") || arg3.equals("blank"))
                {
                    channel.sendMessage("Usage: `" + Msges.PREFIX + "chips [player] [operator] [amount]`. All three arguments must be fulfilled.");
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
                        int amount = Integer.parseInt(arg3);

                        // Add chips
                        if (arg2.equals("add"))
                        {
                            for (Player p : player.getTable().getPlayers())
                                p.addChips(amount);

                            channel.sendMessage("The dealer has given everyone " + amount + " chips.");
                        }

                        // Subtract chips
                        else if (arg2.equals("subtract") || arg2.equals("sub"))
                        {
                            for (Player p : player.getTable().getPlayers())
                                p.subtractChips(amount);

                            channel.sendMessage("The dealer has taken " + amount + " chips from everyone.");
                        }

                        // Set chips
                        else if (arg2.equals("set"))
                        {
                            for (Player p : player.getTable().getPlayers())
                                p.setChips(amount);

                            channel.sendMessage("The dealer has set everyone's chips to " + amount + ".");
                        }

                        else
                            channel.sendMessage("Invalid operator. Valid operators are add, subtract (sub), and set.");

                        // Update table
                        player.getTable().update(guild);
                        return;
                    }

                    /*
                        Specific player
                      */

                    // Make sure they are part of a table first.
                    arg1 = arg1.replaceAll("[<>@!]", "");
                    IUser userFromString = guild.getUserByID(Long.parseLong(arg1));

                    for (Player receivingPlayer : player.getTable().getPlayers())
                    {
                        if (receivingPlayer.getUser().equals(userFromString))
                        {
                            // Found them. Parse amount, and figure out the operator.
                            int amount = Integer.parseInt(arg3);

                            // Add chips
                            if (arg2.equals("add"))
                            {
                                receivingPlayer.addChips(amount);
                                channel.sendMessage("The dealer has given " + receivingPlayer.getUser().getDisplayName(guild) + " " + amount + " chips.");
                            }

                            // Subtract chips
                            else if (arg2.equals("subtract") || arg2.equals("sub"))
                            {
                                receivingPlayer.subtractChips(amount);
                                channel.sendMessage("The dealer has taken " + amount + " chips from " + receivingPlayer.getUser().getDisplayName(guild) + ".");
                            }

                            // Set chips
                            else if (arg2.equals("set"))
                            {
                                receivingPlayer.setChips(amount);
                                channel.sendMessage("The dealer has set " + receivingPlayer.getUser().getDisplayName(guild) + "'s chips to " + amount + ".");
                            }

                            else
                                channel.sendMessage("Invalid operator. Valid operators are add, subtract (sub), and set.");

                            // Update table
                            player.getTable().update(guild);
                            return;
                        }
                    }

                    channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                    return;
                }
            }

            channel.sendMessage(Msges.NOT_DEALER);
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
