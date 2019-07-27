package schoperation.cardschop.command.play;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class GiveCommand implements ICommand {

    /*
        Gives a card to another player.

        give [player] [card] -> Gives the card corresponding to the number inputted, to the specified player.

        If card is not specified, use the topmost card of the givingPlayer's hand.
     */

    private final String command = "give";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {

            // First player
            Player givingPlayer = Utils.getPlayerObj(sender, guild);

            // Card
            int cardInt;
            Card card;

            // Correct arguments?
            if (arg1.equals("blank"))
            {
                PostalService.sendMessage(channel, "Please provide arguments. The player should be mentioned. \nEx. " + Msges.PREFIX + "give @Person#1234 4  //Gives your fourth card to Person.");
                return;
            }
            else if (arg2.equals("blank"))
            {
                // Use the topmost card
                card = givingPlayer.getHand().get(givingPlayer.getNumOfCards() - 1);
            }
            else
            {
                // Decipher the card (just number or a string)
                if (!Utils.isInt(arg2))
                {
                    card = Card.parseCard(arg2);

                    if (card == null || !givingPlayer.hasCard(card))
                    {
                        PostalService.sendMessage(channel, Msges.INVALID_CARD);
                        return;
                    }

                    card = givingPlayer.getCardByCard(card);
                }
                else
                {
                    cardInt = Integer.parseInt(arg2) - 1;

                    if (cardInt > givingPlayer.getHand().size())
                    {
                        PostalService.sendMessage(channel, Msges.INVALID_CARD);
                        return;
                    }

                    card = givingPlayer.getHand().get(cardInt);
                }
            }

            // Player they're going to give it to.
            // Make sure they are part of a table first.
            arg1 = arg1.replaceAll("[<>@!]", "");
            Member userFromString = guild.getMemberById(Snowflake.of(Long.parseLong(arg1))).block();

            for (Player receivingPlayer : givingPlayer.getTable().getPlayers())
            {
                if (receivingPlayer.getUser().getId().equals(userFromString.getId()))
                {
                    // Found them.
                    // Remove the card from givingPlayer and add it to receivingPlayer's hand.
                    givingPlayer.removeCard(card);
                    receivingPlayer.addCard(card);
                    PostalService.sendMessage(channel, givingPlayer.getDisplayName() + " gave a card to " + receivingPlayer.getDisplayName() + ".");
                    SeeCommand.seeHand(givingPlayer);
                    SeeCommand.seeHand(receivingPlayer);
                    givingPlayer.getTable().update(guild);
                    return;
                }
            }

            PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
            return;
        }

        PostalService.sendMessage(channel, Msges.NO_TABLE);
        return;
    }
}
