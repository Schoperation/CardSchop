package schoperation.cardschop.core;

import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Tables;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.GuildLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;

import java.util.ArrayList;
import java.util.List;

public class BotListener {

    /*
        Listen for events
     */

    // When a message is received
    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event)
    {
        // Is this a command we should recognize?
        if (event.getMessage().getContent().toLowerCase().startsWith(Msges.PREFIX))
            CommandProcessor.processCommand(event.getMessage(), Msges.PREFIX);

        return;
    }

    // When a server adds this bot, either through the invite link, back from being offline, or when the bot boots up.
    @EventSubscriber
    public void onGuildCreate(GuildCreateEvent event)
    {
        IGuild guild = event.getGuild();

        // Give the server a table list if they don't already have one.
        if (!Tables.list.containsKey(guild))
        {
            List<Table> newList = new ArrayList<>();
            Tables.list.put(guild, newList);
        }

        return;
    }

    // When the bot leaves a server.
    @EventSubscriber
    public void onGuildLeave(GuildLeaveEvent event)
    {
        IGuild guild = event.getGuild();

        // Get rid of their list.
        Tables.list.remove(guild, Tables.list.get(guild));

        return;
    }
}
