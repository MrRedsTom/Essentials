package fr.timkyl2203.enluria.listeners.player;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.utils.listeners.CustomListener;
import fr.timkyl2203.enluria.utils.users.EUser;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener extends CustomListener<EntityDamageEvent> {
    @Override
    public void run(EntityDamageEvent event) {

        if(!(event.getEntity() instanceof Player)) return;

        EUser user = Core.getInstance().getUserManager().getPlayer((Player) event.getEntity());

        if((boolean) user.get("god")) {
            event.setCancelled(true);
        }
    }
}
