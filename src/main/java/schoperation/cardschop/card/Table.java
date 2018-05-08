package schoperation.cardschop.card;

import schoperation.cardschop.command.play.SeeCommand;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
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

    // Message the table itself is printed on. Constantly edited. The command output will be put below the table.
    private IMessage message;

    // Divider message, for help with clearing.
    private IMessage divider;

    // List of players
    private List<Player> players = new ArrayList<>();

    // Middle pile (right by the deck).
    private List<Card> middlePile = new ArrayList<>();

    // Its deck
    private Deck deck;

    // The current dealer at this table. Dummy player as a placeholder.
    private Player dealer = new Player(null, null);

    // Its pot
    private int pot;

    public Table(String n, IChannel c)
    {
        this.name = n;
        this.channel = c;
        this.message = this.channel.sendMessage("k");
        this.divider = this.channel.sendMessage("------------------------------------------");
        this.deck = new Deck(); // TODO eventually allow players to decide this deck?
        this.update(this.channel.getGuild());
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

    public List<Card> getMiddlePile()
    {
        return this.middlePile;
    }

    public Deck getDeck()
    {
        return this.deck;
    }

    /*
        Dealing with the pot.
     */

    public int getPot()
    {
        return this.pot;
    }

    public void setPot(int p)
    {
        this.pot = p;
        return;
    }

    public void addToPot(int p)
    {
        this.pot += p;
        return;
    }

    public void takeFromPot(int p)
    {
        this.pot -= p;

        if (this.pot < 0)
            this.pot = 0;

        return;
    }

    /*
        Setting and getting the messages associated with the table.
     */

    public IMessage getTableMsg()
    {
        return this.message;
    }

    public IMessage getDivider()
    {
        return this.divider;
    }

    public void setDivider(IMessage msg)
    {
        this.divider = msg;
        return;
    }

    // The main function that edits the table message.
    public void update(IGuild guild)
    {
        StringBuilder sb = new StringBuilder();

        final int SPACES = 35;

        // Use to find the middle of the table. For printing the deck.
        int[] charPosition = new int[50];
        int element = 0;

        // Edge of table. Start with the name of it.
        sb.append("Table **" + this.getName() + "**");
        sb.append("\n");

        int i;
        for (i = 0; i < SPACES; i++)
            sb.append(" ");

        sb.append("+===============+\n");

        for (i = 0; i < SPACES; i++)
            sb.append(" ");

        // For every pipe that is ABOUT TO print (|   |), record the position in the string.
        charPosition[element] = sb.length();
        element++;

        sb.append("|                                          |\n"); // 42 blanks between the pipes


        // For every player (or two players), add another length to the table, with their names.
        int numPlayers = this.players.size();
        int j;
        i = 0;
        while (numPlayers > 0)
        {
            /*
                Only one player left?
             */
            if (numPlayers == 1)
            {
                // Print a line with just the one player.
                String dispName = this.players.get(i).getUser().getDisplayName(guild);

                // Print spaces until we're at the const num.
                for (j = 0; j < (SPACES - (dispName.length() * 2) - 1); j++)
                    sb.append(" ");

                sb.append(dispName);
                sb.append(" ");

                // Append their topmost front card, or nothing.
                charPosition[element] = sb.length();
                element++;

                if (this.players.get(i).getFront().isEmpty())
                    sb.append("|                                          |\n");
                else
                {
                    sb.append("|  ");
                    sb.append(this.players.get(i).getFront().get(this.players.get(i).getFront().size() - 1).getString());
                    sb.append("                              |\n");
                }

                // Now, on the next line, add the amount of chips they have. Allocate enough space for the number to fit
                int chips = this.players.get(i).getChips();
                int digits = getDigits(chips);

                // Print spaces
                for (j = 0; j < (SPACES - (digits * 3) + (digits / 2) - 1); j++)
                    sb.append(" ");

                // Print chips
                sb.append(this.players.get(i).getChips());
                sb.append(" ");

                // Table part
                charPosition[element] = sb.length();
                element++;
                sb.append("|                                          |\n");

                numPlayers--;
                i++;
            }
            /*
                Two or more players?
             */
            else
            {
                // Print a line with just the one player.
                String dispName1 = this.players.get(i).getUser().getDisplayName(guild);
                String dispName2 = this.players.get(i + 1).getUser().getDisplayName(guild);

                // Print spaces until we're at the const num.
                for (j = 0; j < (SPACES - (dispName1.length() * 2) - 1); j++)
                    sb.append(" ");

                sb.append(dispName1);
                sb.append(" ");

                charPosition[element] = sb.length();
                element++;

                // Front cards TODO this good?
                if (this.players.get(i).getFront().isEmpty())
                    sb.append("|                                          ");
                else
                {
                    sb.append("|  ");
                    sb.append(this.players.get(i).getFront().get(this.players.get(i).getFront().size() - 1).getString());
                    sb.append("                                ");
                }

                if (this.players.get(i + 1).getFront().isEmpty())
                    sb.append("|");
                else
                {
                    sb.append(this.players.get(i + 1).getFront().get(this.players.get(i + 1).getFront().size() - 1).getString());
                    sb.append("  |");
                }

                sb.append(" ");
                sb.append(dispName2);
                sb.append("\n");

                // Now, on the next line, add the amount of chips they have. Allocate enough space for the number to fit
                int chips = this.players.get(i).getChips();
                int digits = getDigits(chips);

                // Print spaces
                for (j = 0; j < (SPACES - (digits * 3) + (digits / 2) - 1); j++)
                    sb.append(" ");

                // Print chips
                sb.append(this.players.get(i).getChips());
                sb.append(" ");

                // Table part
                charPosition[element] = sb.length();
                element++;
                sb.append("|                                          | ");

                // Add second player's chips
                sb.append(this.players.get(i + 1).getChips());
                sb.append("\n");

                numPlayers -= 2;
                i += 2;
            }

            // Another divider, so the names are not clumped up.
            for (j = 0; j < SPACES; j++)
                sb.append(" ");

            charPosition[element] = sb.length();
            element++;
            sb.append("|                                          |\n");
        }

        // End edge of table
        // Only print an extra one if no one is at the table.
        if (this.getPlayers().size() <= 0)
        {
            for (i = 0; i < 2; i++)
            {
                for (j = 0; j < SPACES; j++)
                    sb.append(" ");

                charPosition[element] = sb.length();
                element++;
                sb.append("|                                          |\n");
            }
        }
        else
        {
            for (j = 0; j < SPACES; j++)
                sb.append(" ");

            charPosition[element] = sb.length();
            element++;
            sb.append("|                                          |\n");
        }

        for (j = 0; j < SPACES; j++)
            sb.append(" ");

        sb.append("+===============+\n");
        sb.append("\nLog:");

        // (Attempt to) find the middle of the table.
        // That list of char positions? The amount of them is element.
        // We'll use the position that is in the middle of the array, and insert the deck.
        int deckPos = charPosition[element / 2] + 18;
        sb.replace(deckPos, deckPos + 3, "[" + this.deck.getNumberOfCards() + "]");
        sb.delete(deckPos + 4, deckPos + 9);

        // Add the "middle pile"
        deckPos = charPosition[(element / 2) - 1] + 18;

        if (this.middlePile.isEmpty())
        {
            sb.replace(deckPos, deckPos + 3, "mid");
            sb.delete(deckPos + 4, deckPos + 8);
        }
        else
        {
            sb.replace(deckPos, deckPos + 10, this.middlePile.get(this.middlePile.size() - 1).getString());
            sb.delete(deckPos + 11, deckPos + 11);
        }

        // Add the amount in the pot below the deck.
        int digits = getDigits(this.pot);
        deckPos = charPosition[(element / 2) + 1] + 14 - digits;
        sb.replace(deckPos, deckPos + digits, Integer.toString(this.pot));
        sb.delete(deckPos + digits + 1, deckPos + (digits * 2) + 2);


        // Edit message
        if (this.message.isDeleted())
            this.message = this.channel.sendMessage(sb.toString());
        else
            this.message.edit(sb.toString());

        return;
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
        boolean alreadyCounted;
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
        // Go through each player
        for (Player player : this.players)
        {
            // Append their hands into the deck, then clear their hand.
            if (player.getNumOfCards() != 0)
            {
                this.deck.getCards().addAll(player.getHand());
                player.clearHand();
            }

            // Side pile
            if (!player.getPile().isEmpty())
            {
                this.deck.getCards().addAll(player.getPile());
                player.getPile().clear();
            }

            // Front pile
            if (!player.getFront().isEmpty())
            {
                this.deck.getCards().addAll(player.getFront());
                player.getFront().clear();
            }

            // Update their hand
            SeeCommand.seeHand(player);
        }

        // Clear the middle pile
        if (!this.middlePile.isEmpty())
        {
            this.deck.getCards().addAll(this.middlePile);
            this.middlePile.clear();
        }

        return;
    }

    /*
        Used in update, this returns the amount of digits in a number, up to 10 digits.
     */
    private int getDigits(int num)
    {
        if (num >= 1000000000)
            return 10;
        else if (num >= 100000000)
            return 9;
        else if (num >= 10000000)
            return 8;
        else if (num >= 1000000)
            return 7;
        else if (num >= 100000)
            return 6;
        else if (num >= 10000)
            return 5;
        else if (num >= 1000)
            return 4;
        else if (num >= 100)
            return 3;
        else if (num >= 10)
            return 2;
        else
            return 1;
    }
}
