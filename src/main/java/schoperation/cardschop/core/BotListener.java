package schoperation.cardschop.core;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.guild.GuildDeleteEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import schoperation.cardschop.card.Table;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Tables;

import java.util.ArrayList;
import java.util.List;

public class BotListener {

    /*
        Listen for events
     */

    public void listenForEvents(DiscordClient client)
    {
        // When a message is sent.
        client.getEventDispatcher().on(MessageCreateEvent.class)
        .map(MessageCreateEvent::getMessage)
        .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
        .subscribe(event -> {
            // We got a message from a non-bot, check for correct prefix, then send it on its way to CommandProcessor.
            if (event.getContent().get().startsWith(Msges.PREFIX))
            {
                CommandProcessor.processCommand(event.getContent().get(), Msges.PREFIX, event.getAuthor().get(), event.getChannel().block(), event.getGuild().block());
                event.delete().subscribe();
            }
        });

        // When a server adds this bot, or the bot boots up, reconnecting to all the servers.
        client.getEventDispatcher().on(GuildCreateEvent.class)
        .map(GuildCreateEvent::getGuild)
        .subscribe(event -> {

            // Give the server a table list if they don't already have one.
            if (!Tables.list.containsKey(event))
            {
                List<Table> newList = new ArrayList<>();
                Tables.list.put(event, newList);
            }
        });

        // When the bot leaves a server.
        client.getEventDispatcher().on(GuildDeleteEvent.class)
        .subscribe(event -> {

            // Is the server just out during an outage? Then dont remove its list.
            if (!event.isUnavailable())
                Tables.list.remove(event.getGuild().get(), Tables.list.get(event.getGuild().get()));
        });

        return;
    }
}
