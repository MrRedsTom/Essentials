package fr.timkyl2203.enluria.exceptions;

import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class EnluriaException extends Exception {
    public EnluriaException(CustomCommand command, CommandSender sender, String s) {
        if(!s.isEmpty()) command.sendMessage(sender, "&c" + s);
    }
}
