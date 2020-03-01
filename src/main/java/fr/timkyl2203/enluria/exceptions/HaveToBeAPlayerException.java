package fr.timkyl2203.enluria.exceptions;

import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class HaveToBeAPlayerException extends EnluriaException {
    public HaveToBeAPlayerException(CommandSender sender, CustomCommand command) {
        super(command, sender, "L'exécuteur de la commande doit être un joueur !");
    }
}
