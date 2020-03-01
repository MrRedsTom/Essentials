package fr.timkyl2203.enluria.tablist.utils;

import com.google.common.collect.Maps;
import fr.timkyl2203.enluria.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

public class Placeholders {

    private HashMap<String, String> placeholders;

    public void init(Player player) {
        placeholders = Maps.newHashMap();

        placeholders.put("%display_name%", player.getDisplayName());
        placeholders.put("%online%", "" + Bukkit.getOnlinePlayers().size());
        placeholders.put("%name%", player.getName());
        placeholders.put("%total%", "" + Bukkit.getMaxPlayers());


        // placeholders.put("%{field}%", value);

        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
            placeholders.put("%ping%", "" + ping);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            Core.getInstance().getLogger().warning("Placeholder PING cannot be loaded for player " + player.getName());
        }
    }

    public String getValueFor(String key) {
        return placeholders.getOrDefault(key, ChatColor.RED + "ERROR" + ChatColor.RESET);
    }

    public HashMap<String, String> getReplacements() {
        return placeholders;
    }

    public Set<String> getPlaceholders() {
        return placeholders.keySet();
    }

}
