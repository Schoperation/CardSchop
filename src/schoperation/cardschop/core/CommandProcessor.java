package schoperation.cardschop.core;

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

        // Soon make a command class/interface/whatever
        if (command[0].equals("showdeck"))
        {
            //channel.sendMessage("Deck: ");
            //channel.sendMessage(Objs.deck.getCardsToString());
        }

        else if (command[0].equals("shuffledeck"))
        {
            //BotMain.deck.shuffle();
            //channel.sendMessage("Shuffled deck.");
        }
    }
}