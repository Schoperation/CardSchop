package schoperation.cardschop.core;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Commands;
import schoperation.cardschop.util.Msges;

public class CommandProcessor {

    // Process commands coming in
    public static void processCommand(String message, String prefix, User sender, MessageChannel channel, Guild guild)
    {
        // Split up the message, make it lowercase, blah blah blah
        String[] command = message.toLowerCase().replaceFirst(prefix, "").split(" ");

        // Go through each command and try to match it
        for (ICommand cmd : Commands.LIST)
        {
            // Does the message line up with that command? Execute it.
            if (command[0].equals(cmd.getCommand()))
            {
                // Check for arguments
                if (command.length == 4)
                    cmd.execute(sender, channel, guild, command[1], command[2], command[3]);
                else if (command.length == 3)
                    cmd.execute(sender, channel, guild, command[1], command[2], "blank");
                else if (command.length == 2)
                    cmd.execute(sender, channel, guild, command[1], "blank", "blank");
                else
                    cmd.execute(sender, channel, guild, "blank", "blank", "blank");

                return;
            }
        }

        // Invalid command
        channel.createMessage(Msges.INVALID_COMMAND);
    }
}