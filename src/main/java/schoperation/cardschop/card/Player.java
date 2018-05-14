package schoperation.cardschop.card;

import schoperation.cardschop.command.play.SeeCommand;
import schoperation.cardschop.util.Msges;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

    /*
        A player, part of a table.
     */

    // Player himself
    private IUser user;

    // Player's hand
    private List<Card> hand = new ArrayList<>();

    // The pile of cards by the player's side.
    private List<Card> pile = new ArrayList<>();

    // The pile of cards in front of the player (for tricks, mainly).
    private List<Card> front = new ArrayList<>();

    // The private message that will hold the player's hand.
    private IMessage pm;

    // Table
    private Table table;

    // Chips
    private int chips = 0;

    public Player(IUser u, Table t)
    {
        this.user = u;
        this.table = t;

        if (this.user != null)
            this.pm = this.user.getOrCreatePMChannel().sendMessage("Your hand will appear here. Use `" + Msges.PREFIX + "see` to update it, if ever necessary. You can also use `" + Msges.PREFIX + "see pile` for your personal pile, and `" + Msges.PREFIX + "see infront` for the cards in front of you (trick).");
    }

    public IUser getUser()
    {
        return this.user;
    }

    public Table getTable()
    {
        return this.table;
    }

    public IMessage getPM()
    {
        return this.pm;
    }

    public List<Card> getPile()
    {
        return this.pile;
    }

    public List<Card> getFront()
    {
        return this.front;
    }

    /*
        Player's chips methods
     */

    public int getChips()
    {
        return this.chips;
    }

    public void setChips(int c)
    {
        this.chips = c;
        return;
    }

    public void addChips(int c)
    {
        this.chips += c;
        return;
    }

    public void subtractChips(int c)
    {
        this.chips -= c;

        if (this.chips < 0)
            this.chips = 0;

        return;
    }

    /*
        These methods deal with the player's hand.
     */

    public List<Card> getHand()
    {
        return this.hand;
    }

    public int getNumOfCards()
    {
        return this.hand.size();
    }

    public void sortHand(int mode)
    {
        /*
            Ex hand: 2 of clubs, 2 of hearts, 7 of diamonds, 8 of spades, ace of spades, ace of diamonds.
            Mode 1 (by rank): 2 of clubs, 2 of hearts, 7 of diamonds, 8 of spades, ace of diamonds, ace of spades.
            Mode 2 (by suit): 2 of clubs, 7 of diamonds, ace of diamonds, 2 of hearts, 8 of spades, ace of spades.

            Suits are in alphabetical order: clubs, diamonds, hearts, and spades.
            By rank acknowledges this suit order but will always put rank at top priority. So aces will ALWAYS be at the top by rank.
        */

        int i;
        int j;

        Card minCard;
        int minIndex;

        int value;
        Suit suit;

        for (i = 0; i < this.hand.size() - 1; i++)
        {
            // Set the minimum to the current index we're trying to replace.
            minCard = this.hand.get(i);
            minIndex = i;

            for (j = i + 1; j < this.hand.size(); j++)
            {
                value = this.hand.get(j).getValue();
                suit = this.hand.get(j).getSuit();

                // Is this card, by definition, lower than the current minimum card?
                if (mode == 1)
                {
                    // Lower rank
                    if (value < minCard.getValue())
                    {
                        minIndex = j;
                        minCard = this.hand.get(j);
                    }
                    // Same rank. Check suits.
                    else if (value == minCard.getValue())
                    {
                        if (hasLowerSuit(j, minIndex))
                        {
                            minIndex = j;
                            minCard = this.hand.get(j);
                        }
                    }
                }
                else if (mode == 2)
                {
                    // Lower suit?
                    if (hasLowerSuit(j, minIndex))
                    {
                        minIndex = j;
                        minCard = this.hand.get(j);
                    }
                    // Same suit? Then check rank.
                    else if (suit == minCard.getSuit())
                    {
                        if (value < minCard.getValue())
                        {
                            minIndex = j;
                            minCard = this.hand.get(j);
                        }
                    }
                }
            }

            // Swap cards
            swap(i, minIndex);
        }

        // Notify
        SeeCommand.seeHand(this);
        return;
    }

    // Used for the sorting method above
    private void swap(int index1, int index2)
    {
        Card temp = this.hand.get(index1);
        this.hand.set(index1, this.hand.get(index2));
        this.hand.set(index2, temp);
        return;
    }

    // Used for the sorting method above above
    // Returns true if index1 has a lower suit than index2.
    private boolean hasLowerSuit(int index1, int index2)
    {
        if (this.hand.get(index1).getSuit() == this.hand.get(index2).getSuit())
            return false;
        else if (this.hand.get(index2).getSuit() == Suit.SPADES)
            return true;
        else if (this.hand.get(index2).getSuit() == Suit.HEARTS && this.hand.get(index1).getSuit() != Suit.SPADES)
            return true;
        else if (this.hand.get(index1).getSuit() == Suit.CLUBS)
            return true;
        else
            return false;
    }

    /*
        Finding and getting cards from their hand
     */

    public boolean hasCard(Card card)
    {
        for (Card c : this.hand)
        {
            if (c.getSuit() == card.getSuit() && c.getValue() == card.getValue())
                return true;
        }

        return false;
    }

    public Card getCardByCard(Card card)
    {
        for (Card c : this.hand)
        {
            if (c.getSuit() == card.getSuit() && c.getValue() == card.getValue())
                return c;
        }

        return null;
    }

    public void clearHand()
    {
        this.hand.clear();
        return;
    }

    public void addCard(Card card)
    {
        this.hand.add(card);
        return;
    }

    // Removes card from top of hand
    public Card removeCard()
    {
        Card card = this.hand.remove(this.hand.size() - 1);
        return card;
    }

    // Remove specified card (object)
    public void removeCard(Card card)
    {
        this.hand.remove(card);
        return;
    }

    // Returns a string showing off the hand.
    public String handToString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<Card> iterator = this.hand.iterator();
        int i = 1;

        while (iterator.hasNext())
        {
            // Index number
            sb.append("[" + i + "] ");

            Card card = iterator.next();
            sb.append(card.getString());
            sb.append("\n");

            i++;
        }

        return sb.toString();
    }

    // Returns a string showing off the side pile.
    public String pileToString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<Card> iterator = this.pile.iterator();
        int i = 1;

        while (iterator.hasNext())
        {
            // Index number
            sb.append("[" + i + "] ");

            Card card = iterator.next();
            sb.append(card.getString());
            sb.append("\n");

            i++;
        }

        return sb.toString();
    }

    // Returns a string showing off the front pile.
    public String frontToString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<Card> iterator = this.front.iterator();
        int i = 1;

        while (iterator.hasNext())
        {
            // Index number
            sb.append("[" + i + "] ");

            Card card = iterator.next();
            sb.append(card.getString());
            sb.append("\n");

            i++;
        }

        return sb.toString();
    }
}
