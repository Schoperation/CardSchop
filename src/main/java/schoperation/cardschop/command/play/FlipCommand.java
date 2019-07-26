package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;

public class FlipCommand implements ICommand {

    /*
        Flips a card. Or your entire hand.

        flip [card] [upordown] -> Flips the specified card.

        card must be specified. Can use "all" for all cards in a player's hand.
        upordown is optional. It can be "facedown" or "faceup" to just set the card to so.
     */

    private final String command = "flip";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // First argument
            if (arg1.equals("blank"))
            {
                channel.createMessage("Please specify a card, or `all` for all cards in your hand.");
                return;
            }
            // All cards
            else if (arg1.equals("all"))
            {
                // Is there a second argument?
                if (arg2.equals("blank"))
                {
                    // Flip all cards in their hand.
                    for (Card card : player.getHand())
                        card.flip();
                }
                else if (arg2.equals("faceup"))
                {
                    for (Card card : player.getHand())
                        card.setFaceUp();
                }
                else if (arg2.equals("facedown"))
                {
                    for (Card card : player.getHand())
                        card.setFaceDown();
                }
                else
                {
                    channel.createMessage("For the second argument (optional), use `faceup` or `facedown`.");
                    return;
                }
            }
            // Card by index
            else if (Utils.isInt(arg1))
            {
                int cardInt = Integer.parseInt(arg1) - 1;

                if (cardInt > player.getHand().size())
                {
                    channel.createMessage(Msges.INVALID_CARD);
                    return;
                }

                Card card = player.getHand().get(cardInt);

                // Is there a second argument?
                if (arg2.equals("blank"))
                    card.flip();
                else if (arg2.equals("faceup"))
                    card.setFaceUp();
                else if (arg2.equals("facedown"))
                    card.setFaceDown();
                else
                {
                    channel.createMessage("For the second argument (optional), use `faceup` or `facedown`.");
                    return;
                }
            }
            // Card by card
            else
            {
                Card card = Card.parseCard(arg1);

                if (card == null || !player.hasCard(card))
                {
                    channel.createMessage(Msges.INVALID_CARD);
                    return;
                }

                card = player.getCardByCard(card);

                // Is there a second argument?
                if (arg2.equals("blank"))
                    card.flip();
                else if (arg2.equals("faceup"))
                    card.setFaceUp();
                else if (arg2.equals("facedown"))
                    card.setFaceDown();
                else
                {
                    channel.createMessage("For the second argument (optional), use `faceup` or `facedown`.");
                    return;
                }
            }

            // Notify
            SeeCommand.seeHand(player);
            return;
        }

        channel.createMessage(Msges.NO_TABLE);
        return;
    }
}
