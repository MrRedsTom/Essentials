/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.sanctions;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.utils.commands.CooldownedCustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CommandReport extends CooldownedCustomCommand {
    public CommandReport(String modération, String report, BiValMap<String, Boolean, Integer> args) {
        super(modération, report, args, 60);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(args.length < 2) return true;

        if(!(sender instanceof Player)) {
            throw new HaveToBeAPlayerException(sender, this);
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[0]);

        StringBuilder reason = new StringBuilder();
        for(int i = 1; i < args.length; i++)
            reason.append(args[i]).append(i == args.length - 1 ? "" : " ");

        if(target == null) {
            resetCooldown(player);
            throw new NotValidPlayerException(sender, this);
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(Core.getReports());
        int current = configuration.getInt("current_id") + 1;
        configuration.set("current_id", current);

        configuration.set(String.format("reports.%s.name", target.getUniqueId()), target.getName());
        configuration.set(String.format("reports.%s.uuid", target.getUniqueId()), target.getUniqueId().toString());
        configuration.set(String.format("reports.%s.reports.%s.id", target.getUniqueId(), current), current);
        configuration.set(String.format("reports.%s.reports.%s.reason", target.getUniqueId(), current), reason.toString());
        configuration.set(String.format("reports.%s.reports.%s.reporter.uuid", target.getUniqueId(), current), player.getUniqueId().toString());
        configuration.set(String.format("reports.%s.reports.%s.reporter.name", target.getUniqueId(), current), player.getName());

        try {
            configuration.save(Core.getReports());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMessage(sender, String.format("&7Vous avez bien rapporté &b%s&7 pour &b%s &7(Report #%s)", target.getDisplayName(), reason.toString(), current));

        return false;
    }
}
