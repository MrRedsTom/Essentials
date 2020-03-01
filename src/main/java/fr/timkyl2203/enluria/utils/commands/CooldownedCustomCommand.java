package fr.timkyl2203.enluria.utils.commands;

import com.google.common.collect.Maps;
import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.exceptions.cooldown.CooldownNotPassedException;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public abstract class CooldownedCustomCommand extends CustomCommand{

    private Map<UUID, Long> cooldowns = Maps.newHashMap();
    private long cooldownInSeconds;

    public CooldownedCustomCommand(String name, String cmd, BiValMap<String, Boolean, Integer> args, long cooldownInSeconds) {
        super(name, cmd, args);
        this.cooldownInSeconds = cooldownInSeconds + 1;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        boolean b = false;

        if(!(commandSender instanceof Player)) return false;

        if(commandSender.hasPermission("moderateur.use")) {
            try {
                run(commandSender, strings);
            } catch (EnluriaException ignored) {}
            return false;
        }

        Player player = (Player) commandSender;
        if(cooldowns.get(player.getUniqueId()) == null) {
            setCooldown(player);
            try {
                run(commandSender, strings);
            } catch (EnluriaException ignored) {}
            return false;
        }

        if(ZonedDateTime.now().toInstant().getEpochSecond() < cooldowns.get(player.getUniqueId())){
            try {
                throw new CooldownNotPassedException(this, commandSender, getCooldown(player));
            } catch (CooldownNotPassedException ignored) {}
        }
        else {
            setCooldown(player);
            try {
                b = run(commandSender, strings);
            } catch (EnluriaException ignored) {}
        }

        if(b){
            sendBadCommandUsage(commandSender);
        }

        return false;
    }

    protected void setCooldown(Player player){
        long zoneDateTime = ZonedDateTime.now().toInstant().getEpochSecond();
        cooldowns.put(player.getUniqueId(), (zoneDateTime + cooldownInSeconds));
    }
    protected void resetCooldown(Player player){
        cooldowns.put(player.getUniqueId(), null);
    }
    protected long getCooldown(Player player){
        return cooldowns.getOrDefault(player.getUniqueId(), 0L) - ZonedDateTime.now().toInstant().getEpochSecond() ;
    }
}
