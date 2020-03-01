/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.other.tp;

import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.command.CommandSender;

public class CommandSpawn extends CustomCommand {
    public CommandSpawn(String name, String command, BiValMap<String, Boolean, Integer> args) {
        super(name, command, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        /*if(!(sender instanceof Player))
            throw new HaveToBeAPlayerException(sender, this);

        if(args.length != 0) return true;

        FileConfiguration configuration = Core.getInstance().getConfig();

        if(configuration.getDouble("spawn.x") == 0 && configuration.getDouble("spawn.y") == 0 && configuration.getDouble("spawn.z") == 0){
            throw new EnluriaException(this, sender, "Le spawn n'a pas encore été défini");
        }

        String worldName = configuration.getString("spawn.world");
        double x = configuration.getDouble("spawn.x");
        double y = configuration.getDouble("spawn.y");
        double z = configuration.getDouble("spawn.z");

        World w = Bukkit.getWorld(worldName);

        Location spawn = new Location(w, x, y, z);

        ((Player) sender).teleport(spawn);
        sendMessage(sender, "&7Vous avez été téléporté au spawn");*/

        return false;
    }
}
