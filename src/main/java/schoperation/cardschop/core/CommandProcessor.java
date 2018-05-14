package schoperation.cardschop.core;

import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Commands;
import schoperation.cardschop.util.Msges;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandProcessor {

    // Process commands coming in
    public static void processCommand(IMessage message, String prefix)
    {
        // Who and what sent it?
        IUser sender = message.getAuthor();
        IChannel channel = message.getChannel();
        IGuild guild = message.getGuild();

        // Split up the message, make it lowercase, blah blah blah
        String[] command = message.getContent().toLowerCase().replaceFirst(prefix, "").split(" ");

        // Delete command. Very nice
        if (!channel.isPrivate())
            message.delete();

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
        channel.sendMessage(Msges.INVALID_COMMAND);
    }
}