package main.java.schoperation.cardschop.core;

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

        // Split up the message
        String[] command = message.getContent().toLowerCase().replaceFirst(prefix, "").split(" ");

        if (command[0].equals("ping"))
            channel.sendMessage("Pong!");
    }
}
