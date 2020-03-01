/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.home;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import fr.timkyl2203.enluria.utils.users.EUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class CommandSethome extends CustomCommand {
    public CommandSethome(String homes, String sethome, BiValMap<String, Boolean, Integer> args) {
        super(homes, sethome, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(!(sender instanceof Player))
            throw new HaveToBeAPlayerException(sender, this);

        if(args.length < 1 || args.length > 2)
            return true;

        EUser user;

        boolean startsWith = false;

        for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
            if(args[0].startsWith(p.getName() + ":")){
                startsWith = true;
            }
        }
        String homeName;
        String name = null;

        if(startsWith){

            if(!sender.hasPermission("responsable.use"))
            {
                throw new NoPermissionException(this, sender);
            }

            String[] args2 = args[0].split(":");
            name = args2[0];
            homeName = args2[1];
            user = Core.getInstance().getUserManager().getPlayer(Bukkit.getOfflinePlayer(name));
            if(user == null || Bukkit.getOfflinePlayer(name) == null)
                throw new NotValidPlayerException(sender, this);
        }
        else{
            user = Core.getInstance().getUserManager().getPlayer((OfflinePlayer) sender);
            homeName = args[0];
        }

        Player player = (Player) sender;
        Location location = player.getLocation();
        DecimalFormat df = new DecimalFormat("#.##");
        double x = Double.parseDouble(df.format(location.getX()).replaceAll(",", "."));
        double y = Double.parseDouble(df.format(location.getY()).replaceAll(",", "."));
        double z = Double.parseDouble(df.format(location.getZ()).replaceAll(",", "."));
        String worldName = location.getWorld().getName();

        user.set(String.format("homes.%s.name", homeName.toLowerCase()), homeName);
        user.set(String.format("homes.%s.location.x", homeName.toLowerCase()), x);
        user.set(String.format("homes.%s.location.y", homeName.toLowerCase()), y);
        user.set(String.format("homes.%s.location.z", homeName.toLowerCase()), z);
        user.set(String.format("homes.%s.location.world", homeName.toLowerCase()), worldName);

        user.save();

        sendMessage(player, "&7Le home &b" + homeName + "&7 a bien été créé/modifié " + (startsWith ? "pour &6" + name + "&7" : "") + "!");

        return false;
    }
}
