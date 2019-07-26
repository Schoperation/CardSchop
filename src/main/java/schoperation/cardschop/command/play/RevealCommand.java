package schoperation.cardschop.command.play;

import discord4j.core.object.entity.*;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.MessageEditSpec;
import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;

import java.util.function.Consumer;

public class RevealCommand implements ICommand {

    /*
        Reveals something of yours.

        reveal [something] -> reveals something to EVERYONE.
        reveal [something] [someone] -> reveals something to a specific person.

        Options include:
            -hand -> your hand. This is default, if no arguments are provided.
            -pile -> your personal pile.
            -infront -> the cards in front of you.

        Mention the person for revealing something to just them.
     */

    private final String command = "reveal";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Part of a table?
        if (Utils.isPartOfTable(sender, guild))
        {
            Player player = Utils.getPlayerObj(sender, guild);

            // What place?
            if (arg1.equals("blank") || arg1.equals("hand"))
            {
                // Is someone specified?
                if (arg2.equals("blank"))
                {
                    // Make sure all cards are faceup
                    for (Card c : player.getHand())
                        c.setFaceUp();

                    channel.createMessage(player.getDisplayName() + "'s hand:\n" + player.handToString());
                }
                else
                {
                    // Player they're going to give it to.
                    // Make sure they are part of a table first.
                    arg2 = arg2.replaceAll("[<>@!]", "");
                    Member userFromString = guild.getMemberById(Snowflake.of(Long.parseLong(arg2))).block();

                    // Is this player part of the table?
                    if (Utils.isPartOfTable(userFromString, guild))
                    {
                        Player player2 = Utils.getPlayerObj(userFromString, guild);

                        if (player2.getTable() == player.getTable())
                        {
                            // Make sure all cards are faceup
                            for (Card c : player.getHand())
                                c.setFaceUp();

                            // For editing the msg
                            Consumer<? super MessageEditSpec> newMsg = (Consumer<MessageEditSpec>) messageEditSpec -> messageEditSpec.setContent(player.getDisplayName() + "'s hand:\n" + player.handToString());

                            player2.getPM().block().edit(newMsg);
                            Message msg = player2.getUser().getPrivateChannel().block().createMessage(player.getDisplayName() + " has revealed their hand to you.").block();
                            msg.delete();
                            channel.createMessage(player.getDisplayName() + " revealed their hand to " + player2.getDisplayName() + ".");
                            return;
                        }

                        channel.createMessage(userFromString.getDisplayName() + " is not part of this table!");
                        return;
                    }

                    channel.createMessage(userFromString.getDisplayName() + " is not part of this table!");
                    return;
                }
            }
            else if (arg1.equals("pile"))
            {
                // Is someone specified?
                if (arg2.equals("blank"))
                {
                    // Make sure all cards are faceup
                    for (Card c : player.getPile())
                        c.setFaceUp();

                    channel.createMessage(player.getDisplayName() + "'s side pile:\n" + player.pileToString());
                }
                else
                {
                    // Player they're going to give it to.
                    // Make sure they are part of a table first.
                    arg2 = arg2.replaceAll("[<>@!]", "");
                    Member userFromString = guild.getMemberById(Snowflake.of(Long.parseLong(arg2))).block();

                    // Is this player part of the table?
                    if (Utils.isPartOfTable(userFromString, guild))
                    {
                        Player player2 = Utils.getPlayerObj(userFromString, guild);

                        if (player2.getTable() == player.getTable())
                        {
                            // Make sure all cards are faceup
                            for (Card c : player.getPile())
                                c.setFaceUp();

                            // For editing the msg
                            Consumer<? super MessageEditSpec> newMsg = (Consumer<MessageEditSpec>) messageEditSpec -> messageEditSpec.setContent(player.getDisplayName() + "'s side pile:\n" + player.pileToString());

                            player2.getPM().block().edit(newMsg);
                            Message msg = player2.getUser().getPrivateChannel().block().createMessage(player.getDisplayName() + " has revealed their personal pile to you.").block();
                            msg.delete();
                            channel.createMessage(player.getDisplayName() + " revealed their personal pile to " + player2.getDisplayName() + ".");
                            return;
                        }

                        channel.createMessage(userFromString.getDisplayName() + " is not part of this table!");
                        return;
                    }

                    channel.createMessage(userFromString.getDisplayName() + " is not part of this table!");
                    return;
                }
            }
            else if (arg1.equals("infront") || arg1.equals("trick"))
            {
                // Is someone specified?
                if (arg2.equals("blank"))
                {
                    // Make sure all cards are faceup
                    for (Card c : player.getFront())
                        c.setFaceUp();

                    channel.createMessage(player.getDisplayName() + "'s front cards:\n" + player.frontToString());
                }
                else
                {
                    // Player they're going to give it to.
                    // Make sure they are part of a table first.
                    arg2 = arg2.replaceAll("[<>@!]", "");
                    Member userFromString = guild.getMemberById(Snowflake.of(Long.parseLong(arg2))).block();

                    // Is this player part of the table?
                    if (Utils.isPartOfTable(userFromString, guild))
                    {
                        Player player2 = Utils.getPlayerObj(userFromString, guild);

                        if (player2.getTable() == player.getTable())
                        {
                            // Make sure all cards are faceup
                            for (Card c : player.getFront())
                                c.setFaceUp();

                            // For editing the msg
                            Consumer<? super MessageEditSpec> newMsg = (Consumer<MessageEditSpec>) messageEditSpec -> messageEditSpec.setContent(player.getDisplayName() + "'s front cards:\n" + player.frontToString());

                            player2.getPM().block().edit(newMsg);
                            Message msg = player2.getUser().getPrivateChannel().block().createMessage(player.getDisplayName() + " has revealed their front cards to you.").block();
                            msg.delete();
                            channel.createMessage(player.getDisplayName() + " revealed their front cards to " + player2.getDisplayName() + ".");
                            return;
                        }

                        channel.createMessage(userFromString.getDisplayName() + " is not part of this table!");
                        return;
                    }

                    channel.createMessage(userFromString.getDisplayName() + " is not part of this table!");
                    return;
                }
            }
            else if (arg1.equals("middle"))
            {
                // Must be the dealer to reveal the middle.
                if (player.getTable().getDealer() == player)
                {
                    // Make sure all cards are faceup
                    for (Card c : player.getTable().getMiddlePile())
                        c.setFaceUp();

                    channel.createMessage("Middle pile:\n" + player.getTable().middleToString());
                    return;
                }

                channel.createMessage(Msges.NOT_DEALER);
                return;
            }
            else if (arg1.equals("deck"))
            {
                // Must be the dealer to reveal the deck.
                if (player.getTable().getDealer() == player)
                {
                    // Make sure all cards are faceup
                    for (Card c : player.getTable().getDeck().getCards())
                        c.setFaceUp();

                    channel.createMessage("Deck:\n" + player.getTable().getDeck().getCardsToString());
                    return;
                }

                channel.createMessage(Msges.NOT_DEALER);
                return;
            }
            else
                channel.createMessage("Invalid place. Either chose hand, pile, or infront/trick.");
            
            return;
        }

        channel.createMessage(Msges.NO_TABLE);
        return;
    }
}
