package schoperation.cardschop.card;

import schoperation.cardschop.util.Utils;

public class Card {

    /*
        A card. What else?
     */

    // Suit enum
    private Suit suit;

    // Used to designate card value. 2 = 2, 3 = 3,... J = 11, Q = 12, K = 13, A = 14
    private int value;

    // Is this a joker?
    private boolean isJoker;

    // Faceup or facedown?
    private boolean isFaceDown = false;

    // Normal constructor
    public Card(Suit s, int v)
    {
        this.suit = s;
        this.value = v;
        this.isJoker = false;
    }

    // Joker
    public Card()
    {
        this.isJoker = true;
        this.value = 15;
        this.suit = Suit.SPADES;
    }

    public Suit getSuit()
    {
        return this.suit;
    }

    public int getValue()
    {
        return this.value;
    }

    public boolean isFaceDown()
    {
        return this.isFaceDown;
    }

    public void flip()
    {
        if (this.isFaceDown)
            this.isFaceDown = false;
        else
            this.isFaceDown = true;
        return;
    }

    public void setFaceDown()
    {
        this.isFaceDown = true;
        return;
    }

    public void setFaceUp()
    {
        this.isFaceDown = false;
        return;
    }

    // Returns a string that can be printed out.
    public String getString()
    {
        StringBuilder sb = new StringBuilder();

        // If the card is facedown, dont show the card. Show something.
        if (this.isFaceDown)
            sb.append(":mahjong:  ");
        else
        {
            // First, the card value itself.
            sb.append(this.getPicture());

            // Now the suit.
            if (this.isJoker)
                ;
            else if (this.suit == Suit.CLUBS)
                sb.append(":clubs:");
            else if (this.suit == Suit.DIAMONDS)
                sb.append(":diamonds:");
            else if (this.suit == Suit.HEARTS)
                sb.append(":hearts:");
            else
                sb.append(":spades:");
        }

        return sb.toString();
    }

    // Used to help differentiate face cards from normal cards
    private String getPicture()
    {
        if (this.value < 11 && this.value != 1)
            return Integer.toString(this.value);
        else if (this.value == 11)
            return "J";
        else if (this.value == 12)
            return "Q";
        else if (this.value == 13)
            return "K";
        else if (this.value == 14)
            return "A";
        else
            return ":black_joker:";
    }

    // Parse a string and return a card.
    public static Card parseCard(String s)
    {
        /*
            Let's take the 2 of clubs as our example. This method will detect it as either

                2clubs
                2ofclubs

            Face cards are a bit different. Jack of hearts, for example:

                jhearts
                jackhearts
                jofhearts
                jackofhearts

            Joker is just "joker"

            Figure out if it's a joker first. If not, then
            Get the first char and figure out if its an integer or a j,k,q,a.
            Then parse the suit.
            We can safely assume it is all lowercase as the command processor makes the command all lowercase anyway.
         */

        // Joker?
        if (s.equals("joker"))
            return new Card();

        // Not joker
        char first = s.charAt(0);

        // Used for creating the card
        int rank;
        Suit suit;

        // Number?
        if (Character.isDigit(first))
        {
            // Yes!

            // If a 1, it's just part of a 10
            if (first == '1')
                rank = 10;
            else
                rank = Integer.parseInt(Character.toString(first));
        }
        else
        {
            // No.
            if (first == 'j')
                rank = 11;
            else if (first == 'q')
                rank = 12;
            else if (first == 'k')
                rank = 13;
            else if (first == 'a')
                rank = 14;
            else
                rank = 0;
        }

        // Now search the string for any of the suits.
        if (s.contains("clubs"))
            suit = Suit.CLUBS;
        else if (s.contains("diamonds"))
            suit = Suit.DIAMONDS;
        else if (s.contains("hearts"))
            suit = Suit.HEARTS;
        else if (s.contains("spades"))
            suit = Suit.SPADES;
        else
            suit = null;

        // Combine the rank and suit.
        if (rank != 0 && suit != null)
            return new Card(suit, rank);
        else
            return null;
    }
}
