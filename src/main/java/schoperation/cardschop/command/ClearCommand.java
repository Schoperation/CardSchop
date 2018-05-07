package schoperation.cardschop.command;

import schoperation.cardschop.card.Table;
import schoperation.cardschop.core.BotMain;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MessageHistory;

public class ClearCommand implements ICommand {

    /*
        Clears table log. NOT the table.

        clear [amount] -> Clears [amount] messages that were sent by the bot, under the "Log:" line.

        If no amount specified, clears all messages under "Log:" line.
     */

    private final String command = "clear";

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
            // Alright, start clearing them.
            Table table = Utils.getPlayerObj(sender).getTable();
            MessageHistory history = channel.getMessageHistoryTo(table.getDivider().getLongID());

            // Did they specify how many messages to delete?
            if (arg1.equals("blank"))
            {
                history.bulkDelete();
                channel.sendMessage(table.getDivider().getContent());
                return;
            }
            else
            {
                int amount = Integer.parseInt(arg1);
                MessageHistory historyLimited = channel.getMessageHistoryTo(table.getDivider().getLongID(), amount);
                historyLimited.bulkDelete();

                if (amount >= history.size())
                    channel.sendMessage(table.getDivider().getContent());

                return;
            }
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
