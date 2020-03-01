package fr.timkyl2203.enluria.commands.resp;

import com.google.common.collect.Lists;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandGamemode extends CustomCommand implements TabCompleter {
    public CommandGamemode(String gameMode, String gamemode, BiValMap<String, Boolean, Integer> args) {
        super(gameMode, gamemode, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(!sender.hasPermission("responsable.use")){
            throw new NoPermissionException(this, sender);
        }

        if(args.length < 1 || args.length > 2){
            return true;
        }

        if(!(sender instanceof Player)){
            throw new HaveToBeAPlayerException(sender, this);
        }

        Player p = (Player) sender;

        if(args.length == 1){

            switch (args[0]){

                case "1":
                case "c":
                case "creative":
                    p.setGameMode(GameMode.CREATIVE);
                    sendMessage(p, "&7Vous avez modifié votre mode de jeu en &bcréatif &7!");
                    break;
                case "0":
                case "s":
                case "survival":
                    p.setGameMode(GameMode.SURVIVAL);
                    sendMessage(p, "&7Vous avez modifié votre mode de jeu en &bsurvie &7!");
                    break;
                case "2":
                case "a":
                case "adventure":
                    p.setGameMode(GameMode.ADVENTURE);
                    sendMessage(p, "&7Vous avez modifié votre mode de jeu en &badventure &7!");
                    break;
                case "3":
                case "sp":
                case "spectator":
                    p.setGameMode(GameMode.SPECTATOR);
                    sendMessage(p, "&7Vous avez modifié votre mode de jeu en &bspectateur &7!");
                    break;
                default:
                    sendMessage(p, "&cLe mode jeu que vous avez précisé n'existe pas !");
                    return false;
            }

        }else{

            Player target = Bukkit.getPlayerExact(args[1]);

            if(target == null){
                throw new NotValidPlayerException(sender, this);
            }

            switch (args[0]){

                case "1":
                case "c":
                case "creative":
                    target.setGameMode(GameMode.CREATIVE);
                    if(target == p){
                        sendMessage(p, "&7Vous avez modifié votre mode de jeu en &bcréatif &7!");
                        break;
                    }
                    sendMessage(p, "&7Vous avez modifié le mode de jeu de &a" + target.getDisplayName() + "&7 en &bcréatif &7!");
                    sendMessage(target, "&7Votre mode de jeu a été modifié en &bcréatif &7!");
                    break;
                case "0":
                case "s":
                case "survival":
                    target.setGameMode(GameMode.SURVIVAL);
                    if(target == p){
                        sendMessage(p, "&7Vous avez modifié votre mode de jeu en &bsurvie &7!");
                        break;
                    }
                    sendMessage(p, "&7Vous avez modifié le mode de jeu de &a" + target.getDisplayName() + "&7 en &bsurvie &7!");
                    sendMessage(target, "&7Votre mode de jeu a été modifié en &bsurvie &7!");
                    break;
                case "2":
                case "a":
                case "adventure":
                    if(target == p){
                        sendMessage(p, "&7Vous avez modifié votre mode de jeu en &baventure &7!");
                        break;
                    }
                    target.setGameMode(GameMode.ADVENTURE);
                    sendMessage(p, "&7Vous avez modifié le mode de jeu de &a" + target.getDisplayName() + "&7 en &baventure &7!");
                    sendMessage(target, "&7Votre mode de jeu a été modifié en &baventure &7!");
                    break;
                case "3":
                case "sp":
                case "spectator":
                    if(target == p){
                        sendMessage(p, "&7Vous avez modifié votre mode de jeu en &bspectateur &7!");
                        break;
                    }
                    target.setGameMode(GameMode.SPECTATOR);
                    sendMessage(p, "&7Vous avez modifié le mode de jeu de &a" + target.getDisplayName() + "&7 en &bspectateur &7!");
                    sendMessage(target, "&7Votre mode de jeu a été modifié en &bspectateur &7!");
                    break;
                default:
                    sendMessage(p, "&cLe mode jeu que vous avez précisé n'existe pas !");
                    return false;

            }

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> gamemodes = Lists.newArrayList("creative", "survival", "adventure", "spectator");
        List<String> final_ = Lists.newArrayList();
        for (String gamemode : gamemodes) {
            if(gamemode.startsWith(args[0])){
                final_.add(gamemode);
            }
        }
        if(args.length == 2){
            final_.clear();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                final_.add(onlinePlayer.getName());
            }
        }
        return final_;
    }
}
