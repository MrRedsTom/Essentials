package fr.timkyl2203.enluria.exceptions.messages;

import fr.timkyl2203.enluria.commands.messages.CommandPrivateMessage;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import org.bukkit.command.CommandSender;

public class MissingMessageException extends EnluriaException {
    public MissingMessageException(CommandSender sender, CommandPrivateMessage clazz) {
        super(clazz, sender, "Veuillez préciser un message !");
    }
}
