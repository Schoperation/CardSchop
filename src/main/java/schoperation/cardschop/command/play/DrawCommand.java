package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class DrawCommand implements ICommand {

    /*
        Draws a card from the top of the deck.

        draw -> takes a card from the top of the deck.
     */

    private final String command = "draw";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {
        // Part of a table?
        if (Utils.isPartOfTable(sender))
        {
            Player player = Utils.getPlayerClass(sender);

            // Is there a card in the deck?
            if(player.getTable().getDeck().getNumberOfCards() < 1)
            {
                channel.sendMessage(Msges.EMPTY_DECK);
                return;
            }
            else
            {
                // Take a card from the deck and add it to the player's hand.
                Card card = player.getTable().getDeck().getCards().remove(player.getTable().getDeck().getNumberOfCards() - 1);
                player.addCard(card);
                channel.sendMessage(player.getUser().getDisplayName(guild) + " has drawn a card from the deck.");
                return;
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
