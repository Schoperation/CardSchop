package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class DealCommand implements ICommand {

    /*
        Deals cards.

        deal [perplayer] [atatime] [dealergetscards] -> deals [perplayer] cards to every way, [atatime] cards at a time.
     */

    private final String command = "deal";

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
            Player player = Utils.getPlayerObj(sender);

            // Table
            Table table = player.getTable();

            // Check if dealer
            if (table.getDealer() == player)
            {
                // Argument checking
                if (arg1.equals("blank"))
                {
                    table.dealCards(0, 1, true, player);
                    channel.sendMessage("Dealt all cards to everyone, one at a time. Use " + Msges.PREFIX + "see to privately see your hand.");
                }
                else if (arg2.equals("blank"))
                {
                    table.dealCards(Integer.parseInt(arg1), 1, true, player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, one at a time. Use " + Msges.PREFIX + "see to privately see your hand.");
                }
                else if (arg3.equals("blank"))
                {
                    table.dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), true, player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time. Use " + Msges.PREFIX + "see to privately see your hand.");
                }
                else
                {
                    table.dealCards(Integer.parseInt(arg1), Integer.parseInt(arg2), Boolean.parseBoolean(arg3), player);
                    channel.sendMessage("Dealt " + arg1 + " cards to everyone, " + arg2 + " at a time. Dealer got cards = " + arg3 + ". Use " + Msges.PREFIX + "see to privately see your hand.");
                }

                // Update hands and table
                for (Player p : table.getPlayers())
                    SeeCommand.seeHand(p);

                table.update(guild);
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
