package schoperation.cardschop.command.play.bet;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
import schoperation.cardschop.util.Utils;

public class BetCommand implements ICommand {

    /*
        Bet.

        bet [amount] -> throws [amount] chips into the table's pot.
     */

    private final String command = "bet";

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

            // No arguments
            if (arg1.equals("blank"))
            {
                PostalService.sendMessage(channel, "Please provide an amount of chips you'd like to throw into the pot.");
                return;
            }
            else
            {
                if (!Utils.isInt(arg1))
                {
                    PostalService.sendMessage(channel, Msges.NAN);
                    return;
                }

                int amount = Integer.parseInt(arg1);

                // Enough chips?
                if (amount > player.getChips())
                {
                    PostalService.sendMessage(channel, "Sorry, you're too poor to throw away that amount.");
                    return;
                }
                else
                {
                    player.subtractChips(amount);
                    player.getTable().addToPot(amount);
                    PostalService.sendMessage(channel, player.getDisplayName() + " threw " + amount + " chips into the pot.");
                }
            }

            // Update table
            player.getTable().update(guild);
            return;
        }

        PostalService.sendMessage(channel, Msges.NO_TABLE);
        return;
    }
}
