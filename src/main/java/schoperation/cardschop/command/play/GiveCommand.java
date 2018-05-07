package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class GiveCommand implements ICommand {

    /*
        Gives a card to another player.

        give [player] [card] -> Gives the card corresponding to the number inputted, to the specified player.

        If card is not specified, use the topmost card of the givingPlayer's hand.
     */

    private final String command = "give";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if (Utils.isPartOfTable(sender))
        {

            // First player
            Player givingPlayer = Utils.getPlayerObj(sender);

            // Card integer
            int cardInt;

            // Correct arguments?
            if (arg1.equals("blank"))
            {
                channel.sendMessage("Please provide arguments. The player should be mentioned. \nEx. " + Msges.PREFIX + "give @Person#1234 4  //Gives your fourth card to Person.");
                return;
            }
            else if (arg2.equals("blank"))
            {
                // Use the topmost card
                cardInt = givingPlayer.getHand().size() - 1;
            }
            else
            {
                // Parse the card and see if that's part of their hand
                cardInt = Integer.parseInt(arg2) - 1;

                // NOT part of their hand.
                if (cardInt > givingPlayer.getHand().size())
                {
                    channel.sendMessage(Msges.INVALID_CARD);
                    return;
                }
            }

            // Player they're going to give it to.
            // Make sure they are part of a table first.
            arg1 = arg1.replaceAll("[<>@!]", "");
            IUser userFromString = guild.getUserByID(Long.parseLong(arg1));

            for (Player receivingPlayer : givingPlayer.getTable().getPlayers())
            {
                if (receivingPlayer.getUser().equals(userFromString))
                {
                    // Found them.
                    // Remove the card from givingPlayer and add it to receivingPlayer's hand.
                    Card card = givingPlayer.removeCard(cardInt);
                    receivingPlayer.addCard(card);
                    channel.sendMessage(givingPlayer.getUser().getDisplayName(guild) + " gave their number " + (cardInt + 1) + " card to " + receivingPlayer.getUser().getDisplayName(guild));
                    SeeCommand.seeHand(givingPlayer);
                    SeeCommand.seeHand(receivingPlayer);
                    givingPlayer.getTable().update(guild);
                    return;
                }
            }

            channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
