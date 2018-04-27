package schoperation.cardschop.card;

import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;

public class Player {

    /*
        A player, part of a table.
     */

    // Player himself
    private IUser user;

    // Player's hand
    private List<Card> hand = new ArrayList<>();

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

    public void emptyHand()
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
}
