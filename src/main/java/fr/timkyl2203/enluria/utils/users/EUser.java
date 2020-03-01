package fr.timkyl2203.enluria.utils.users;

import fr.timkyl2203.enluria.Core;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EUser {

    private OfflinePlayer player;

    private File file;

    private YamlConfiguration fileConfig;

    public EUser(OfflinePlayer player){

        this.player = player;

        file = new File(EFileData.getData("userFolder") + "/" + player.getUniqueId().toString() + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!Core.getInstance().getUserManager().isUserLoaded(this)) {
            Core.getInstance().getLogger().fine(String.format("User %s loaded !", player.getName()));
            initPlayer();
        }

    }

    private void initPlayer(){

        fileConfig = YamlConfiguration.loadConfiguration(file);

        set("name", player.getName());

        if(get("god") == null || !(boolean) get("god")){
            fileConfig.set("god", false);
        }
        if(get("vanish") == null || !(boolean) get("vanish")){
            fileConfig.set("vanish", false);
        }

        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Core.getInstance().saveConfig();

    }

    public void set(String path, Object value) {
        fileConfig.set(path, value);
    }

    public void delete(String path){
        fileConfig.set(path, null);
    }

    public Object get(String path){
        return fileConfig.get(path);
    }

    public YamlConfiguration getYamlConfig() {
        return fileConfig;
    }

    public void save(){
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public boolean isOnline(){
        return player.isOnline();
    }

    public void sendCommandFeedback(String message){
        if(isOnline()){
            player.getPlayer().sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "Successful : " + ChatColor.RESET + ChatColor.GOLD + message);
        }
    }

    public void destroy(){
        Core.getInstance().getUserManager().removeUser(this);
    }
}
