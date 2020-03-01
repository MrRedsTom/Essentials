package fr.timkyl2203.enluria.utils.listeners;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.listeners.player.PlayerDamageListener;
import fr.timkyl2203.enluria.listeners.player.PlayerQuitListener;
import fr.timkyl2203.enluria.tablist.listeners.ChatListener;
import fr.timkyl2203.enluria.tablist.listeners.PlayerJoinListener;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoadListeners
{
    public static void onLoad() {
        registerListener(PlayerJoinEvent.class, new PlayerJoinListener());
        registerListener(AsyncPlayerChatEvent.class, new ChatListener());
        registerListener(PlayerQuitEvent.class, new PlayerQuitListener());
        registerListener(EntityDamageEvent.class, new PlayerDamageListener());
        registerListener(PlayerJoinEvent.class, new fr.timkyl2203.enluria.listeners.player.PlayerJoinListener());
    }

    private static<T> void registerListener(Class<? extends Event> eventType, CustomListener executor){
        Core core = Core.getInstance();
        core.getServer().getPluginManager().registerEvent(eventType, executor, EventPriority.NORMAL, executor, core);
    }

}
