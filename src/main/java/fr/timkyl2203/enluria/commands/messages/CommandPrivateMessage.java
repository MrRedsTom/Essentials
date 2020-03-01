package fr.timkyl2203.enluria.commands.messages;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.exceptions.messages.MissingMessageException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandPrivateMessage extends CustomCommand {

    public CommandPrivateMessage(String name, String commandName, BiValMap<String, Boolean, Integer> args) {
        super(name, commandName, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(args.length < 1) return true;
        if(args.length == 1) throw new MissingMessageException(sender, this);

        else{

            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null){
                throw new NotValidPlayerException(sender, this);
            }
            StringBuilder message = new StringBuilder();
            for(int i = 1; i < args.length; i++){
                message.append(args[i]);
                if(!(i == args.length)) message.append(" ");
            }

            sendMessage(sender, target, message.toString());

            Core.getInstance().getLastMessagedPlayers().put(sender, target);
            Core.getInstance().getLastMessagedPlayers().put(target, sender);

        }
        return false;
    }

    public static void sendMessage(CommandSender from, CommandSender to, String message){

        YamlConfiguration messages = YamlConfiguration.loadConfiguration(Core.getMessages());
        String format = messages.getString("commands.private.format");
        String symbol = messages.getString("commands.private.symbol");
        format = format.replaceAll("%symbol%", "%s").replaceAll("%first%", "%s").replaceAll("%second%", "%s").replaceAll("%message%", "%s").replaceAll("%s", "%s&r");

        for(UUID uuid : Core.getInstance().getSocialSpiedPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(format, (from instanceof Player ? ((Player)from).getDisplayName() : from.getName()), symbol,  (to instanceof Player ? ((Player)to).getDisplayName() : to.getName()), message)));
        }

        from.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(format, "me", symbol, (to instanceof Player ? ((Player)to).getDisplayName() : to.getName()), message)));
        to.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(format, (from instanceof Player ? ((Player)from).getDisplayName() : from.getName()), symbol, "me", message)));


    }

}
