/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors:
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.modo.tp;

import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTp extends CustomCommand {
    public CommandTp(String name, String command, BiValMap<String, Boolean, Integer> args) {
        super(name, command, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {
        if(!sender.hasPermission("moderateur.use")){
            throw new NoPermissionException(this, sender);
        }
        if(!(sender instanceof Player)) throw new HaveToBeAPlayerException(sender, this);

        if(args.length < 1) return true;

        if(args.length == 1) {
            Player player = (Player)sender;
            Player target = Bukkit.getPlayerExact(args[0]);

            if(target == null) throw new NotValidPlayerException(sender, this);

            player.teleport(target);
            sendMessage(player, String.format("&7Vous venez de vous téléporter à &b" + target.getDisplayName()));
        }else if(args.length == 2){
            Player player = (Player) sender;
            Player target1  = Bukkit.getPlayerExact(args[0]);
            Player target2  = Bukkit.getPlayerExact(args[1]);

            if(target1 == null || target2 == null) throw new NotValidPlayerException(sender, this);

            target1.teleport(target2);
            sendMessage(target1, "&7Vous avez été téléporté à &b" + target2.getDisplayName());
            sendMessage(player, "&7Vous avez téléporté &b" + target1.getDisplayName() + "&7 à &b" + target2.getDisplayName());
        }

        return false;
    }
}
