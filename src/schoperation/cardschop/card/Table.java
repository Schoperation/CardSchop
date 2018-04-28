package schoperation.cardschop.card;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;

public class Table {

    /*
        Where people play. What a concept. This is what the users will create by themselves.
     */

    // Name
    private String name;

    // Channel it's on
    private IChannel channel;

    // Message the table is printed on
    private IMessage message;

    // List of players
    private List<Player> players = new ArrayList<>();

    // Its deck
    private Deck deck;

    // The current dealer at this table.
    private Player dealer;

    // Its pot
    private int pot;

    public Table(String n, IChannel c)
    {
        this.name = n;
        this.channel = c;
        this.message = this.channel.sendMessage("table is this message yay");
        this.deck = new Deck();
    }

    public Player getDealer()
    {
        return this.dealer;
    }

    public void setDealer(Player p)
    {
        this.dealer = p;
        return;
    }

    public String getName()
    {
        return this.name;
    }

    public IChannel getChannel()
    {
        return this.channel;
    }

    public List<Player> getPlayers()
    {
        return this.players;
    }

    public Deck getDeck()
    {
        return this.deck;
    }

    public int getPot()
    {
        return this.pot;
    }

    /*
        Deals the cards from the deck to players. 0 perPlayer means just try to distribute the cards evenly.
     */
    public void dealCards(int perPlayer, int atATime, boolean dealerGetsCards, Player player)
    {
        // So this player is the dealer.
        // Go through each player and give them atATime cards, assuming that doesn't go over perPlayer.
        // If perPlayer cards is not reached by all players, do another round.
        // If perPlayer is 0, just keep going until all cards are dealt.
        if (perPlayer == 0)
            perPlayer = 52;

        int i;
        int numCards = 0;
        boolean alreadyCounted = false;
        while (numCards < perPlayer && !this.deck.getCards().isEmpty())
        {
            alreadyCounted = false;

            // Go through each player
            for (Player p : this.players)
            {
                // Give them atATime cards
                if (p.equals(player) && !dealerGetsCards)
                    ;
                else
                {
                    for (i = 0; i < atATime; i++)
                    {
                        Card card;

                        if (this.deck.getCards().isEmpty())
                            break;
                        else
                            card = this.deck.getCards().remove(this.deck.getNumberOfCards() - 1);

                        p.addCard(card);

                        if (!alreadyCounted)
                            numCards++;
                    }
                }

                alreadyCounted = true;
            }
        }
    }

    /*
        Collects every player's cards (and from the table) and returns them to the deck.
     */
    public void collectCards()
    {
        // Go through each player, append their hands into the deck, then clear their hand.
        for (Player player : this.players)
        {
            if (player.getNumOfCards() != 0)
            {
                this.deck.getCards().addAll(player.getHand());
                player.emptyHand();
            }
        }
    }
}
