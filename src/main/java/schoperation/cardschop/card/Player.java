package schoperation.cardschop.card;

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

    // Table
    private Table table;

    // Chips (soon tm)
    private int chips;

    public Player(IUser u, Table t)
    {
        this.user = u;
        this.table = t;
    }

    public IUser getUser()
    {
        return this.user;
    }

    public Table getTable()
    {
        return this.table;
    }

    public int getChips()
    {
        return this.chips;
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
    public void removeCard()
    {
        this.hand.remove(this.hand.size() - 1);
        return;
    }

    // Remove specified card (index)
    public void removeCard(int index)
    {
        this.hand.remove(index);
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
}
