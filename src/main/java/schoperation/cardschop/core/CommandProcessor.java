package main.java.schoperation.cardschop.core;

import main.java.schoperation.cardschop.card.Card;
import main.java.schoperation.cardschop.card.Suit;
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
        if (command[0].equals("showcards"))
        {

            // temp bullcrap
            int i;
            Suit s;
            Card card;
            StringBuilder sb = new StringBuilder();
            for (i = 0; i < 52; i++)
            {
                if (i < 13)
                    s = Suit.CLUBS;
                else if (i < 26)
                    s = Suit.DIAMONDS;
                else if (i < 39)
                    s = Suit.HEARTS;
                else
                    s = Suit.SPADES;

                card = new Card(s, i % 13 + 1);
                sb.append(card.toPrinted());

                if (i % 13 == 12)
                    sb.append("\n");
                else
                    sb.append(", ");
            }
            //message.delete();
            //sender.getOrCreatePMChannel().sendMessage(sb.toString());
            channel.sendMessage(sb.toString());
        }
    }
}