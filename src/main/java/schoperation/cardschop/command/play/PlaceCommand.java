package schoperation.cardschop.command.play;

import schoperation.cardschop.command.ICommand;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class PlaceCommand implements ICommand {

    /*
        Places the specified card somewhere.

        place [place] [card] -> places [card] on top of [place]. If no card specified, places the topmost one from the player's hand.

        If nothing specified, place the topmost card in front of the player.

        Available places:
            -underdeck -> slides the card back under the deck.
            -middle -> the pile right by the deck. Usually "discard pile", or games like egyptian rat screw.
            -pile -> player's personal pile.
            -infront -> in front of the player. For tricks.

            Defaults to infront.
     */

    private final String command = "place";

    @Override
    public String getCommand()
    {
        return this.command;
    }

    @Override
    public void execute(IUser sender, IChannel channel, IGuild guild, String arg1, String arg2, String arg3)
    {
        // TODO
        return;
    }
}
