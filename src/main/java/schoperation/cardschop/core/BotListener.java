package main.java.schoperation.cardschop.core;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class BotListener {

    /*
        Listen for events
     */

    // The holy prefix. Whoop de freaking do.
    public static String prefix = new String("&");

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event)
    {
        if (event.getMessage().getContent().toLowerCase().startsWith(prefix))
            CommandProcessor.processCommand(event.getMessage(), prefix);
    }
}
