package schoperation.cardschop.card;

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

    // Chips (soon tm)
    private int chips;

    public Player(IUser u, Table t)
    {
        this.user = u;
        this.table = t;

        if (this.user != null)
            this.pm = this.user.getOrCreatePMChannel().sendMessage("Your hand will appear here.");
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

    public int getChips()
    {
        return this.chips;
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

    // Remove specified card (index)
    public Card removeCard(int index)
    {
        Card card = this.hand.remove(index);
        return card;
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
