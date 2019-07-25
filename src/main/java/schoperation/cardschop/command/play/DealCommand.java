package schoperation.cardschop.command.play;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class DealCommand implements ICommand {

    /*
        Deals cards.

        deal [perplayer] [atatime] [dealergetscards] -> deals [perplayer] cards to every way, [atatime] cards at a time.
     */

    private final String command = "deal";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // Table
            Table table = player.getTable();

            // Check if dealer
            if (table.getDealer() == player)
            {
                // Argument checking
                if (arg1.equals("blank"))
                {
                    table.dealCards(0, 1, true, player);
                    channel.sendMessage("Dealt all cards to everyone, one at a time.");
                }
                else if (arg2.equals("blank"))
                {
                    if (!Utils.isInt(arg1))
                    {
                        channel.sendMessage(Msges.NAN);
                        return;
                    }

                    table.dealCards(Integer.parseInt(arg1), 1, true, player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, one at a time.");
                }
                else if (arg3.equals("blank"))
                {
                    if (!Utils.isInt(arg1))
                    {
                        channel.sendMessage(Msges.NAN);
                        return;
                    }

                    // Could be a username
                    if (Utils.isInt(arg2))
                    {
                        table.dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), true, player);
                        channel.sendMessage("Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time.");
                    }
                    else
                    {
                        arg2 = arg2.replaceAll("[<>@!]", "");
                        IUser userFromString = guild.getUserByID(Long.parseLong(arg2));

                        // Same table?
                        if (Utils.isPartOfTable(userFromString, guild))
                        {
                            Player player1 = Utils.getPlayerObj(userFromString, guild);

                            if (player1.getTable() == table)
                            {
                                table.dealCardsToSinglePlayer(Integer.parseInt(arg1), player1);
                                channel.sendMessage("Dealt " + arg1 + " cards to " + userFromString.getDisplayName(guild) + ".");
                            }
                        }
                        else
                        {
                            channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                            return;
                        }
                    }
                }
                else
                {
                    if (!Utils.isInt(arg1) || !Utils.isInt(arg2))
                    {
                        channel.sendMessage(Msges.NAN);
                        return;
                    }

                    table.dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), Boolean.parseBoolean(arg3), player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time. Dealer got cards = " + arg3 + ".");
                }

                // Update hands and table
                for (Player p : table.getPlayers())
                    SeeCommand.seeHand(p);

                table.update(guild);
                return;
            }
            else
            {
                channel.sendMessage(Msges.NOT_DEALER);
                return;
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
