package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;

public class DrawCommand implements ICommand {

    /*
        Draws a card from the specified place.

        draw [place] [amount] -> takes [amount] cards from [place].

        No amount? Just one, by default. Use 'all' just to take all cards.

        Available places:
            -deck -> Takes a card from the deck.
            -middle -> the pile right by the deck. Usually "discard pile", or games like egyptian rat screw.
            -pile -> player's personal pile.
            -infront -> in front of the player. For tricks.

            Defaults to deck.
     */

    private final String command = "draw";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);
            Table table = player.getTable();

            // No arguments? Draw one card from the deck.
            if (arg1.equals("blank"))
            {

                // Is there a card in the deck?
                if(table.getDeck().getNumberOfCards() < 1)
                {
                    channel.createMessage(Msges.EMPTY_PILE);
                    return;
                }
                else
                {
                    // Take a card from the deck and add it to the player's hand.
                    Card card = table.getDeck().getCards().remove(table.getDeck().getNumberOfCards() - 1);
                    player.addCard(card);
                    channel.createMessage(player.getDisplayName() + " has drawn a card from the deck.");
                }
                
                // Update hand and table
                SeeCommand.seeHand(player);
                table.update(guild);
                return;
            }
            
            // One argument? Draw one card from [place].
            else if (arg2.equals("blank"))
            {

                // Go through the spots.
                if (arg1.equals("deck"))
                {
                    Card card = table.getDeck().getCards().remove(table.getDeck().getNumberOfCards() - 1);
                    player.addCard(card);
                    channel.createMessage(player.getDisplayName() + " has drawn a card from the deck.");
                }
                else if (arg1.equals("middle"))
                {
                    Card card = table.getMiddlePile().remove(table.getMiddlePile().size() - 1);
                    player.addCard(card);
                    channel.createMessage(player.getDisplayName() + " has drawn a card from the middle pile.");
                }
                else if (arg1.equals("pile"))
                {
                    Card card = player.getPile().remove(player.getPile().size() - 1);
                    player.addCard(card);
                    channel.createMessage(player.getDisplayName() + " has drawn a card from their personal pile.");
                }
                else if (arg1.equals("infront") || arg1.equals("trick"))
                {
                    Card card = player.getFront().remove(player.getFront().size() - 1);
                    player.addCard(card);
                    channel.createMessage(player.getDisplayName() + " has drawn a card from the pile in front of them.");
                }
                else
                {
                    channel.createMessage(Msges.INVALID_PLACE_DRAW);
                    return;
                }

                // Update hand and table
                SeeCommand.seeHand(player);
                table.update(guild);
                return;
            }
            // Two arguments. Place and amount of cards.
            else
            {
                // Card amount
                int amount;

                if (arg2.equals("all"))
                    amount = 1000;
                else if (Utils.isInt(arg2))
                    amount = Integer.parseInt(arg2);
                else
                {
                    channel.createMessage(Msges.NAN);
                    return;
                }

                int i = 0;

                // Go through the spots.
                if (arg1.equals("deck"))
                {
                    while (i < amount && !table.getDeck().getCards().isEmpty())
                    {
                        Card card = table.getDeck().getCards().remove(table.getDeck().getNumberOfCards() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.createMessage(player.getDisplayName() + " drew " + amount + " cards from the deck.");
                }
                else if (arg1.equals("middle"))
                {
                    while (i < amount && !table.getMiddlePile().isEmpty())
                    {
                        Card card = table.getMiddlePile().remove(table.getMiddlePile().size() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.createMessage(player.getDisplayName() + " drew " + amount + " cards from the middle pile.");
                }
                else if (arg1.equals("pile"))
                {
                    while (i < amount && !player.getPile().isEmpty())
                    {
                        Card card = player.getPile().remove(player.getPile().size() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.createMessage(player.getDisplayName() + " drew " + amount + " cards from their personal pile.");
                }
                else if (arg1.equals("infront") || arg1.equals("trick"))
                {
                    while (i < amount && !player.getFront().isEmpty())
                    {
                        Card card = player.getFront().remove(player.getFront().size() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.createMessage(player.getDisplayName() + " drew " + amount + " cards from the pile in front of them.");
                }
                else
                {
                    channel.createMessage(Msges.INVALID_PLACE_DRAW);
                    return;
                }

                // Update hand and table
                SeeCommand.seeHand(player);
                table.update(guild);
                return;
            }
        }

        channel.createMessage(Msges.NO_TABLE);
        return;
    }
}
