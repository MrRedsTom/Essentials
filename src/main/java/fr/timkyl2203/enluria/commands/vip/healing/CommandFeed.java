/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.vip.healing;

import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.utils.commands.CooldownedCustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFeed extends CooldownedCustomCommand {
    public CommandFeed(String heal, String feed, BiValMap<String, Boolean, Integer> args) {
        super(heal, feed, args, 60);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {
        if(!(sender instanceof Player))
            throw new HaveToBeAPlayerException(sender, this);

        Player player = (Player)sender;
         if(player.hasPermission("moderateur.use")){
            player.setFoodLevel(20);
            player.setSaturation(100f);
            sendMessage(sender, "&7Votre appétit a été rassasié");
        }else
           throw new NoPermissionException(this, sender);

        return false;
    }
}
