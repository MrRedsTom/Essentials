package fr.timkyl2203.enluria;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.timkyl2203.enluria.tablist.utils.Packet;
import fr.timkyl2203.enluria.tablist.utils.Placeholders;
import fr.timkyl2203.enluria.tablist.utils.TabListRunnable;
import fr.timkyl2203.enluria.utils.commands.LoadCommands;
import fr.timkyl2203.enluria.utils.listeners.LoadListeners;
import fr.timkyl2203.enluria.utils.users.EFileData;
import fr.timkyl2203.enluria.utils.users.EUserManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Core extends JavaPlugin{

    /*
     * VARIABLES DECLARATION
     * private static
     */
    private static Core instance;
    private static File reports;
    private static File messages;
    /*
     * VARIABLES DECLARATION
     * private
     */
    private List<UUID> socialSpiedPlayers;
    private Map<CommandSender, CommandSender> lastMessagedPlayers;
    private EUserManager userManager;
    /*
     * VARIABLES DECLARATION
     * public static
     */
    /*
     * VARIABLES DECLARATION
     * public
     */
    public Permission permission = null;

    /*
     * SPIGOT DEFAULT METHODS
     */

    @Override
    public void onLoad() {}

    @Override
    public void onEnable() {

        /*
         * VARIABLE ASSIGNMENT
         */
        //OTHER
        instance = this;
        userManager = new EUserManager();

        //LISTS
        socialSpiedPlayers = Lists.newArrayList();

        //MAPS
        lastMessagedPlayers = Maps.newHashMap();

        //FILES
        messages = new File(getDataFolder() + File.separator + "messages.yml");
        reports = new File(getDataFolder() + File.separator + "reports.yml");

        /*
         * OTHER CODE
         */

        if(!getDataFolder().exists())
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdir();

        /*
         * CALLING OTHER METHODS
         */
        initMessages();
        initReports();
        setupPermission();
        setupTablist();
        setupUserData();
        LoadListeners.onLoad();
        LoadCommands.onLoad();
    }

    @Override
    public void onDisable() {}

    /*
     * OTHER METHODS
     */

    /*
     * SETUPS
     */

    private void setupPermission(){

        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null)
            this.permission = permissionProvider.getProvider();

    }
    private void setupTablist(){
        new TabListRunnable().runTaskTimerAsynchronously(this, 300L, 300L);
            Bukkit.getScheduler().runTaskTimer(this, () -> {

                for(Player p : Bukkit.getOnlinePlayers()){
                    setPlayerTablistWithAutoMessages(p);
                }

            }, 0, 20);
    }
    private void setupUserData(){

        EFileData.addData("userFolder", new File(getDataFolder() + "/users"));
        if(!EFileData.getData("userFolder").exists()){
            EFileData.getData("userFolder").mkdir();
        }
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
            userManager.addPlayer(p);
        }

    }

    /*
     * INITIALIZERS
     */

    private void initMessages() {

        if (!messages.exists()) {
            try {
                messages.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(messages);

            configuration.set("commands.prefix", "&b%name%");
            configuration.set("commands.symbol", "&8»");

            configuration.set("commands.messages.bad_usage", "&cMauvaise syntaxe ! %command% %args%");
            configuration.set("commands.messages.noperm", "&cVous n'avez pas les permissions nécessaires");

            configuration.set("commands.private.format", "&7[&b%first% &6%symbol% &b%second%&7] &7%message%");
            configuration.set("commands.private.symbol", "►");

            try {
                configuration.save(messages);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void initReports(){

        if(!reports.exists()){

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(reports);

            configuration.set("current_id", 0);

            try {
                configuration.save(reports);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * OTHERS
     */

    public void setPlayerTablistWithAutoMessages(Player p){

        Placeholders placeholders = new Placeholders();
        placeholders.init(p);

        List<String> header = getConfig().getStringList("edit.header");
        List<String> footer = getConfig().getStringList("edit.footer");

        int headerCurrent = 0, footerCurrent = 0;

        StringBuilder header_ = new StringBuilder();
        StringBuilder footer_ = new StringBuilder();

        generateString(placeholders, header, headerCurrent, header_);
        generateString(placeholders, footer, footerCurrent, footer_);

        Packet.setPlayerList(p, header_.toString(), footer_.toString());
    }

    private void generateString(Placeholders placeholders, List<String> strings, int i, StringBuilder strings_) {
        for (String s : strings) {
            i++;
            StringBuilder sentance = new StringBuilder();
            int length = 0;
            for(String word : s.split(" ")) {
                length++;
                for (String placeholder : placeholders.getPlaceholders()) {
                    if (word.contains(placeholder)) {
                        word = word.replaceAll(placeholder, placeholders.getValueFor(placeholder));
                    }
                }
                sentance.append(word).append(length == s.split(" ").length ? "" : " ");
            }
            strings_.append(ChatColor.translateAlternateColorCodes('&', sentance.toString())).append(i == strings.size() ? "" : "\n");
        }
    }

    /*
     * GETTERS AND SETTERS
     */

    public static File getReports() {
        return reports;
    }

    public static File getMessages() {
        return messages;
    }

    public static Core getInstance() {
        return instance;
    }

    public EUserManager getUserManager() {
        return userManager;
    }

    public List<UUID> getSocialSpiedPlayers() {
        return socialSpiedPlayers;
    }

    public Map<CommandSender, CommandSender> getLastMessagedPlayers() {
        return lastMessagedPlayers;
    }

    public Permission getPermission() {
        return permission;
    }
}