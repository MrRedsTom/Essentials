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

public class CommandS extends CustomCommand {
    public CommandS(String modération, String s, BiValMap<String, Boolean, Integer> args) {
        super(modération, s, args);

    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {
        if(!(sender instanceof Player))
            throw new HaveToBeAPlayerException(sender, this);

        if (args.length != 1)
            return true;

        if(!sender.hasPermission("moderateur.use")){
            throw new NoPermissionException(this, sender);
        }

        if (args.length == 1) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                throw new NotValidPlayerException(sender, this);
            }

            target.teleport(player);
            sendMessage(player, "&7Vous avez téléporté &b" + target.getDisplayName() + "&7 à vous !");
            sendMessage(target, "&7Vous avez été téléporté par &b" + player.getDisplayName());

        }
        return false;
    }
}
