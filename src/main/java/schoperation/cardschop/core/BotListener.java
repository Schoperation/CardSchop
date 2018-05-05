package schoperation.cardschop.core;

import schoperation.cardschop.util.Msges;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class BotListener {

    /*
        Listen for events
     */

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event)
    {
        // Is this a command we should recognize?
        if (event.getMessage().getContent().toLowerCase().startsWith(Msges.PREFIX))
            CommandProcessor.processCommand(event.getMessage(), Msges.PREFIX);
    }
}
