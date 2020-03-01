package fr.timkyl2203.enluria.utils.users;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.timkyl2203.enluria.Core;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;

public class EUserManager {

    private Map<OfflinePlayer, EUser> players;
    private List<EUser> users;
    private Map<EUser, Boolean> loadedUsers;

    public EUserManager(OfflinePlayer... players){
        this.players = Maps.newHashMap();
        loadedUsers = Maps.newHashMap();
        users = Lists.newArrayList();
        for (OfflinePlayer player : players) {
            if(this.players.containsKey(player)) continue;
            this.players.put(player, new EUser(player));
        }
    }
    public EUserManager(){
        players = Maps.newHashMap();
        loadedUsers = Maps.newHashMap();
        users = Lists.newArrayList();
    }

    public void addPlayer(OfflinePlayer player) {
        if(players.containsKey(player)) return;
        players.put(player, new EUser(player));
        loadedUsers.put(players.get(player), false);
        Core.getInstance().getLogger().info("Joueur " + player.getName() + " chargé dans le fichier " + player.getUniqueId() + ".yml");
    }
    public void removePlayer(OfflinePlayer player) {
        if(!players.containsKey(player)) return;

        loadedUsers.remove(players.get(player));
        players.remove(player);
        Core.getInstance().getLogger().info(String.format("User %s removed from user manager!", player.getName()));
    }

    public void addLoadedUser(EUser user){
        loadedUsers.put(user, true);
    }
    public void removeLoadedUser(EUser user){
        loadedUsers.put(user, false);
        Core.getInstance().getLogger().info(String.format("User %s unloaded !", user.getPlayer().getName()));
    }
    public boolean isUserLoaded(EUser user){
        return loadedUsers.getOrDefault(user, false);
    }

    public EUser getPlayer(OfflinePlayer player){
        return players.getOrDefault(player, null);
    }

    public boolean containsEUser(EUser eUser) {
        return players.containsValue(eUser);
    }

    public void addUser(EUser user){
        users.add(user);
        players.put(user.getPlayer(), user);
    }
    public void removeUser(EUser user){
        users.remove(user);
        removePlayer(user.getPlayer());
    }
    public boolean isUser(EUser user){
        return users.contains(user);
    }
}
