package schoperation.cardschop.command.play.bet;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class BetCommand implements ICommand {

    /*
        Bet.

        bet [amount] -> throws [amount] chips into the table's pot.
     */

    private final String command = "bet";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // No arguments
            if (arg1.equals("blank"))
            {
                channel.sendMessage("Please provide an amount of chips you'd like to throw into the pot.");
                return;
            }
            else
            {
                int amount = Integer.parseInt(arg1);

                // Enough chips?
                if (amount > player.getChips())
                {
                    channel.sendMessage("Sorry, you're too poor to throw away that amount.");
                    return;
                }
                else
                {
                    player.subtractChips(amount);
                    player.getTable().addToPot(amount);
                    channel.sendMessage(player.getUser().getDisplayName(guild) + " threw " + amount + " chips into the pot.");
                }
            }

            // Update table
            player.getTable().update(guild);
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
