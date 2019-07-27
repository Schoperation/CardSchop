package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class PlaceCommand implements ICommand {

    /*
        Places the specified card somewhere.

        place [place] [card] [faceupordown] -> places [card] on top of [place]. If no card specified, places the topmost one from the player's hand.

        If nothing specified, place the topmost card in the middle.

        Available places:
            -underdeck -> slides the card back under the deck.
            -middle -> the pile right by the deck. Usually "discard pile", or games like egyptian rat screw.
            -pile -> player's personal pile.
            -infront -> in front of the player. For tricks.

            Defaults to middle.

        Can add 'faceup' or 'facedown' as an argument to any of the above combinations.
     */

    private final String command = "place";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);
            Table table = player.getTable();

            // If no arguments, except for faceup or facedown, place the topmost card of the player's hand in the middle.
            if (arg1.equals("blank") || arg1.equals("faceup") || arg1.equals("facedown"))
            {
                Card card = player.removeCard();

                if (arg1.equals("faceup"))
                    card.setFaceUp();
                else if (arg1.equals("facedown"))
                    card.setFaceDown();

                table.getMiddlePile().add(card);
            }
            // If one argument, use the topmost card.
            else if (arg2.equals("blank") || arg2.equals("faceup") || arg2.equals("facedown"))
            {
                // Go through the spots.
                if (arg1.equals("underdeck"))
                {
                    Card card = player.removeCard();

                    if (arg2.equals("faceup"))
                        card.setFaceUp();
                    else if (arg2.equals("facedown"))
                        card.setFaceDown();

                    table.getDeck().addToBottom(card);
                }
                else if (arg1.equals("middle"))
                {
                    Card card = player.removeCard();

                    if (arg2.equals("faceup"))
                        card.setFaceUp();
                    else if (arg2.equals("facedown"))
                        card.setFaceDown();

                    table.getMiddlePile().add(card);
                }
                else if (arg1.equals("pile"))
                {
                    Card card = player.removeCard();

                    if (arg2.equals("faceup"))
                        card.setFaceUp();
                    else if (arg2.equals("facedown"))
                        card.setFaceDown();

                    player.getPile().add(card);
                }
                else if (arg1.equals("infront") || arg1.equals("trick"))
                {
                    Card card = player.removeCard();

                    if (arg2.equals("faceup"))
                        card.setFaceUp();
                    else if (arg2.equals("facedown"))
                        card.setFaceDown();

                    player.getFront().add(card);
                }
                else
                {
                    PostalService.sendMessage(channel, Msges.INVALID_PLACE_PLACE);
                    return;
                }
            }
            // Both arguments.
            else
            {
                // Card
                int cardInt;
                Card card;

                // Decipher the card (just number or a string)
                if (!Utils.isInt(arg2))
                {
                    card = Card.parseCard(arg2);

                    if (card == null || !player.hasCard(card))
                    {
                        PostalService.sendMessage(channel, Msges.INVALID_CARD);
                        return;
                    }

                    card = player.getCardByCard(card);
                }
                else
                {
                    cardInt = Integer.parseInt(arg2) - 1;

                    if (cardInt > player.getHand().size())
                    {
                        PostalService.sendMessage(channel, Msges.INVALID_CARD);
                        return;
                    }

                    card = player.getHand().get(cardInt);
                }

                // Go through the spots, then remove the card from the player and add it to that spot.
                if (arg1.equals("underdeck"))
                {
                    player.removeCard(card);

                    if (arg3.equals("faceup"))
                        card.setFaceUp();
                    else if (arg3.equals("facedown"))
                        card.setFaceDown();

                    table.getDeck().addToBottom(card);
                }
                else if (arg1.equals("middle"))
                {
                    player.removeCard(card);

                    if (arg3.equals("faceup"))
                        card.setFaceUp();
                    else if (arg3.equals("facedown"))
                        card.setFaceDown();

                    table.getMiddlePile().add(card);
                }
                else if (arg1.equals("pile"))
                {
                    player.removeCard(card);

                    if (arg3.equals("faceup"))
                        card.setFaceUp();
                    else if (arg3.equals("facedown"))
                        card.setFaceDown();

                    player.getPile().add(card);
                }
                else if (arg1.equals("infront") || arg1.equals("trick"))
                {
                    player.removeCard(card);

                    if (arg3.equals("faceup"))
                        card.setFaceUp();
                    else if (arg3.equals("facedown"))
                        card.setFaceDown();

                    player.getFront().add(card);
                }
                else
                {
                    PostalService.sendMessage(channel, Msges.INVALID_PLACE_PLACE);
                    return;
                }
            }

            // Update hand and table
            SeeCommand.seeHand(player);
            table.update(guild);
            return;
        }

        PostalService.sendMessage(channel, Msges.NO_TABLE);
        return;
    }
}
