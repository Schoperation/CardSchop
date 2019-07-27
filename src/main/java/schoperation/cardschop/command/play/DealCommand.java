package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class DealCommand implements ICommand {

    /*
        Deals cards.

        deal [perplayer] [atatime] [dealergetscards] -> deals [perplayer] cards to every way, [atatime] cards at a time.
     */

    private final String command = "deal";

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

            // Table
            Table table = player.getTable();

            // Check if dealer
            if (table.getDealer() == player)
            {
                // Argument checking
                if (arg1.equals("blank"))
                {
                    table.dealCards(0, 1, true, player);
                    PostalService.sendMessage(channel, "Dealt all cards to everyone, one at a time.");
                }
                else if (arg2.equals("blank"))
                {
                    if (!Utils.isInt(arg1))
                    {
                        PostalService.sendMessage(channel, Msges.NAN);
                        return;
                    }

                    table.dealCards(Integer.parseInt(arg1), 1, true, player);
                    PostalService.sendMessage(channel, "Dealt " + arg1 + " cards to everyone, one at a time.");
                }
                else if (arg3.equals("blank"))
                {
                    if (!Utils.isInt(arg1))
                    {
                        PostalService.sendMessage(channel, Msges.NAN);
                        return;
                    }

                    // Could be a username
                    if (Utils.isInt(arg2))
                    {
                        table.dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), true, player);
                        PostalService.sendMessage(channel, "Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time.");
                    }
                    else
                    {
                        arg2 = arg2.replaceAll("[<>@!]", "");
                        Member userFromString = guild.getMemberById(Snowflake.of(Long.parseLong(arg2))).block();

                        // Same table?
                        if (Utils.isPartOfTable(userFromString, guild))
                        {
                            Player player1 = Utils.getPlayerObj(userFromString, guild);

                            if (player1.getTable() == table)
                            {
                                table.dealCardsToSinglePlayer(Integer.parseInt(arg1), player1);
                                PostalService.sendMessage(channel, "Dealt " + arg1 + " cards to " + userFromString.getDisplayName() + ".");
                            }
                        }
                        else
                        {
                            PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
                            return;
                        }
                    }
                }
                else
                {
                    if (!Utils.isInt(arg1) || !Utils.isInt(arg2))
                    {
                        PostalService.sendMessage(channel, Msges.NAN);
                        return;
                    }

                    table.dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), Boolean.parseBoolean(arg3), player);
                    PostalService.sendMessage(channel, "Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time. Dealer got cards = " + arg3 + ".");
                }

                // Update hands and table
                for (Player p : table.getPlayers())
                    SeeCommand.seeHand(p);

                table.update(guild);
                return;
            }
            else
            {
                PostalService.sendMessage(channel, Msges.NOT_DEALER);
                return;
            }
        }

        PostalService.sendMessage(channel, Msges.NO_TABLE);
        return;
    }
}
