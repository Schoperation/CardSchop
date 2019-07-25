package schoperation.cardschop.util;

import discord4j.core.object.entity.Guild;
import schoperation.cardschop.card.Table;

import java.util.HashMap;
import java.util.List;

public class Tables {

    /*
        This class contains the almighty hashmap, where all tables are stored in memory.
        Every new server that adds this bot gets its own list of tables to mess with.
     */

    public static HashMap<Guild, List<Table>> list = new HashMap<>();
}
