package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class CollectCommand implements ICommand {

    /*
        Collects everyone's cards from their hands (and table, soon tm)
        and puts them back into the deck.

        collect -> collects everyone's cards.
     */

    private final String command = "collect";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {

        // Is this player part of a table?
        if(Utils.isPartOfTable(sender))
        {
            // Check if dealer
            Player player = Utils.getPlayerClass(sender);

            if (player.getTable().getDealer().equals(player))
            {
                player.getTable().collectCards();
                channel.sendMessage("The dealer collected everyone's cards.");
                player.getTable().update(guild);
                return;
            }
            else
            {
                channel.sendMessage(Msges.NOT_DEALER);
                return;
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
