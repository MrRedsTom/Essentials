/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.home;

import com.google.common.collect.Maps;
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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandHome extends CustomCommand implements TabCompleter {

    private Location homeLocation;

    public CommandHome(String homes, String home, BiValMap<String, Boolean, Integer> args) {
        super(homes, home, args);
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
            if(args2.length < 2){
                ((Player) sender).chat("/homes " + args2[0]);
                return false;
            }
            name = args2[0];
            homeName = args2[1].toLowerCase();
            user = Core.getInstance().getUserManager().getPlayer(Bukkit.getOfflinePlayer(name));
            if(user == null || Bukkit.getOfflinePlayer(name) == null)
                throw new NotValidPlayerException(sender, this);
        }
        else{
            user = Core.getInstance().getUserManager().getPlayer((OfflinePlayer) sender);
            homeName = args[0].toLowerCase();
        }
        Player player = (Player) sender;

        if(user.get(String.format("homes.%s", homeName)) == null){
            throw new EnluriaException(this, sender, String.format("Le home &b%s&c n'existe pas !", args[0]));
        }

        double x = (double) user.get(String.format("homes.%s.location.x", homeName));
        double y = (double) user.get(String.format("homes.%s.location.y", homeName));
        double z = (double) user.get(String.format("homes.%s.location.z", homeName));
        String worldName = (String) user.get(String.format("homes.%s.location.world", homeName));
        World world = Bukkit.getWorld(worldName);

        Location location = new Location(world, x, y, z);

        this.homeLocation = location;

        player.teleport(location);
        sendMessage(player, "&7Vous avez été téléporté au home &b" + homeName + (startsWith ? "&7 pour &6" + name + "&7" : "") + " !");

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> toReturn = new ArrayList<>();

        if(args.length > 1) return null;

        Map<OfflinePlayer, Boolean> players = Maps.newHashMap();
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
            players.put(p, true);
        }

        if(sender instanceof Player){
            Player p = (Player) sender;
            EUser user = Core.getInstance().getUserManager().getPlayer(p);

            ConfigurationSection section = user.getYamlConfig().getConfigurationSection("homes");

            if (!(section == null) || !(section.getKeys(false).size() == 0)) {
                for (String key : section.getKeys(false)) {
                    toReturn.add((String) user.get("homes." + key + ".name"));
                }
            }

        }

        /*if(sender.hasPermission("responsable.use")) {

            for (OfflinePlayer player : players.keySet()) {

                if (players.getOrDefault(player, false)) {

                    EUser user = Core.getInstance().getUserManager().getPlayer(player);

                    ConfigurationSection section = user.getYamlConfig().getConfigurationSection("homes");

                    if (section == null || section.getKeys(false).size() == 0) {
                        continue;
                    }

                    toReturn.add(player.getName() + ":");

                    for (String key : section.getKeys(false)) {
                        toReturn.add(player.getName() + ":" + user.get("homes." + key + ".name"));
                    }

                }

            }
        }*/

        toReturn.removeIf(s -> !s.startsWith(args[0]));

        return toReturn;
    }
}
