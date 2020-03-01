/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors:
 *   RedsTom
 *   Timkyl2203
 */

package fr.timkyl2203.enluria.utils.commands;

import fr.timkyl2203.enluria.Core;
import fr.timkyl2203.enluria.commands.home.CommandDelhome;
import fr.timkyl2203.enluria.commands.home.CommandHome;
import fr.timkyl2203.enluria.commands.home.CommandHomes;
import fr.timkyl2203.enluria.commands.home.CommandSethome;
import fr.timkyl2203.enluria.commands.messages.CommandPrivateMessage;
import fr.timkyl2203.enluria.commands.messages.CommandReply;
import fr.timkyl2203.enluria.commands.modo.CommandGod;
import fr.timkyl2203.enluria.commands.modo.other.CommandClear;
import fr.timkyl2203.enluria.commands.modo.other.CommandVanish;
import fr.timkyl2203.enluria.commands.modo.tp.CommandS;
import fr.timkyl2203.enluria.commands.modo.tp.CommandTp;
import fr.timkyl2203.enluria.commands.other.CommandGc;
import fr.timkyl2203.enluria.commands.other.tp.CommandSpawn;
import fr.timkyl2203.enluria.commands.resp.CommandGamemode;
import fr.timkyl2203.enluria.commands.resp.messages.CommandAnnonce;
import fr.timkyl2203.enluria.commands.sanctions.CommandClearReport;
import fr.timkyl2203.enluria.commands.sanctions.CommandReport;
import fr.timkyl2203.enluria.commands.sanctions.CommandReports;
import fr.timkyl2203.enluria.commands.vip.healing.CommandFeed;
import fr.timkyl2203.enluria.utils.other.BiValHashMap;
import fr.timkyl2203.enluria.utils.other.BiValMap;

public class LoadCommands {

    public static void onLoad(){
        BiValMap<String, Boolean, Integer> args;
        args = new BiValHashMap<>();
        args.put("joueur", true, 1);
        args.put("message", true, 2);
        registerCommand("msg", new CommandPrivateMessage("Message", "msg", args));
        args.clear();

        args.put("message", true, 1);
        registerCommand("r", new CommandReply("Message", "r", args));
        args.clear();

        args.put("joueur", false, 1);
        registerCommand("god", new CommandGod("God", "god", args));
        args.clear();

        args.put("gamemode", true, 1);
        args.put("joueur", false, 2);
        registerCommand("gamemode", new CommandGamemode("GameMode", "gamemode", args));
        args.clear();

        args.put("message", true, 1);
        registerCommand("annonce", new CommandAnnonce("Annonce", "annonce", args));
        args.clear();

        args.put("joueur", true, 1);
        args.put("joueur2", false, 2);
        registerCommand("tp", new CommandTp("Modération", "tp", args));
        args.clear();

        args.put("joueur", true, 1);
        args.put("raison", true, 2);
        registerCommand("report", new CommandReport("Modération", "report", args));
        args.clear();

        args.put("joueur", true, 1);
        registerCommand("s", new CommandS("Modération", "s", args));
        args.clear();

        args.put("joueur", false, 1);
        registerCommand("clear", new CommandClear("Modération", "clear", args));
        args.clear();

        args.put("nom", true, 1);
        registerCommand("sethome", new CommandSethome("Homes", "sethome", args));

        registerCommand("home", new CommandHome("Homes", "home", args));

        registerCommand("delhome", new CommandDelhome("Homes", "delhome", args));
        args.clear();

        args.put("joueur", false, 1);
        registerCommand("homes", new CommandHomes("Homes", "homes", args));
        args.clear();

        registerCommand("gc", new CommandGc("", "gc", args));

        registerCommand("spawn", new CommandSpawn("Spawn", "spawn", args));

        registerCommand("vanish", new CommandVanish( "Vanish", "vanish", args));

        args.put("joueur", false, 1);
        registerCommand("feed", new CommandFeed("Heal", "feed", args));
        args.clear();

        args.put("joueur", true, 1);
        registerCommand("reports", new CommandReports("Modération", "reports", args));
        args.clear();

        args.put("#id | joueur", true, 1);
        registerCommand("clear-report", new CommandClearReport("Modération", "clear-report", args));
        args.clear();

    }

    private static void registerCommand(String commandName, CustomCommand executor){
        Core.getInstance().getCommand(commandName).setExecutor(executor);
    }

}
