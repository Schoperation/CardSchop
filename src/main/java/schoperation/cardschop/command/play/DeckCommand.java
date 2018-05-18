package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Suit;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.Iterator;

public class DeckCommand implements ICommand {

    /*
        Edits the deck.

        deck [operator] [card(s)] -> Adds/removes the specified card(s).

        Operator can be:
            -add -> Add a card
            -remove -> Remove a card
            -reset -> Resets the deck to the normal 52 cards.

        You can specify one card (by name only, such as 7ofclubs), or omit the suit to remove all cards of that rank.

        Specify 'joker' for one joker, 'jokers' for both jokers.
     */

    private final String command = "deck";

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
            // Check if dealer
            Player player = Utils.getPlayerObj(sender, guild);
            Table table = player.getTable();

            if (player.getTable().getDealer() == player)
            {
                // First argument
                if (arg1.equals("blank"))
                {
                    channel.sendMessage("Please provide proper arguments. `" + Msges.PREFIX + "deck [operator] [card]`.");
                    return;
                }
                else if (arg1.equals("add"))
                {
                    // Second argument.
                    if (arg2.equals("blank"))
                    {
                        channel.sendMessage("Please provide proper arguments. `" + Msges.PREFIX + "deck [operator] [card]`.");
                        return;
                    }
                    else if (arg2.equals("joker"))
                    {
                        // Add a single joker.
                        table.getDeck().getCards().add(new Card());
                        channel.sendMessage("The dealer added a single joker to the deck.");
                    }
                    else if (arg2.equals("jokers"))
                    {
                        // Add two jokers.
                        table.getDeck().getCards().add(new Card());
                        table.getDeck().getCards().add(new Card());
                        channel.sendMessage("The dealer added 2 jokers to the deck.");
                    }

                    // Try to parse it as an int, then a card
                    else
                    {
                        // Integer? We'll add all cards of that rank.
                        if (Utils.isInt(arg2))
                        {
                            int rank = Integer.parseInt(arg2);

                            table.getDeck().getCards().add(new Card(Suit.CLUBS, rank));
                            table.getDeck().getCards().add(new Card(Suit.DIAMONDS, rank));
                            table.getDeck().getCards().add(new Card(Suit.HEARTS, rank));
                            table.getDeck().getCards().add(new Card(Suit.SPADES, rank));

                            channel.sendMessage("The dealer has added all " + rank + "s.");
                        }

                        // Not an int. Maybe a face card/ace by itself.
                        else if (arg2.equals("ace") || arg2.equals("aces") || arg2.equals("a"))
                        {
                            table.getDeck().getCards().add(new Card(Suit.CLUBS, 14));
                            table.getDeck().getCards().add(new Card(Suit.DIAMONDS, 14));
                            table.getDeck().getCards().add(new Card(Suit.HEARTS, 14));
                            table.getDeck().getCards().add(new Card(Suit.SPADES, 14));

                            channel.sendMessage("The dealer has added all aces.");
                        }
                        else if (arg2.equals("king") || arg2.equals("kings") || arg2.equals("k"))
                        {
                            table.getDeck().getCards().add(new Card(Suit.CLUBS, 13));
                            table.getDeck().getCards().add(new Card(Suit.DIAMONDS, 13));
                            table.getDeck().getCards().add(new Card(Suit.HEARTS, 13));
                            table.getDeck().getCards().add(new Card(Suit.SPADES, 13));

                            channel.sendMessage("The dealer has added all kings.");
                        }
                        else if (arg2.equals("queen") || arg2.equals("queens") || arg2.equals("q"))
                        {
                            table.getDeck().getCards().add(new Card(Suit.CLUBS, 12));
                            table.getDeck().getCards().add(new Card(Suit.DIAMONDS, 12));
                            table.getDeck().getCards().add(new Card(Suit.HEARTS, 12));
                            table.getDeck().getCards().add(new Card(Suit.SPADES, 12));

                            channel.sendMessage("The dealer has added all queens.");
                        }
                        else if (arg2.equals("jack") || arg2.equals("jacks") || arg2.equals("j"))
                        {
                            table.getDeck().getCards().add(new Card(Suit.CLUBS, 11));
                            table.getDeck().getCards().add(new Card(Suit.DIAMONDS, 11));
                            table.getDeck().getCards().add(new Card(Suit.HEARTS, 11));
                            table.getDeck().getCards().add(new Card(Suit.SPADES, 11));

                            channel.sendMessage("The dealer has added all jacks.");
                        }

                        // Parse it as a card then.
                        else
                        {
                            Card card = Card.parseCard(arg2);

                            if (card == null)
                            {
                                channel.sendMessage(Msges.INVALID_CARD);
                                return;
                            }

                            table.getDeck().getCards().add(card);
                            channel.sendMessage("The dealer has added the " + card.getString() + ".");
                        }
                    }
                }
                else if (arg1.equals("remove"))
                {
                    // Second argument.
                    if (arg2.equals("blank"))
                    {
                        channel.sendMessage("Please provide proper arguments. `" + Msges.PREFIX + "deck [operator] [card]`.");
                        return;
                    }
                    else if (arg2.equals("joker"))
                    {
                        // Remove a single joker.
                        Iterator<Card> iterator = table.getDeck().getCards().iterator();
                        while (iterator.hasNext())
                        {
                            Card card = iterator.next();

                            if (card.getValue() == 15)
                            {
                                iterator.remove();
                                break;
                            }
                        }

                        channel.sendMessage("The dealer removed a single joker from the deck.");
                    }
                    else if (arg2.equals("jokers"))
                    {
                        // Remove all jokers.
                        Iterator<Card> iterator = table.getDeck().getCards().iterator();
                        while (iterator.hasNext())
                        {
                            Card card = iterator.next();

                            if (card.getValue() == 15)
                                iterator.remove();
                        }

                        channel.sendMessage("The dealer removed all jokers from the deck.");
                    }

                    // Try to parse it as an int, then a card
                    else
                    {
                        // Integer? We'll remove all cards of that rank.
                        if (Utils.isInt(arg2))
                        {
                            int rank = Integer.parseInt(arg2);

                            Iterator<Card> iterator = table.getDeck().getCards().iterator();
                            while (iterator.hasNext())
                            {
                                Card card = iterator.next();

                                if (card.getValue() == rank)
                                    iterator.remove();
                            }

                            channel.sendMessage("The dealer has removed all " + rank + "s.");
                        }

                        // Not an int. Maybe a face card/ace by itself.
                        else if (arg2.equals("ace") || arg2.equals("aces") || arg2.equals("a"))
                        {
                            Iterator<Card> iterator = table.getDeck().getCards().iterator();
                            while (iterator.hasNext())
                            {
                                Card card = iterator.next();

                                if (card.getValue() == 14)
                                    iterator.remove();
                            }

                            channel.sendMessage("The dealer has removed all aces.");
                        }
                        else if (arg2.equals("king") || arg2.equals("kings") || arg2.equals("k"))
                        {
                            Iterator<Card> iterator = table.getDeck().getCards().iterator();
                            while (iterator.hasNext())
                            {
                                Card card = iterator.next();

                                if (card.getValue() == 13)
                                    iterator.remove();
                            }

                            channel.sendMessage("The dealer has removed all kings.");
                        }
                        else if (arg2.equals("queen") || arg2.equals("queens") || arg2.equals("q"))
                        {
                            Iterator<Card> iterator = table.getDeck().getCards().iterator();
                            while (iterator.hasNext())
                            {
                                Card card = iterator.next();

                                if (card.getValue() == 12)
                                    iterator.remove();
                            }

                            channel.sendMessage("The dealer has removed all queens.");
                        }
                        else if (arg2.equals("jack") || arg2.equals("jacks") || arg2.equals("j"))
                        {
                            Iterator<Card> iterator = table.getDeck().getCards().iterator();
                            while (iterator.hasNext())
                            {
                                Card card = iterator.next();

                                if (card.getValue() == 11)
                                    iterator.remove();
                            }

                            channel.sendMessage("The dealer has removed all jacks.");
                        }

                        // Parse it as a card then.
                        else
                        {
                            Card card = Card.parseCard(arg2);

                            if (card == null)
                            {
                                channel.sendMessage(Msges.INVALID_CARD);
                                return;
                            }

                            Iterator<Card> iterator = table.getDeck().getCards().iterator();
                            while (iterator.hasNext())
                            {
                                Card card1 = iterator.next();

                                if (card1.getValue() == card.getValue() && card1.getSuit() == card.getSuit())
                                {
                                    iterator.remove();
                                    break;
                                }
                            }

                            channel.sendMessage("The dealer has removed the " + card.getString() + ".");
                        }
                    }
                }
                else if (arg1.equals("reset"))
                {
                    // Clear the deck and refill it.
                    table.getDeck().getCards().clear();
                    table.getDeck().fill(false);

                    channel.sendMessage("The dealer reset the deck.");
                }
                else
                {
                    channel.sendMessage("Invalid operator. Your options are: add, remove, and reset.");
                    return;
                }

                // Update table
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
