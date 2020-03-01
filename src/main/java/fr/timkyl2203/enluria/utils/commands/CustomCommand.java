/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors:
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.utils.commands;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class CustomCommand
        implements CommandExecutor {

    CommandPlaceholders placeholders = new CommandPlaceholders();

    private String name;
    private String cmd;
    private BiValMap<String, Boolean, Integer> args;

    public CustomCommand(String name, String cmd, BiValMap<String, Boolean, Integer> args) {
        this.name = name;
        this.cmd = cmd;
        this.args = args;
        placeholders.init(name, cmd, args);
    }

    public abstract boolean run(CommandSender sender, String[] args) throws EnluriaException;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        boolean return_ = false;
        try {
            return_ = run(commandSender, strings);
        } catch (EnluriaException ignored){}
        if(return_){
            sendBadCommandUsage(commandSender);
        }
        return false;
    }

    public void sendMessage(CommandSender player, String... messages) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());

        String prefix = config.getString("commands.prefix");
        String symbol = config.getString("commands.symbol");

        prefix = autoMessageBuilder(prefix);
        symbol = autoMessageBuilder(symbol);

        for (String s : messages) {
            s = autoMessageBuilder(s);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + ChatColor.RESET + symbol + " " + ChatColor.RESET + s));
        }
    }

    public void sendBadCommandUsage(CommandSender player) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());
        String badUsage = config.getString("commands.messages.bad_usage");

        for (String placeholder : placeholders.getPlaceholders()) {
            if (badUsage.contains(placeholder)) badUsage.replace(placeholder, placeholders.getValueFor(placeholder));
        }
        sendMessage(player, badUsage);
    }

    public void sendNoPerm(CommandSender player) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());
        String noPerm = config.getString("commands.messages.noperm");

        for (String placeholder : placeholders.getPlaceholders()) {
            if (noPerm.contains(placeholder)) noPerm.replace(placeholder, placeholders.getValueFor(placeholder));
        }
        sendMessage(player, noPerm);
    }

    private String autoMessageBuilder(String var) {
        System.out.println(var);
        StringBuilder sentence = new StringBuilder();
        int i = 0;
        for (String s : var.split(" ")) {
            i++;
            int length = 0;
            for (String placeholder : placeholders.getPlaceholders()) {
                length++;
                if (s.contains(placeholder)) {
                    s = s.replaceAll(placeholder, placeholders.getValueFor(placeholder));
                }
            }
            sentence.append(s);
            if(!(i == var.split(" ").length)){
                sentence.append(" ");
            }
        }
        return sentence.toString();
    }

    public CustomCommand setArgs(BiValMap<String, Boolean, Integer> args) {
        this.args = args;
        return this;
    }

    public CustomCommand setCmd(String cmd) {
        this.cmd = cmd;
        return this;
    }

    public CustomCommand setName(String name) {
        this.name = name;
        return this;
    }

    private void updatePlaceholders(String name, String cmd, BiValMap<String, Boolean, Integer> args){
        placeholders.init(name, cmd, args);
    }

    public void broadcast(String message) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());

        String prefix = config.getString("commands.prefix");
        String symbol = config.getString("commands.symbol");

        prefix = autoMessageBuilder(prefix);
        symbol = autoMessageBuilder(symbol);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + ChatColor.RESET + symbol + " " + ChatColor.RESET + message));

    }
}
