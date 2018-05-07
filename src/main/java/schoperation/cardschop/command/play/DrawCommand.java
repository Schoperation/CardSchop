package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

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

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {
        // Part of a table?
        if (Utils.isPartOfTable(sender))
        {
            Player player = Utils.getPlayerObj(sender);
            Table table = player.getTable();

            // No arguments? Draw one card from the deck.
            if (arg1.equals("blank"))
            {

                // Is there a card in the deck?
                if(table.getDeck().getNumberOfCards() < 1)
                {
                    channel.sendMessage(Msges.EMPTY_PILE);
                    return;
                }
                else
                {
                    // Take a card from the deck and add it to the player's hand.
                    Card card = table.getDeck().getCards().remove(table.getDeck().getNumberOfCards() - 1);
                    player.addCard(card);
                    channel.sendMessage(player.getUser().getDisplayName(guild) + " has drawn a card from the deck.");
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
                if (arg1.toLowerCase().equals("deck"))
                {
                    Card card = table.getDeck().getCards().remove(table.getDeck().getNumberOfCards() - 1);
                    player.addCard(card);
                    channel.sendMessage(player.getUser().getDisplayName(guild) + " has drawn a card from the deck.");
                }
                else if (arg1.toLowerCase().equals("middle"))
                {
                    Card card = table.getMiddlePile().remove(table.getMiddlePile().size() - 1);
                    player.addCard(card);
                    channel.sendMessage(player.getUser().getDisplayName(guild) + " has drawn a card from the middle pile.");
                }
                else if (arg1.toLowerCase().equals("pile"))
                {
                    Card card = player.getPile().remove(player.getPile().size() - 1);
                    player.addCard(card);
                    channel.sendMessage(player.getUser().getDisplayName(guild) + " has drawn a card from their personal pile.");
                }
                else if (arg1.toLowerCase().equals("infront") || arg1.toLowerCase().equals("trick"))
                {
                    Card card = player.getFront().remove(player.getFront().size() - 1);
                    player.addCard(card);
                    channel.sendMessage(player.getUser().getDisplayName(guild) + " has drawn a card from the pile in front of them.");
                }
                else
                {
                    channel.sendMessage(Msges.INVALID_PLACE_DRAW);
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
                    amount = 200;
                else
                    amount = Integer.parseInt(arg2);

                int i = 0;

                // Go through the spots.
                if (arg1.toLowerCase().equals("deck"))
                {
                    while (i < amount && !table.getDeck().getCards().isEmpty())
                    {
                        Card card = table.getDeck().getCards().remove(table.getDeck().getNumberOfCards() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.sendMessage(player.getUser().getDisplayName(guild) + " drew " + amount + " cards from the deck.");
                }
                else if (arg1.toLowerCase().equals("middle"))
                {
                    while (i < amount && !table.getMiddlePile().isEmpty())
                    {
                        Card card = table.getMiddlePile().remove(table.getMiddlePile().size() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.sendMessage(player.getUser().getDisplayName(guild) + " drew " + amount + " cards from the middle pile.");
                }
                else if (arg1.toLowerCase().equals("pile"))
                {
                    while (i < amount && !player.getPile().isEmpty())
                    {
                        Card card = player.getPile().remove(player.getPile().size() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.sendMessage(player.getUser().getDisplayName(guild) + " drew " + amount + " cards from their personal pile.");
                }
                else if (arg1.toLowerCase().equals("infront"))
                {
                    while (i < amount && !player.getFront().isEmpty())
                    {
                        Card card = player.getFront().remove(player.getFront().size() - 1);
                        player.addCard(card);
                        i++;
                    }

                    channel.sendMessage(player.getUser().getDisplayName(guild) + " drew " + amount + " cards from the pile in front of them.");
                }
                else
                {
                    channel.sendMessage(Msges.INVALID_PLACE_DRAW);
                    return;
                }

                // Update hand and table
                SeeCommand.seeHand(player);
                table.update(guild);
                return;
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
