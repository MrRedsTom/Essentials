package fr.timkyl2203.enluria.listeners.player;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.utils.listeners.CustomListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends CustomListener<PlayerQuitEvent> {
    @Override
    public void run(PlayerQuitEvent event) {

        Core.getInstance().getLastMessagedPlayers().put(Core.getInstance().getLastMessagedPlayers().get(event.getPlayer()), null);
        Core.getInstance().getLastMessagedPlayers().put(event.getPlayer(), null);

    }
}
