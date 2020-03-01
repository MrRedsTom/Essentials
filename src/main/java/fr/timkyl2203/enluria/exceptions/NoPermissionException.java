/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.exceptions;

import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class NoPermissionException extends EnluriaException {

    public NoPermissionException(CustomCommand command, CommandSender sender) {
        super(command, sender, "");
        command.sendNoPerm(sender);
    }
}
