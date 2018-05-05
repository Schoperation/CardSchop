package schoperation.cardschop.command;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.core.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class DealCommand implements ICommand {

    /*
        Deals cards.

        deal [perplayer] [atatime] [dealergetscards] -> deals [perplayer] cards to every way, [atatime] cards at a time.
     */

    private String command = "deal";

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
                // Argument checking
                if (arg1.equals("blank"))
                {
                    player.getTable().dealCards(0, 1, true, player);
                    channel.sendMessage("Dealt all cards to everyone, one at a time.");
                }
                else if (arg2.equals("blank"))
                {
                    player.getTable().dealCards(Integer.parseInt(arg1), 1, true, player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, one at a time.");
                }
                else if (arg3.equals("blank"))
                {
                    player.getTable().dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), true, player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time.");
                }
                else
                {
                    player.getTable().dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), Boolean.parseBoolean(arg3), player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time. Dealer got cards = " + arg3);
                }

                return;
            }
            else
            {
                channel.sendMessage("You are not the dealer! Use &setdealer.");
                return;
            }
        }

        channel.sendMessage("You must be part of a table.");
        return;
    }
}
