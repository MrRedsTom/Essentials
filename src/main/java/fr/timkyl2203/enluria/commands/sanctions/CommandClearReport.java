/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.sanctions;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class CommandClearReport extends CustomCommand {
    public CommandClearReport(String modération, String s, BiValMap<String, Boolean, Integer> args) {
        super(modération, s, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(!(sender.hasPermission("responsable.use"))) throw new NoPermissionException(this, sender);

        if(args.length != 1) return true;

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(Core.getReports());


        if(args[0].startsWith("#")) {
            if(Integer.parseInt(args[0].replace("#", "")) == 0) return true;
            int mentionned = Integer.parseInt(args[0].replace("#", ""));
            boolean ok = false;

            for (String s : configuration.getConfigurationSection("reports").getKeys(false)) {

                for (String s1 : configuration.getConfigurationSection("reports." + s).getKeys(false)) {

                    ConfigurationSection reportsB = configuration.getConfigurationSection("reports." + s + "." + "reports");

                    if(reportsB == null) continue;

                    for (String s2 : reportsB.getKeys(false)) {

                        if (reportsB.getInt(s2 + ".id") == mentionned) {
                            configuration.set(reportsB.getCurrentPath() + "." + s2, null);
                            ok = true;
                            break;
                        }

                    }

                }

            }

            if (ok) {
                sendMessage(sender, "&aLe report d'id &7#" + mentionned + "&a a bien été supprimé");
                try {
                    configuration.save(Core.getReports());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                sendMessage(sender, "&cLe report d'id &7#" + mentionned + " &cn'a pas pu être trouvé");
            }
        } else {

            OfflinePlayer p = null;

            for (OfflinePlayer p2 : Bukkit.getOfflinePlayers()){
                if(p2.getName().equalsIgnoreCase(args[0])){
                    p = p2;
                    break;
                }
            }

            if(p == null) {
                sendMessage(sender, "&cLe joueur &7" + args[0] + "&c n'a pas pu être trouvé ! ");
                sendMessage(sender, "&8Vous ne cherchiez pas un joueur, essayez de rajouter un &7# &8devant l'id du report que vous cherchiez");
                return false;
            }

            configuration.set("reports." + p.getUniqueId() + ".reports", null);

            sendMessage(sender, "&aTous les reports du joueur &7" + p.getName() + "&a ont bien été supprimés");
            try {
                configuration.save(Core.getReports());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
