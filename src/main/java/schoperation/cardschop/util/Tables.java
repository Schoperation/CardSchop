package schoperation.cardschop.util;

import schoperation.cardschop.card.Table;
import sx.blah.discord.handle.obj.IGuild;

import java.util.HashMap;
import java.util.List;

public class Tables {

    /*
        This class contains the almighty hashmap, where all tables are stored in memory.
        Every new server that adds this bot gets its own list of tables to mess with.
     */

    public static HashMap<IGuild, List<Table>> list = new HashMap<>();
}
