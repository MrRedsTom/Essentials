/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.modo.other;

import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClear extends CustomCommand {
    public CommandClear(String modération, String clear, BiValMap<String, Boolean, Integer> args) {
        super(modération, clear, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {
        if(!(sender instanceof Player))
            throw new HaveToBeAPlayerException(sender, this);
        if(args.length > 1)
            return true;

        if(args.length == 0) {
            if(!sender.hasPermission("moderateur.use")) {
                throw new NoPermissionException(this, sender);
            }
            Player player = (Player)sender;
            player.getInventory().clear();
            sendMessage(player, "&7Votre inventaire à été clear");
        }else {
            if(!sender.hasPermission("responsable.use")) {
                throw new NoPermissionException(this, sender);
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            Player player = (Player) sender;
            if(target == null) throw new NotValidPlayerException(sender, this);

             target.getInventory().clear();
             sendMessage(target, "&7Vous avez été clear par &b" + player.getDisplayName());
             sendMessage(player, "&7Vous avez clear &b" + target.getDisplayName());
        }
        return false;

    }
}
