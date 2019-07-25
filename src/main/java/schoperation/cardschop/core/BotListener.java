package schoperation.cardschop.core;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import schoperation.cardschop.util.Msges;

public class BotListener {

    /*
        Listen for events
     */

    public void listenForEvents(DiscordClient client)
    {
        client.getEventDispatcher().on(MessageCreateEvent.class)
        .map(MessageCreateEvent::getMessage)
        .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
        .subscribe(event -> {
            // We got a message from a non-bot, check for correct prefix, then send it on its way to CommandProcessor.
            if (event.getContent().get().startsWith(Msges.PREFIX))
                CommandProcessor.processCommand(event.getContent().get(), Msges.PREFIX, event.getAuthor().get(), event.getChannel().block(), event.getGuild().block());
        });


    }
/*
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

 */
}
