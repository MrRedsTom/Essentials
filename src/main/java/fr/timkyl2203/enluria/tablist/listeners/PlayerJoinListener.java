package fr.timkyl2203.enluria.tablist.listeners;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.tablist.utils.TeamsTagManager;
import fr.timkyl2203.enluria.utils.listeners.CustomListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener extends CustomListener<PlayerJoinEvent> {

    @Override
    public void run(PlayerJoinEvent e) {

        ConfigurationSection configurationSection = Core.getInstance().getConfig().getConfigurationSection("list_groups");

        if (configurationSection != null) {
            for (String lg : configurationSection.getKeys(false)) {

                if ((Core.getInstance()).permission.playerInGroup(e.getPlayer(), lg)) {
                    TeamsTagManager.setNameTag(e.getPlayer(), lg, Core.getInstance().getConfig().getString("list_groups." + lg + ".prefix"), Core.getInstance().getConfig().getInt("list_groups." + lg + ".priority"));
                }
            }
        }

    }
}
