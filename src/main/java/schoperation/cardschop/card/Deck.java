package schoperation.cardschop.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Deck {

    /*
        Holds the cards. Not much else to say here.
     */

    // The list. Wow.
    private List<Card> cards = new ArrayList<>();

    public Deck()
    {
        this.fill(false);
    }

    public Deck(boolean withJoker)
    {
        this.fill(withJoker);
    }

    // Returns the amount of cards.
    public int getNumberOfCards()
    {
        return this.cards.size();
    }

    // Returns the actual contents.
    public List<Card> getCards()
    {
        return this.cards;
    }

    // Shuffles the deck.
    public void shuffle()
    {
        Collections.shuffle(this.cards);
        return;
    }

    // Adds a card to the BOTTOM of the deck. Normal getCards().remove() removes from the end of the list, which is the top.
    // This is a special method to shift the entire deck to the right.
    public void addToBottom(Card card)
    {
        // Add a dummy card to the deck. We'll just use the given card.
        this.cards.add(card);

        // Shift the cards to the right, starting from the top of the deck.
        int i;
        for (i = this.cards.size() - 1; i > 0; i--)
            this.cards.set(i, this.cards.get(i - 1));

        // Now set the given card to the top.
        this.cards.set(0, card);

        return;
    }

    // Converts the contents of the deck into a printable string.
    public String getCardsToString()
    {
        StringBuilder sb = new StringBuilder();
        Iterator<Card> iterator = this.cards.iterator();
        int i = 0;

        while (iterator.hasNext())
        {
            Card card = iterator.next();
            sb.append(card.getString());

            if (i % 13 == 12)
                sb.append("\n");
            else
                sb.append(", ");

            i++;
        }

        return sb.toString();
    }

    // Fills it up with cards
    private void fill(boolean withJoker)
    {
        // Add all 52 cards to the deck
        int i;
        Suit s;
        Card card;
        for (i = 0; i < 52; i++)
        {
            if (i < 13)
                s = Suit.CLUBS;
            else if (i < 26)
                s = Suit.DIAMONDS;
            else if (i < 39)
                s = Suit.HEARTS;
            else
                s = Suit.SPADES;

            card = new Card(s, i % 13 + 2);
            this.cards.add(card);
        }

        // Jokers?
        if (withJoker)
        {
            card = new Card();
            Card card2 = new Card();
            this.cards.add(card);
            this.cards.add(card2);
            return;
        }
        else
            return;
    }
}
