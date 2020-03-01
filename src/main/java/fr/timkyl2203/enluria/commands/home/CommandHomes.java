/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors:
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.home;

import com.google.common.collect.Lists;
import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import fr.timkyl2203.enluria.utils.users.EUser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHomes extends CustomCommand implements TabCompleter {
    public CommandHomes(String homes, String homes1, BiValMap<String, Boolean, Integer> args) {
        super(homes, homes1, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if (!(sender instanceof Player)) {
            throw new HaveToBeAPlayerException(sender, this);
        }

        if (args.length > 1) {
            return true;
        }

        List<String> homes = Lists.newArrayList();

        EUser user;

        if (args.length == 0){
            user = Core.getInstance().getUserManager().getPlayer((Player) sender);



            YamlConfiguration config = user.getYamlConfig();

            ConfigurationSection homeSection = config.getConfigurationSection("homes");

            if(homeSection == null){
                throw new EnluriaException(this, sender, "Vous n'avez pas de homes !");
            }

            if(homeSection.getKeys(false).size() == 0){
                throw new EnluriaException(this, sender, "Vous n'avez pas de homes !");
            }

            for(String s : homeSection.getKeys(false)){

                ConfigurationSection section = homeSection.getConfigurationSection(s);

                homes.add(section.getString("name"));
            }

            StringBuilder homesBuilder = new StringBuilder();
            int i = 0;

            for (String home : homes) {
                i++;
                homesBuilder.append("&a").append(home).append(i == homes.size() ? " " : (i == homes.size() - 1 ? "&7 et " : "&7, "));
            }

            sendMessage(sender, "&7Vos homes sont " + homesBuilder.toString() + "&7!");
        }else {

            if(!sender.hasPermission("responsable.use"))
            {
                throw new NoPermissionException(this, sender);
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            user = Core.getInstance().getUserManager().getPlayer(target);
            if(target == null || user == null){
                throw new NotValidPlayerException(sender, this);
            }



            YamlConfiguration config = user.getYamlConfig();

            ConfigurationSection homeSection = config.getConfigurationSection("homes");

            if(homeSection == null){
                throw new EnluriaException(this, sender, "Le joueur n'a pas de homes !");
            }

            if(homeSection.getKeys(false).size() == 0){
                throw new EnluriaException(this, sender, "Le joueur n'a pas de homes !");
            }

            for(String s : homeSection.getKeys(false)){

                ConfigurationSection section = homeSection.getConfigurationSection(s);

                homes.add(section.getString("name"));
            }

            StringBuilder homesBuilder = new StringBuilder();
            int i = 0;

            for (String home : homes) {
                i++;
                homesBuilder.append("&a").append(home).append(i == homes.size() ? " " : (i == homes.size() - 1 ? "&7 et " : "&7, "));
            }

            sendMessage(sender, "&7Les homes de &6" + user.getPlayer().getName() + "&7 sont " + homesBuilder.toString() + "&7!");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> players = Lists.newArrayList();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            players.add(offlinePlayer.getName());
        }
        players.removeIf(element -> !element.startsWith(strings[0]));
        return players;
    }
}
