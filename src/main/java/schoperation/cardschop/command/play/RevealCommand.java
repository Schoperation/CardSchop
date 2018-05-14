package schoperation.cardschop.command.play;

import schoperation.cardschop.card.Player;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.Utils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

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

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
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
                    channel.sendMessage(sender.getDisplayName(guild) + "'s hand:\n" + player.handToString());
                else
                {
                    // Player they're going to give it to.
                    // Make sure they are part of a table first.
                    arg2 = arg2.replaceAll("[<>@!]", "");
                    IUser userFromString = guild.getUserByID(Long.parseLong(arg2));

                    // Is this player part of the table?
                    if (Utils.isPartOfTable(userFromString, guild))
                    {
                        Player player2 = Utils.getPlayerObj(userFromString, guild);

                        if (player2.getTable() == player.getTable())
                        {
                            player2.getPM().edit(sender.getDisplayName(guild) + "'s hand:\n" + player.handToString());
                            IMessage msg = player2.getUser().getOrCreatePMChannel().sendMessage(sender.getDisplayName(guild) + " has revealed their hand to you.");
                            msg.delete();
                            return;
                        }

                        channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                        return;
                    }

                    channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                    return;
                }
            }
            else if (arg1.equals("pile"))
            {
                // Is someone specified?
                if (arg2.equals("blank"))
                    channel.sendMessage(sender.getDisplayName(guild) + "'s side pile:\n" + player.pileToString());
                else
                {
                    // Player they're going to give it to.
                    // Make sure they are part of a table first.
                    arg2 = arg2.replaceAll("[<>@!]", "");
                    IUser userFromString = guild.getUserByID(Long.parseLong(arg2));

                    // Is this player part of the table?
                    if (Utils.isPartOfTable(userFromString, guild))
                    {
                        Player player2 = Utils.getPlayerObj(userFromString, guild);

                        if (player2.getTable() == player.getTable())
                        {
                            player2.getPM().edit(sender.getDisplayName(guild) + "'s side pile:\n" + player.pileToString());
                            IMessage msg = player2.getUser().getOrCreatePMChannel().sendMessage(sender.getDisplayName(guild) + " has revealed their personal pile to you.");
                            msg.delete();
                            return;
                        }

                        channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                        return;
                    }

                    channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                    return;
                }
            }
            else if (arg1.equals("infront") || arg1.equals("trick"))
            {
                // Is someone specified?
                if (arg2.equals("blank"))
                    channel.sendMessage(sender.getDisplayName(guild) + "'s front cards:\n" + player.frontToString());
                else
                {
                    // Player they're going to give it to.
                    // Make sure they are part of a table first.
                    arg2 = arg2.replaceAll("[<>@!]", "");
                    IUser userFromString = guild.getUserByID(Long.parseLong(arg2));

                    // Is this player part of the table?
                    if (Utils.isPartOfTable(userFromString, guild))
                    {
                        Player player2 = Utils.getPlayerObj(userFromString, guild);

                        if (player2.getTable() == player.getTable())
                        {
                            player2.getPM().edit(sender.getDisplayName(guild) + "'s front cards:\n" + player.frontToString());
                            IMessage msg = player2.getUser().getOrCreatePMChannel().sendMessage(sender.getDisplayName(guild) + " has revealed their front cards to you.");
                            msg.delete();
                            return;
                        }

                        channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                        return;
                    }

                    channel.sendMessage(userFromString.getDisplayName(guild) + " is not part of this table!");
                    return;
                }
            }
            else if (arg1.equals("middle"))
            {
                // Must be the dealer to reveal the middle.
                if (player.getTable().getDealer() == player)
                {
                    channel.sendMessage("Middle pile:\n" + player.getTable().middleToString());
                    return;
                }

                channel.sendMessage(Msges.NOT_DEALER);
                return;
            }
            else if (arg1.equals("deck"))
            {
                // Must be the dealer to reveal the deck.
                if (player.getTable().getDealer() == player)
                {
                    channel.sendMessage("Deck:\n" + player.getTable().getDeck().getCardsToString());
                    return;
                }

                channel.sendMessage(Msges.NOT_DEALER);
                return;
            }
            else
                channel.sendMessage("Invalid place. Either chose hand, pile, or infront/trick.");
            
            return;
        }

        channel.sendMessage(Msges.NO_TABLE);
        return;
    }
}
