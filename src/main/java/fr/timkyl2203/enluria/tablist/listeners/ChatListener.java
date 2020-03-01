package fr.timkyl2203.enluria.tablist.listeners;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.tablist.utils.TeamsTagManager;
import fr.timkyl2203.enluria.utils.listeners.CustomListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;
import java.util.Objects;

public class ChatListener extends CustomListener<AsyncPlayerChatEvent> {
    @Override
    public void run(AsyncPlayerChatEvent event) {

        ConfigurationSection configurationSection = Core.getInstance().getConfig().getConfigurationSection("list_groups");


        if (configurationSection != null) {
            for (String lg : configurationSection.getKeys(false)) {

                Iterator iterator = Bukkit.getOnlinePlayers().iterator();
                if (iterator.hasNext()) {
                    Player e = (Player)iterator.next();

                    if ((Core.getInstance()).permission.playerInGroup(Objects.requireNonNull(e.getPlayer()), lg))
                        TeamsTagManager.setNameTag(e.getPlayer(), lg, Core.getInstance().getConfig().getString("list_groups." + lg + ".prefix"), Core.getInstance().getConfig().getInt("list_groups." + lg + ".priority"));  }

            }
        }
    }
}
