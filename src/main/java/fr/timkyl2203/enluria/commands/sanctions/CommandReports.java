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

import java.util.UUID;

public class CommandReports extends CustomCommand {
    public CommandReports(String modération, String reports, BiValMap<String, Boolean, Integer> args) {
        super(modération, reports, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(!(sender.hasPermission("moderateur.use"))) throw new NoPermissionException(this, sender);

        if(args.length != 1) return true;

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UUID uuid = target.getUniqueId();

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(Core.getReports());

        ConfigurationSection targetSection = configuration.getConfigurationSection("reports." + uuid + ".reports");

        if(targetSection == null){
            throw new EnluriaException(this, sender, "Le joueur mentionné n'a pas de reports");
        }

        sendMessage(sender, "&cLes reports de " + target.getName() + " sont :");

        for(String sectionN : targetSection.getKeys(false)){

            ConfigurationSection section = targetSection.getConfigurationSection(sectionN);

            int id = section.getInt("id");
            OfflinePlayer reporter = Bukkit.getOfflinePlayer(UUID.fromString(section.getString("reporter.uuid")));
            String reason = section.getString("reason");

            sendMessage(sender, String.format(" ○ &cReport &7#%s&c by &7%s&c &l→ &7%s", id, reporter.getName(), reason));


        }

        return false;
    }
}
