/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors: 
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.commands.other;

import fr.timkyl2203.enluria.exceptions.EnluriaException;
import fr.timkyl2203.enluria.utils.commands.CustomCommand;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class CommandGc extends CustomCommand {
    public CommandGc(String s, String gc, BiValMap<String, Boolean, Integer> args) {
        super(s, gc, args);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws EnluriaException {

        double tps = MinecraftServer.getServer().recentTps[0];
        ChatColor color;
        if (tps >= 18.0)
        {
            color = ChatColor.GREEN;
        }
        else if (tps >= 15.0)
        {
            color = ChatColor.YELLOW;
        }
        else
        {
            color = ChatColor.RED;
        }

        DecimalFormat format = new DecimalFormat("#.##");

        Date date = new Date(ManagementFactory.getRuntimeMXBean().getUptime());
        DateFormat dateFormat = new SimpleDateFormat("HH;mm/ss:");
        String formated = dateFormat.format(date).replace(";", " heure(s) ").replace("/", " minute(s) et ").replace(":", " seconde(s)");

        sendMessage(sender,
                "&7Lancé depuis : &b" + formated,
                "&7TPS : &b" + "" + color + format.format(tps),
                "&7Mémoire max : &b" + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "Mo",
                "&7Mémore totale :&b " + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "Mo",
                "&7Mémoire libre : &b" + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + "Mo");

        List<World> worlds = Bukkit.getServer().getWorlds();
        for (World w : worlds)
        {
            String worldType = "World";
            switch (w.getEnvironment())
            {
                case NETHER:
                    worldType = "Nether";
                    break;
                case THE_END:
                    worldType = "The End";
                    break;
            }

            int tileEntities = 0;

            try
            {
                for (Chunk chunk : w.getLoadedChunks())
                {
                    tileEntities += chunk.getTileEntities().length;
                }
            }
            catch (java.lang.ClassCastException ex)
            {
                Bukkit.getLogger().log(Level.SEVERE, "Corrupted chunk data on world " + w, ex);
            }

            sendMessage(sender, "&7" + w.getName() + " : &b" +
                    "\n     &7Type : &b" + worldType +
                    "&7 | &7Chuncks chargés : &b" + w.getLoadedChunks().length +
                    "&7 | &7Entités : &b" +  w.getEntities().size() +
                    "&7 | &7Tiles Entités : &b" +  tileEntities);
        }

        return false;
    }
}
