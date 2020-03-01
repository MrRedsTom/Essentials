package fr.timkyl2203.enluria.exceptions;

import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class NotValidPlayerException extends EnluriaException {
    public NotValidPlayerException(CommandSender sender, CustomCommand command) {
        super(command, sender, "Le joueur que vous avez mentionné est invalide !");
    }
}
