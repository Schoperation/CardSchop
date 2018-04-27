package schoperation.cardschop.core;

import schoperation.cardschop.card.Table;
import schoperation.cardschop.command.CreateTableCommand;
import schoperation.cardschop.command.ICommand;
import schoperation.cardschop.command.JoinCommand;
import schoperation.cardschop.command.LeaveCommand;

import java.util.ArrayList;
import java.util.List;

public class Objs {

    /*
        This stores objects of stuff. Just a nice, central place.
     */

    // Tables. These will have their own player lists, and any user can join a table, adding them to the list.
    public static final List<Table> TABLES = new ArrayList<>();

    // Commands
    public static final ICommand COMMANDS[] = {

            new CreateTableCommand(),
            new JoinCommand(),
            new LeaveCommand()
    };
}
