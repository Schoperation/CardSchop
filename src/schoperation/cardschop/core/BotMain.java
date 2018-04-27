package schoperation.cardschop.core;

import schoperation.cardschop.card.Deck;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

public class BotMain {

    // The bot itself. The token is hidden. Because fuck you. You'll probably figure it out somehow.
    public static final IDiscordClient bot = createClient(Token.t, true);

    // temp
    public static final Deck deck = new Deck();

    // Main
    public static void main(String args[])
    {
        EventDispatcher dispatcher = bot.getDispatcher();
        dispatcher.registerListener(new BotListener());
    }

    public static IDiscordClient createClient(String token, boolean login)
    {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);

        try
        {
            if (login)
                return clientBuilder.login();
            else
                return clientBuilder.build();
        }
        catch (DiscordException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}