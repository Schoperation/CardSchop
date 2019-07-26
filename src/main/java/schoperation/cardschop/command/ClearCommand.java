package schoperation.cardschop.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;

import java.util.List;

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
    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Is this player part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            // Alright, start clearing them.
            Table table = Utils.getPlayerObj(sender, guild).getTable();
            List<Message> msgList;


            // Did they specify how many messages to delete?
            // No specification
            if (arg1.equals("blank"))
            {
                msgList = channel.getMessagesAfter(table.getDivider().block().getId()).collectList().block();

                // We got the list, now iterate through each msg and delete it.
                for (Message msg : msgList)
                    msg.delete();

                //table.setDivider(channel.sendMessage(table.getDivider().getContent()));
                return;
            }
            else // Specific amount
            {
                int amount;
                if (!Utils.isInt(arg1))
                {
                    channel.createMessage(Msges.NAN);
                    return;
                }

                amount = Integer.parseInt(arg1);
                Long l = new Long(amount);

                msgList = channel.getMessagesAfter(table.getDivider().block().getId()).take(l).collectList().block();

                // We got the list, now iterate through each msg and delete it.
                for (Message msg : msgList)
                    msg.delete();

                return;
            }
        }

        channel.createMessage(Msges.NO_TABLE);
        return;
    }
}
