package fr.timkyl2203.enluria.commands.modo;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.HaveToBeAPlayerException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.exceptions.NotValidPlayerException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import fr.timkyl2203.enluria.utils.users.EUser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGod extends CustomCommand {
    public CommandGod(String god, String god2, BiValMap<String, Boolean, Integer> args) {
        super(god, god2, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(!sender.hasPermission("moderateur.use")){
            throw new NoPermissionException(this, sender);
        }

        if(!(sender instanceof Player))
            throw new HaveToBeAPlayerException(sender, this);

        Player player = (Player) sender;

        if(args.length == 0){

            EUser user = Core.getInstance().getUserManager().getPlayer(player);
            if(!(boolean) user.get("god")){
                user.set("god", true);
                sendMessage(player, "&7Mode dieu &aactivé&7 !");
            }else{
                user.set("god", false);
                sendMessage(player, "&7Mode dieu &cdésactivé&7 !");
            }
            user.save();

        }else if(args.length == 1){

            Player target = Bukkit.getPlayerExact(args[0]);

            if(player == null){
                throw new NotValidPlayerException(sender, this);
            }

            EUser user = Core.getInstance().getUserManager().getPlayer(target);
            if(!(boolean) user.get("god")){
                user.set("god", true);
                sendMessage(player, "&7Mode dieu &aactivé&7 pour &b" + target.getDisplayName() + "&7 !");
                sendMessage(target, "&7Mode dieu &aactivé&7 par &b" + player.getDisplayName() + "&7 !");
            }else{
                user.set("god", false);
                sendMessage(player, "&7Mode dieu &cdésactivé&7 pour &b" + target.getDisplayName() + "&7 !");
                sendMessage(target, "&7Mode dieu &cdésactivé&7 par &b" + player.getDisplayName() + "&7 !");
            }
            user.save();

        }else{
            return true;
        }

        return false;
    }
}
