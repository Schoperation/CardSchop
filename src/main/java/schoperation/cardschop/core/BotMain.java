package schoperation.cardschop.core;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;

public class BotMain {

    // The bot itself.
    public static DiscordClient bot = null;

    // Build and log the bot on.
    public static void main(String args[])
    {
        DiscordClientBuilder builder = new DiscordClientBuilder(Token.t);
        bot = builder.build();

        // Call event listener
        BotListener listener = new BotListener();
        listener.listenForEvents(bot);

        // Log in
        bot.login().block();

        // Update presence
        bot.updatePresence(Presence.online(Activity.watching("your stupid cmds")));
    }
}
