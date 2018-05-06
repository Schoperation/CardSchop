package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class PlaceCommand implements ICommand {

    /*
        Places the specified card somewhere.

        place [place] [card] -> places [card] on top of [place]. If no card specified, places the topmost one from the player's hand.

        If nothing specified, place the topmost card in the middle.

        Available places:
            -underdeck -> slides the card back under the deck.
            -middle -> the pile right by the deck. Usually "discard pile", or games like egyptian rat screw.
            -pile -> player's personal pile.
            -infront -> in front of the player. For tricks.

            Defaults to middle.
     */

    private final String command = "place";

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

            // If no arguments, place the topmost card of the player's hand in the middle.
            if (arg1.equals("blank"))
            {
                Card card = player.removeCard();
                player.getTable().getMiddlePile().add(card);
                player.getTable().update(guild);
                return;
            }
            // If one argument, use the topmost card.
            else if (arg2.equals("blank"))
            {
                // Go through the spots.
                if (arg1.toLowerCase().equals("underdeck"))
                {
                    Card card = player.removeCard();
                    player.getTable().getDeck().addToBottom(card);
                    player.getTable().update(guild);
                    return;
                }
                else if (arg1.toLowerCase().equals("middle"))
                {
                    Card card = player.removeCard();
                    player.getTable().getMiddlePile().add(card);
                    player.getTable().update(guild);
                    return;
                }
                else if (arg1.toLowerCase().equals("pile"))
                {
                    Card card = player.removeCard();
                    player.getPile().add(card);
                    player.getTable().update(guild);
                    return;
                }
                else if (arg1.toLowerCase().equals("infront"))
                {
                    Card card = player.removeCard();
                    player.getFront().add(card);
                    player.getTable().update(guild);
                    return;
                }
                else
                {
                    channel.sendMessage(Msges.INVALID_PLACE);
                    return;
                }
            }
            // Both arguments.
            else
            {
                // Card
                int cardInt = Integer.parseInt(arg2) - 1;

                // NOT part of their hand.
                if (cardInt > player.getHand().size())
                {
                    channel.sendMessage(Msges.INVALID_CARD);
                    return;
                }

                // Go through the spots.
                if (arg1.toLowerCase().equals("underdeck"))
                {
                    Card card = player.removeCard(cardInt);
                    player.getTable().getDeck().addToBottom(card);
                    player.getTable().update(guild);
                    return;
                }
                else if (arg1.toLowerCase().equals("middle"))
                {
                    Card card = player.removeCard(cardInt);
                    player.getTable().getMiddlePile().add(card);
                    player.getTable().update(guild);
                    return;
                }
                else if (arg1.toLowerCase().equals("pile"))
                {
                    Card card = player.removeCard(cardInt);
                    player.getPile().add(card);
                    player.getTable().update(guild);
                    return;
                }
                else if (arg1.toLowerCase().equals("infront"))
                {
                    Card card = player.removeCard(cardInt);
                    player.getFront().add(card);
                    player.getTable().update(guild);
                    return;
                }
                else
                {
                    channel.sendMessage(Msges.INVALID_PLACE);
                    return;
                }
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
