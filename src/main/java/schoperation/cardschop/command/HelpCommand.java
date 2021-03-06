package schoperation.cardschop.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import schoperation.cardschop.util.Msges;
import schoperation.cardschop.util.PostalService;

public class HelpCommand implements ICommand {


    /*
        Receive help.

        help -> introduction.

        help list -> list of commands.

        help [command] -> gets help for that specific command.
     */

    private final String command = "help";

    public String getCommand()
    {
        return this.command;
    }

    public void execute(User sender, MessageChannel channel, Guild guild, String arg1, String arg2, String arg3)
    {
        // Stringbuilder
        StringBuilder sb = new StringBuilder();

        // No arguments? Introduction.
        switch (arg1) {

            case "blank":
                sb.append("CardSchop is a bot specifically made for playing almost all card games in Discord.\n\n");
                sb.append("To start off, you'll want to create a table. Tables are where all of the card-playing action happens.\n");
                sb.append("To create one, use `" + Msges.PREFIX + "table create [name]`, where [name] is some custom name for the table.\n\n");
                sb.append("With that set up, use `" + Msges.PREFIX + "join [table]`, putting the name of the table into [table].\n\n");

                sb.append("Each table comes with its own deck of cards, as shown by the [52] in the middle.\n");
                sb.append("Above that is where a middle pile can be formed. Use this as a discard pile or whatever.\n");
                sb.append("Finally, the 0 below the deck represents the table's pot, where everyone's (fake) chips will be thrown into.\n\n");

                sb.append("You, as a player, have a personal chip store, represented by the 0 below your name, printed by the table.\n");
                sb.append("You personally have 3 places where cards can go:\n");
                sb.append("\t-Your personal hand, labeled as `hand`. Of course, this is your main hand.\n");
                sb.append("\t-Your personal pile, labeled as `pile`. Use this to store cards that you don't want to keep in your main hand.\n");
                sb.append("\t-Your \"front\" pile, labeled as `infront` and `trick`. Use this to play a card into a trick, or to generally place a card in front of you.\n\n");

                sb.append("Before you really do anything, set someone as the dealer, as only they can deal cards, set chips, collect cards, etc.\n");
                sb.append("Use `" + Msges.PREFIX + "setdealer [name]` to do so. Leave out the [name] argument to make yourself the dealer, or slap on a name to make them the dealer. If you're providing a name, mention them.\n\n");

                sb.append("Now... do `" + Msges.PREFIX + "help list` to get the full list of commands.\n");
                sb.append("You can then do `" + Msges.PREFIX + "help [command]` to get more information about a command.\n\n");

                sb.append("Cheers,\n");
                sb.append("Schoperation");
                break;

            // List of commands
            case "list":
                sb.append("```");
                sb.append("Prefix is " + Msges.PREFIX + ". Use `" + Msges.PREFIX + "help [command]` for more details and arguments about said command.\n");
                sb.append("\nBasic\n");
                sb.append("\tinfo -> See some miscellaneous information about this bot.\n");
                sb.append("\thelp -> Stop it. Get some help.\n");

                sb.append("\nTable\n");
                sb.append("\ttable -> Deal with the creation, deletion, and flipping of tables.\n");
                sb.append("\tjoin [table] -> Join the table named [table].\n");
                sb.append("\tleave -> Leave the current table.\n");
                sb.append("\tclear -> Clear the table's log.\n");

                sb.append("\nPlay\n");
                sb.append("\tsetdealer -> Set the dealer to someone.\n");
                sb.append("\tdeal -> DEALER ONLY. Deal out a bunch of cards from the deck to everyone at the table.\n");
                sb.append("\tshuffle -> DEALER ONLY. Shuffle the deck.\n");
                sb.append("\tdeck -> DEALER ONLY. Edit the deck.\n");
                sb.append("\tdraw -> Draw/Take a card from some pile.\n");
                sb.append("\tplace -> Place a card onto some pile.\n");
                sb.append("\tgive -> Give a card to someone else.\n");
                sb.append("\tcollect -> Collect something.\n");
                sb.append("\tslap -> Slap the middle pile.\n");

                sb.append("\n(Own) Cards\n");
                sb.append("\tsee -> See your hand/pile/front cards PRIVATELY.\n");
                sb.append("\treveal -> Reveal your hand/pile/front cards PUBLICLY.\n");
                sb.append("\tsort -> Sort your hand.\n");
                sb.append("\tflip -> Flip a card in your hand.\n");

                sb.append("\nChips\n");
                sb.append("\tbet -> Throw an amount of chips into the pot.\n");
                sb.append("\tchips -> DEALER ONLY. Mess with someone's chips.\n");

                sb.append("```");
                break;

            // Specific commands
            case "info":
                sb.append("```");

                sb.append(" info\n\n");
                sb.append("\tinfo -> Display some information about this bot.\n");

                sb.append("\nIt's great\n");

                sb.append("```");
                break;

            case "help":
                sb.append("```");

                sb.append(" help\n\n");
                sb.append("\thelp -> Show a basic startup guide for using this bot.\n");
                sb.append("\thelp list -> Show a list of commands.\n");
                sb.append("\thelp [command] -> Show more details and arguments about a command.\n");

                sb.append("\nYou just used this command, and you appear to be pretty good at it!\n");

                sb.append("```");
                break;

            case "table":
                sb.append("```");

                sb.append(" table\n\n");
                sb.append("\ttable create [name] -> Creates a new table called [name].\n");
                sb.append("\ttable delete [name] -> Deletes the table called [name].\n");
                sb.append("\ttable list -> Lists every table created on your server.\n");
                sb.append("\ttable flip -> Flips the current table you're at.\n");

                sb.append("\nA table is where the card-playing happens. Create one and join it to start playing.\n");

                sb.append("```");
                break;

            case "join":
                sb.append("```");

                sb.append(" join\n\n");
                sb.append("\tjoin [name] -> Join the table called [name].\n");

                sb.append("\nThis is how you join a table. Of course, such table must exist.\n");

                sb.append("```");
                break;

            case "leave":
                sb.append("```");

                sb.append(" leave\n\n");
                sb.append("\tleave -> Leave the current table.\n");

                sb.append("\nThis is how you leave a table. Of course, you must be at one to leave it.\n");

                sb.append("```");
                break;

            case "clear":
                sb.append("```");

                sb.append(" clear\n\n");
                sb.append("\tclear -> Clear the entire log of a table.\n");
                sb.append("\tclear [amount] -> Clear a certain amount of lines of the log of a table.\n");

                sb.append("\nClears the table log and anything in its way. As a table is not specified, it'll use the table you're a part of, so you must be part of a table to use this command.\n");

                sb.append("```");
                break;

            case "setdealer":
                sb.append("```");

                sb.append(" setdealer\n\n");
                sb.append("\tsetdealer -> Set yourself as the dealer of the table.\n");
                sb.append("\tsetdealer [name] -> Set the specified user as the dealer of the table.\n");

                sb.append("\nThe dealer is pretty important. He/She has access to some exclusive commands such as deal and chips. When setting someone else as the dealer, mention them (@someone#1234).\n");

                sb.append("```");
                break;

            case "deal":
                sb.append("```");

                sb.append(" deal\n\n");
                sb.append("\tdeal -> Deal all cards to everyone, one at a time.\n");
                sb.append("\tdeal [amount] -> Deal the specified amount of cards to everyone, one at a time.\n");
                sb.append("\tdeal [amount] [player] -> Deal the specified amount of cards to the specified person. For [player], mention them.\n");
                sb.append("\tdeal [amount] [atatime] -> Deal the specified amount of cards to everyone, a specified amount at a time. Ex. deal 5 2 deals 5 cards to everyone, 2 cards at a time.\n");
                sb.append("\tdeal [amount] [atatime] [dealergetscards] -> Like above, but can specify whether the dealer also gets cards. Use 'true' or 'false' for this argument.\n");


                sb.append("\nA pretty essential command. Only the dealer can use this. This takes cards directly from the deck.\n");
                sb.append("Keep in mind that the deck doesn't start out shuffled. Use " + Msges.PREFIX + "shuffle to do that thing.\n");

                sb.append("```");
                break;

            case "shuffle":
                sb.append("```");

                sb.append(" shuffle\n\n");
                sb.append("\tshuffle -> Shuffle the deck.\n");

                sb.append("\nDealer only. The deck does NOT start out shuffle upon creating a table. So, use this command before you use deal, unless you don't want it shuffled.\n");

                sb.append("```");
                break;

            case "deck":
                sb.append("```");

                sb.append(" deck\n\n");
                sb.append("\tdeck [operator] [card] -> Edit the deck.\n");

                sb.append("\nDealer only.\n");
                sb.append("Options for operator are:\n");
                sb.append("\tadd -> Add a card, or cards.\n");
                sb.append("\tremove -> Remove a card, or cards.\n");
                sb.append("\treset -> Resets the deck to the standard 52 cards.\n");

                sb.append("\nFor [card], you can either specify the card directly, like so:\n");
                sb.append("Take the 2 of clubs, for example. Acceptable input would include:\n");
                sb.append("\t2clubs\n");
                sb.append("\t2ofclubs\n\n");

                sb.append("For the king of diamonds, say:\n");
                sb.append("\tkingofdiamonds\n");
                sb.append("\tkingdiamonds\n");
                sb.append("\tkofdiamonds\n");
                sb.append("\tkdiamonds\n\n");

                sb.append("For face cards and aces, you can either spell their rank out or use the first letter.\n");
                sb.append("For other ranks use the actual number.\n");
                sb.append("Nonetheless, you must specify the rank and the suit correctly, in ONE WORD.\n");

                sb.append("\nYou can also omit the suit (so just '2' or just 'king' or 'kings') to add/remove ALL cards of that rank.\n");
                sb.append("Along with that, omit the rank (so just 'clubs', 'diamonds', 'hearts', or 'spades') to add/remove ALL cards of that suit.\n");
                sb.append("Specify 'deck' to add a second deck to the existing one.\n");

                sb.append("```");
                break;

            case "draw":
                sb.append("```");

                sb.append(" draw\n\n");
                sb.append("\tdraw -> Take a card from the deck, into your hand.\n");
                sb.append("\tdraw [place] -> Take a card from the specified place.\n");
                sb.append("\tdraw [place] [amount] -> Take a specified amount of cards from the specified place.\n");

                sb.append("\nThe options for [place] are:\n");
                sb.append("\tdeck -> The main deck.\n");
                sb.append("\tmiddle -> The pile in the middle of the table, above the deck. The discard pile.\n");
                sb.append("\tpile -> Your personal side pile. Useful for storing cards you don't want in your hand.\n");
                sb.append("\tinfront -> Cards in front of you. For tricks.\n");
                sb.append("\ttrick -> Same as infront.\n");

                sb.append("\nUse 'all' for [amount] to take all cards from that place.\n");

                sb.append("```");
                break;

            case "place":
                sb.append("```");

                sb.append(" place\n\n");
                sb.append("\tplace -> Place your topmost card in the middle pile.\n");
                sb.append("\tplace [faceupordown] -> Place your topmost card in the middle pile, faceup or facedown.\n");
                sb.append("\tplace [place] -> Place your topmost card in the specified place.\n");
                sb.append("\tplace [place] [faceupordown] -> Place your topmost card in the specified place, faceup or facedown.\n");
                sb.append("\tplace [place] [card] -> Place a specified card in the specified place.\n");
                sb.append("\tplace [place] [card] [faceupordown] -> Place a specified card in the specified place, faceup or facedown.\n");

                sb.append("\nThe options for [place] are:\n");
                sb.append("\tunderdeck -> Slides the card UNDER the main deck.\n");
                sb.append("\tmiddle -> The pile in the middle of the table, above the deck. The discard pile.\n");
                sb.append("\tpile -> Your personal side pile. Useful for storing cards you don't want in your hand.\n");
                sb.append("\tinfront -> Cards in front of you. For tricks.\n");
                sb.append("\ttrick -> Same as infront.\n");

                sb.append("\nFor [card], you can either use the number assigned to the card in brackets [],\n");
                sb.append("Or spell out the card. Let's take the 2 of clubs, for example. Acceptable input would include:\n");
                sb.append("\t2clubs\n");
                sb.append("\t2ofclubs\n\n");

                sb.append("For the king of diamonds, say:\n");
                sb.append("\tkingofdiamonds\n");
                sb.append("\tkingdiamonds\n");
                sb.append("\tkofdiamonds\n");
                sb.append("\tkdiamonds\n\n");

                sb.append("For face cards and aces, you can either spell their rank out or use the first letter.\n");
                sb.append("For other ranks use the actual number.\n");
                sb.append("Nonetheless, you must specify the rank and the suit correctly, in ONE WORD.\n");

                sb.append("\nFor [faceupordown], say 'faceup' or 'facedown', and it'll flip the card accordingly.\n");

                sb.append("```");
                break;

            case "give":
                sb.append("```");

                sb.append(" give\n\n");
                sb.append("\tgive [player] -> Give the specified player your topmost card.\n");
                sb.append("\tgive [player] [card] -> Give the specified player the specified card.\n");

                sb.append("\nThe card must be in your hand. [player] should be mentioned.\n");
                sb.append("\nFor [card], you can either use the number assigned to the card in brackets [],\n");
                sb.append("Or spell out the card. Let's take the 2 of clubs, for example. Acceptable input would include:\n");
                sb.append("\t2clubs\n");
                sb.append("\t2ofclubs\n\n");

                sb.append("For the king of diamonds, say:\n");
                sb.append("\tkingofdiamonds\n");
                sb.append("\tkingdiamonds\n");
                sb.append("\tkofdiamonds\n");
                sb.append("\tkdiamonds\n\n");

                sb.append("For face cards and aces, you can either spell their rank out or use the first letter.\n");
                sb.append("For other ranks use the actual number.\n");
                sb.append("Nonetheless, you must specify the rank and the suit correctly, in ONE WORD.\n");

                sb.append("```");
                break;

            case "collect":
                sb.append("```");

                sb.append(" collect\n\n");
                sb.append("\tcollect [something] -> Collect the specified thing.\n");

                sb.append("\nYour options are:\n");
                sb.append("\tcards -> Collects everyone's cards and all cards on the table, back into the deck. Only the dealer can do this.\n");
                sb.append("\ttrick -> Collects the trick, or everyone's front cards, into your personal pile.\n");
                sb.append("\tinfront -> Same as trick.\n");
                sb.append("\tmiddle -> Collects the cards in the middle pile, into your personal pile.\n");
                sb.append("\tpot -> Collects the table's pot into your chip bank/pot/whatever.\n");

                sb.append("\nYou MUST provide an argument; there's no default for this one.\n");

                sb.append("```");
                break;

            case "slap":
                sb.append("```");

                sb.append(" slap\n\n");
                sb.append("\tslap -> Slap the middle pile.\n");

                sb.append("\nUse this command for slapping, for games like egyptian rat screw.\n");

                sb.append("```");
                break;

            case "see":
                sb.append("```");

                sb.append(" see\n\n");
                sb.append("\tsee -> See your hand privately.\n");
                sb.append("\tsee [place] -> See something privately.\n");

                sb.append("\nYour options for [place] are:\n");
                sb.append("\thand -> Your own hand.\n");
                sb.append("\tpile -> Your personal pile.\n");
                sb.append("\tinfront -> Your cards in front of you.\n");
                sb.append("\ttrick -> Same as infront.\n");
                sb.append("\tmiddle -> Dealer only. The middle pile of the table.\n");
                sb.append("\tdeck -> Dealer only. The deck of the table.\n");

                sb.append("\nThis command PMs you the cards. Many commands will automatically call this command for your hand, whenever it gets updated.\n");

                sb.append("```");
                break;

            case "reveal":
                sb.append("```");

                sb.append(" reveal\n\n");
                sb.append("\treveal -> Reveal your hand publicly.\n");
                sb.append("\treveal [place] -> Reveal something publicly.\n");
                sb.append("\treveal [place] [player] -> Reveal something to the specified person.\n");

                sb.append("\nYour options for [place] are:\n");
                sb.append("\thand -> Your own hand.\n");
                sb.append("\tpile -> Your personal pile.\n");
                sb.append("\tinfront -> Your cards in front of you.\n");
                sb.append("\ttrick -> Same as infront.\n");
                sb.append("\tmiddle -> Dealer only. The middle pile of the table.\n");
                sb.append("\tdeck -> Dealer only. The deck of the table.\n");

                sb.append("\nFor [player], mention the user.\n");

                sb.append("\nThis command messages the cards in the same channel if no one is specified, so everyone can see them. Use this for the showdown.\n");
                sb.append("Any cards revealed WILL BE FACE UP, regardless if they already are or not.\n");

                sb.append("```");
                break;

            case "sort":
                sb.append("```");

                sb.append(" sort\n\n");
                sb.append("\tsort -> Sort your hand by rank.\n");
                sb.append("\tsort [by] -> Sort your hand by [by].\n");

                sb.append("\nYour options for [by] are:\n");
                sb.append("\tbyrank -> Rank takes precedence.\n");
                sb.append("\tbysuit -> Suit takes precedence. Clubs, Diamonds, Hearts, Spades, is the order.\n");

                sb.append("\nThis command sorts your hand, for your convenience.\n");

                sb.append("```");
                break;

            case "flip":
                sb.append("```");

                sb.append(" flip\n\n");
                sb.append("\tflip [card] -> Flip the specified card.\n");
                sb.append("\tflip [card] [faceupordown] -> Flip the specified card to the specified side.\n");

                sb.append("\nThe card must be in your hand. This command only affects cards in your hand.\n");
                sb.append("\nFor [card], you can either use the number assigned to the card in brackets [], use 'all' for all cards in your hand,\n");
                sb.append("Or spell out the card. Let's take the 2 of clubs, for example. Acceptable input would include:\n");
                sb.append("\t2clubs\n");
                sb.append("\t2ofclubs\n\n");

                sb.append("For the king of diamonds, say:\n");
                sb.append("\tkingofdiamonds\n");
                sb.append("\tkingdiamonds\n");
                sb.append("\tkofdiamonds\n");
                sb.append("\tkdiamonds\n\n");

                sb.append("For face cards and aces, you can either spell their rank out or use the first letter.\n");
                sb.append("For other ranks use the actual number.\n");
                sb.append("Nonetheless, you must specify the rank and the suit correctly, in ONE WORD.\n");

                sb.append("\nFor [faceupordown], say 'faceup' or 'facedown', and it'll flip the card accordingly.\n");

                sb.append("```");
                break;

            case "bet":
                sb.append("```");

                sb.append(" bet\n\n");
                sb.append("\tbet [amount] -> Throw a certain amount of chips into the table's pot.\n");

                sb.append("\nOf course, you'll need to have enough chips in order to bet such amount.\n");
                sb.append("The dealer can use " + Msges.PREFIX + "chips to give everyone chips, if desired.\n");
                sb.append("Schoperation is not responsible for the loss of your life savings nor of your Honda.\n");
                sb.append("We all make choices.\n");

                sb.append("```");
                break;

            case "chips":
                sb.append("```");

                sb.append(" chips\n\n");
                sb.append("\tchips [player] [operator] [amount] -> Mess with a player's chips.\n");

                sb.append("\nOnly the dealer may use this command. All three arguments must be provided.\n");
                sb.append("For [operator], your options are:\n");
                sb.append("\tadd -> Add chips.\n");
                sb.append("\tsubtract -> Subtract chips.\n");
                sb.append("\tsub -> Same as subtract.\n");
                sb.append("\tset -> Set someone's chips.\n");

                sb.append("\nFor [player], either mention a single user, or type 'all' to affect everyone's chips.\n");
                sb.append("You may use any amount of chips, but try to keep it in the billions or less, so the numbers can properly fit on the table message.\n");

                sb.append("```");
                break;

            default:
                sb.append(Msges.INVALID_COMMAND);
                break;
        }

        // Send the message
        PostalService.sendMessage(sender.getPrivateChannel().block(), sb.toString());
        return;
    }
}
