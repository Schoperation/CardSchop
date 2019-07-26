package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;

public class CollectCommand implements ICommand {

    /*
        Collects something.

        collect [something] -> collects the specified thing. Nothing by default.

        Options:
            -cards -> Eveeryone's cards, back into the deck. Dealer only.
            -trick/infront -> Takes everyone's infront cards, and puts them into the player's personal pile.
            -middle -> Takes the middle cards and puts them into the player's hand.
            -pot -> Takes the pot on the table.

     */

    private final String command = "collect";

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
            Table table = player.getTable();

            // No arguments
            if (arg1.equals("blank"))
            {
                channel.createMessage(Msges.COLLECT_ARGUMENT);
                return;
            }

            // One argument
            else
            {
                // Go through possibilities.
                if (arg1.equals("cards"))
                {
                    // They must be the dealer to do this.
                    if (table.getDealer() == player)
                    {
                        table.collectCards();
                        channel.createMessage("The dealer collected everyone's cards.");

                        for (Player p : table.getPlayers())
                            SeeCommand.seeHand(p);
                    }
                    else
                    {
                        channel.createMessage(Msges.NOT_DEALER);
                        return;
                    }
                }
                else if (arg1.equals("trick") || arg1.equals("infront"))
                {
                    // Go through everyone's front piles and add them to this player's personal pile.
                    for (Player p : table.getPlayers())
                    {
                        if (!p.getFront().isEmpty())
                        {
                            player.getPile().addAll(p.getFront());
                            p.getFront().clear();
                            channel.createMessage(player.getDisplayName() + " has taken the trick.");
                        }
                    }
                }
                else if (arg1.equals("middle"))
                {
                    // Take the middle pile and put it into the player's personal pile.
                    if (!table.getMiddlePile().isEmpty())
                    {
                        player.getPile().addAll(table.getMiddlePile());
                        table.getMiddlePile().clear();
                        channel.createMessage(player.getDisplayName() + " has taken the middle pile.");
                        SeeCommand.seeHand(player);
                    }

                }
                else if (arg1.equals("pot"))
                {
                    // Amount
                    int amount = table.getPot();
                    table.takeFromPot(amount);
                    player.addChips(amount);
                    channel.createMessage(player.getDisplayName() + " has collected the pot from the table.");
                }
                else
                {
                    channel.createMessage(Msges.COLLECT_ARGUMENT);
                    return;
                }

                // Update table
                table.update(guild);
                return;
            }
        }

        channel.createMessage(Msges.NO_TABLE);
        return;
    }
}
