package main.java.schoperation.cardschop.card;

public class Card {

    /*
        A card. What else?
     */

    // Suit enum
    private Suit suit;

    // Used to designate card value. Ace = 1, 2 = 2, 3 = 3,... J = 11, Q = 12, K = 13
    private int value;

    private boolean isJoker;

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
        this.value = -1;
        this.suit = Suit.HEARTS;
    }

    public Suit getSuit()
    {
        return this.suit;
    }

    public int getValue()
    {
        return this.value;
    }

    // Returns a string that can be printed out.
    public String toPrinted()
    {
        StringBuilder sb = new StringBuilder();

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

        return sb.toString();
    }

    // Used to help differentiate face cards from normal cards
    private String getPicture()
    {
        if (this.value < 11 && this.value != 1)
            return Integer.toString(this.value);
        else if (this.value == 1)
            return "A";
        else if (this.value == 11)
            return "J";
        else if (this.value == 12)
            return "Q";
        else if (this.value == 13)
            return "K";
        else
            return ":black_joker:";
    }
}
