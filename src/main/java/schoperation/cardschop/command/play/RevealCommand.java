package schoperation.cardschop.command.play;

import discord4j.core.object.entity.*;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.MessageEditSpec;
import schoperation.cardschop.card.Card;
import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;
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

                    PostalService.sendMessage(channel, player.getDisplayName() + "'s hand:\n" + player.handToString());
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

                            PostalService.editMessage(player2.getPM(), player.getDisplayName() + "'s hand:\n" + player.handToString());

                            Message msg = player2.getUser().getPrivateChannel().block().createMessage(player.getDisplayName() + " has revealed their hand to you.").block();
                            msg.delete().subscribe();
                            PostalService.sendMessage(channel, player.getDisplayName() + " revealed their hand to " + player2.getDisplayName() + ".");
                            return;
                        }

                        PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
                        return;
                    }

                    PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
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

                    PostalService.sendMessage(channel, player.getDisplayName() + "'s side pile:\n" + player.pileToString());
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

                            PostalService.editMessage(player2.getPM(), player.getDisplayName() + "'s side pile:\n" + player.pileToString());

                            Message msg = player2.getUser().getPrivateChannel().block().createMessage(player.getDisplayName() + " has revealed their personal pile to you.").block();
                            msg.delete().subscribe();
                            PostalService.sendMessage(channel, player.getDisplayName() + " revealed their personal pile to " + player2.getDisplayName() + ".");
                            return;
                        }

                        PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
                        return;
                    }

                    PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
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

                    PostalService.sendMessage(channel, player.getDisplayName() + "'s front cards:\n" + player.frontToString());
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

                            PostalService.editMessage(player2.getPM(), player.getDisplayName() + "'s front cards:\n" + player.frontToString());
                            Message msg = player2.getUser().getPrivateChannel().block().createMessage(player.getDisplayName() + " has revealed their front cards to you.").block();
                            msg.delete().subscribe();
                            PostalService.sendMessage(channel, player.getDisplayName() + " revealed their front cards to " + player2.getDisplayName() + ".");
                            return;
                        }

                        PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
                        return;
                    }

                    PostalService.sendMessage(channel, userFromString.getDisplayName() + " is not part of this table!");
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

                    PostalService.sendMessage(channel, "Middle pile:\n" + player.getTable().middleToString());
                    return;
                }

                PostalService.sendMessage(channel, Msges.NOT_DEALER);
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

                    PostalService.sendMessage(channel, "Deck:\n" + player.getTable().getDeck().getCardsToString());
                    return;
                }

                PostalService.sendMessage(channel, Msges.NOT_DEALER);
                return;
            }
            else
                PostalService.sendMessage(channel, "Invalid place. Either chose hand, pile, or infront/trick.");
            
            return;
        }

        PostalService.sendMessage(channel, Msges.NO_TABLE);
        return;
    }
}
