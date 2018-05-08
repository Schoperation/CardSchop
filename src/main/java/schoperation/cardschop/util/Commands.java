package schoperation.cardschop.util;

import schoperation.cardschop.command.ClearCommand;
import schoperation.cardschop.command.HelpCommand;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.command.InfoCommand;
import schoperation.cardschop.command.JoinCommand;
import schoperation.cardschop.command.LeaveCommand;
import schoperation.cardschop.command.TableCommand;
import schoperation.cardschop.command.play.CollectCommand;
import schoperation.cardschop.command.play.DealCommand;
import schoperation.cardschop.command.play.DrawCommand;
import schoperation.cardschop.command.play.GiveCommand;
import schoperation.cardschop.command.play.PlaceCommand;
import schoperation.cardschop.command.play.RevealCommand;
import schoperation.cardschop.command.play.SeeCommand;
import schoperation.cardschop.command.play.SetDealerCommand;
import schoperation.cardschop.command.play.ShuffleCommand;
import schoperation.cardschop.command.play.bet.BetCommand;
import schoperation.cardschop.command.play.bet.ChipsCommand;

public class Commands {

    // Command list. This is iterated through when a message is received, to check the command.
    public static final ICommand LIST[] = {

            new TableCommand(),
            new JoinCommand(),
            new LeaveCommand(),
            new SetDealerCommand(),
            new DealCommand(),
            new CollectCommand(),
            new ShuffleCommand(),
            new GiveCommand(),
            new RevealCommand(),
            new SeeCommand(),
            new DrawCommand(),
            new PlaceCommand(),
            new ClearCommand(),
            new BetCommand(),
            new ChipsCommand(),
            new HelpCommand(),
            new InfoCommand()

    };
}
