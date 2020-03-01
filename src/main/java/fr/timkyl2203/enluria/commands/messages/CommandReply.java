package fr.timkyl2203.enluria.commands.messages;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.command.CommandSender;

public class CommandReply extends CustomCommand {
    public CommandReply(String message, String r, BiValMap<String, Boolean, Integer> args) {
        super(message, r, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException, EnluriaException {

        if(args.length < 1)
            return true;

        CommandSender target = Core.getInstance().getLastMessagedPlayers().get(sender);

        if(target == null){
            sendMessage(sender, "&cVous n'avez pas reçu/envoyé de message dernièrement ou la personne avec qui vous discutiez s'est déconnectée");
            return false;
        }

        StringBuilder message = new StringBuilder();
        for(int i = 0; i < args.length; i++){
            message.append(args[i]);
            if(!(i == args.length)) message.append(" ");
        }

        CommandPrivateMessage.sendMessage(sender, target, message.toString());

        return false;
    }
}
