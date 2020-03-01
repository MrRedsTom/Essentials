/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.listeners.player;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.utils.listeners.CustomListener;
import fr.timkyl2203.enluria.utils.users.EUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends CustomListener<PlayerJoinEvent> {
    @Override
    public void run(PlayerJoinEvent event) {

        Player p = event.getPlayer();
        Core.getInstance().getUserManager().removePlayer(p);
        Core.getInstance().getUserManager().addPlayer(p);

        for(Player target : Bukkit.getOnlinePlayers()){

            EUser user = Core.getInstance().getUserManager().getPlayer(target);

            if(user != null && user.get("vanish") != null) {
                if ((boolean) user.get("vanish") && !(p.hasPermission("moderateur.use"))) p.hidePlayer(target);
                else if (!((boolean) user.get("vanish"))) p.showPlayer(target);
            }
        }

        EUser user = Core.getInstance().getUserManager().getPlayer(p);

        for (Player target : Bukkit.getOnlinePlayers()) {

            if(user != null && user.get("vanish") != null) {
                if ((boolean) user.get("vanish") && !(target.hasPermission("moderateur.use"))) target.hidePlayer(p);
            }
        }

    }
}
