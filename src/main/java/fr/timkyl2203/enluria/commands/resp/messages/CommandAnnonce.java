package fr.timkyl2203.enluria.commands.resp.messages;

import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.NoPermissionException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAnnonce extends CustomCommand {
    public CommandAnnonce(String annonce1, String annonce, BiValMap<String, Boolean, Integer> args) {
        super(annonce1, annonce, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        if(!sender.hasPermission("responsable.use")) {
            throw new NoPermissionException(this, sender);
        }

        if(args.length < 1) {
            return true;
        }
        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(ChatColor.translateAlternateColorCodes('&', arg)).append(" ");
        }

        sendMessage(sender, "&aVotre message a bien été envoyé !");
        broadcast(message.toString());

        return false;
    }
}
