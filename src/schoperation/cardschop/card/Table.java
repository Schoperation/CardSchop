package schoperation.cardschop.card;

import schoperation.cardschop.core.Objs;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    Table(String n, IChannel c)
    {
        this.name = n;
        this.channel = c;
        this.message = this.channel.sendMessage("");
        this.deck = new Deck();
        Objs.tables.add(this);
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
        // Is this player the dealer? If not, screw it.
        if (!player.equals(this.dealer))
        {
            this.channel.sendMessage("Sorry, you are not the dealer. Try &setdealer.");
            return;
        }

        // So this player is the dealer.
        // Pick a random player to test against.
        Player tested;
        Random random = new Random();

        if (dealerGetsCards)
            tested = player;
        else
        {
            int randint = random.nextInt(this.players.size());
            tested = this.players.get(randint);
            while (tested.equals(player))
            {
                randint = random.nextInt(this.players.size());
                tested = this.players.get(randint);
            }
        }

        // Go through each player and give them atATime cards, assuming that doesn't go over perPlayer.
        // If perPlayer cards is not reached by all players, do another round.
        // If perPlayer is 0, just keep going until all cards are dealt.
        int i;
        while (tested.getNumOfCards() < perPlayer && !this.deck.getCards().isEmpty())
        {
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
                        Card card = this.deck.getCards().remove(this.deck.getNumberOfCards());
                        p.addCard(card);
                    }
                }
            }
        }
    }

    /*
        Collects every player's cards (and from the table) and returns them to the deck.
     */
    public void collectCards(Player player)
    {
        // Is this player the dealer? If not, screw it.
        if (!player.equals(this.dealer))
        {
            this.channel.sendMessage("Sorry, you are not the dealer. Try &setdealer.");
            return;
        }

        // This IS the dealer.
        // Go through each player, copy their hands to a temp list, clear their hands, and add the temp to the deck.
        List<Card> temp;
        for (Player p : this.players)
        {
            if (p.getNumOfCards() != 0)
            {
                temp = p.getHand();
                p.emptyHand();

                this.deck.getCards().addAll(temp);
            }
        }
    }
}
