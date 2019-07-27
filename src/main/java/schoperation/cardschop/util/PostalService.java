package schoperation.cardschop.util;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.spec.MessageEditSpec;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class PostalService {

    /*
        We deliver, we deliver!

        Without taxpayer money, too!

        These methods are for all commands to use to send message because I know SOMEWHERE I'm going to forget to do the Consumer thing or tack on .subscribe().
     */

    // This is only here bc of Mono, Flux, blah blah blah the stream stuff. Every message goes through here to be subscribed, aka executed.
    // Normal message
    public static void sendMessage(MessageChannel channel, String message)
    {
        channel.createMessage(message).subscribe();
        return;
    }

    // Private message
    public static void sendMessage(PrivateChannel channel, String message)
    {
        channel.createMessage(message).subscribe();
        return;
    }

    // If we want it to return the message
    public static Message sendAndReturnMessage(MessageChannel channel, String message)
    {
        Mono<Message> msg = channel.createMessage(message);
        return msg.block();
    }

    // Returning private message
    public static Message sendAndReturnMessage(PrivateChannel channel, String message)
    {
        Mono<Message> msg = channel.createMessage(message);
        return msg.block();
    }
    // Edit message
    public static void editMessage(Message message, String newMessage)
    {
        // Set new content
        Consumer<? super MessageEditSpec> consumer = (Consumer<MessageEditSpec>) messageEditSpec -> messageEditSpec.setContent(newMessage);

        // Actually edit the message
        message.edit(consumer).subscribe();
        return;
    }
}
