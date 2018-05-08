package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
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
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);
            Table table = player.getTable();

            // If no arguments, place the topmost card of the player's hand in the middle.
            if (arg1.equals("blank"))
            {
                Card card = player.removeCard();
                table.getMiddlePile().add(card);

                // Update hand and table
                SeeCommand.seeHand(player);
                table.update(guild);
                return;
            }
            // If one argument, use the topmost card.
            else if (arg2.equals("blank"))
            {
                // Go through the spots.
                if (arg1.toLowerCase().equals("underdeck"))
                {
                    Card card = player.removeCard();
                    table.getDeck().addToBottom(card);
                }
                else if (arg1.toLowerCase().equals("middle"))
                {
                    Card card = player.removeCard();
                    table.getMiddlePile().add(card);
                }
                else if (arg1.toLowerCase().equals("pile"))
                {
                    Card card = player.removeCard();
                    player.getPile().add(card);
                }
                else if (arg1.toLowerCase().equals("infront") || arg1.toLowerCase().equals("trick"))
                {
                    Card card = player.removeCard();
                    player.getFront().add(card);
                }
                else
                {
                    channel.sendMessage(Msges.INVALID_PLACE_PLACE);
                    return;
                }

                // Update hand and table
                SeeCommand.seeHand(player);
                table.update(guild);
                return;
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
                    table.getDeck().addToBottom(card);
                }
                else if (arg1.toLowerCase().equals("middle"))
                {
                    Card card = player.removeCard(cardInt);
                    table.getMiddlePile().add(card);
                }
                else if (arg1.toLowerCase().equals("pile"))
                {
                    Card card = player.removeCard(cardInt);
                    player.getPile().add(card);
                }
                else if (arg1.toLowerCase().equals("infront") || arg1.toLowerCase().equals("trick"))
                {
                    Card card = player.removeCard(cardInt);
                    player.getFront().add(card);
                }
                else
                {
                    channel.sendMessage(Msges.INVALID_PLACE_PLACE);
                    return;
                }

                // Update hand and table
                SeeCommand.seeHand(player);
                table.update(guild);
                return;
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
