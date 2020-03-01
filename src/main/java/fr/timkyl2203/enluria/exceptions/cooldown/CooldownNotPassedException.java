package fr.timkyl2203.enluria.exceptions.cooldown;

import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class CooldownNotPassedException extends EnluriaException {
    public CooldownNotPassedException(CustomCommand command, CommandSender sender, long cooldown) {
        super(command, sender, "Veuillez patienter encore &b" + cooldown + " secondes &c pour pouvoir de nouveau exécuter cette commande");
    }
}
