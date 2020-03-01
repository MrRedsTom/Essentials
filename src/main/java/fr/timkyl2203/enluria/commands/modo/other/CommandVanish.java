/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors:
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.modo.other;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import fr.timkyl2203.enluria.utils.users.EUser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVanish extends CustomCommand {
    public CommandVanish(String vanish, String vanish2, BiValMap<String, Boolean, Integer> args) {
        super(vanish, vanish2, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {
        if (!(sender instanceof Player))
            throw new HaveToBeAPlayerException(sender, this);

        Player player = (Player)sender;

        if(args.length == 0) {

            EUser user = Core.getInstance().getUserManager().getPlayer(player);

            boolean vanished = (boolean) user.get("vanish");

            if(vanished){
                //TODO ENLEVER LE VANISH (Player#showPlayer(Player))
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.showPlayer(player);
                }
                sendMessage(sender, "&7Vanish désactivé");
            }
            else{
                //TODO METTRE LE VANISH
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if(target.hasPermission("moderateur.use")) continue;
                    target.hidePlayer(player);
                }
                sendMessage(sender, "&7Vanish activé");
            }


            user.set("vanish", !vanished);

        }

        return false;
    }
}

