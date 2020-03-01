package fr.timkyl2203.enluria.tablist.utils;

import fr.timkyl2203.enluria.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

public class TabListRunnable
        extends BukkitRunnable
{
    public void run() {
        ConfigurationSection configurationSection = Core.getInstance().getConfig().getConfigurationSection("list_groups");

        for (String lg : configurationSection.getKeys(false)) {

            Iterator iterator = Bukkit.getOnlinePlayers().iterator(); if (iterator.hasNext()) { Player e = (Player)iterator.next();

                if ((Core.getInstance()).permission.playerInGroup(e.getPlayer(), lg))
                    TeamsTagManager.setNameTag(e.getPlayer(), lg, Core.getInstance().getConfig().getString("list_groups." + lg + ".prefix"), Core.getInstance().getConfig().getInt("list_groups." + lg + ".priority"));  }

        }
    }
}
