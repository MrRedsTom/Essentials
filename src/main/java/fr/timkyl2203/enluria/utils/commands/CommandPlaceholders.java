package fr.timkyl2203.enluria.utils.commands;

import com.google.common.collect.Maps;
import fr.timkyl2203.enluria.utils.other.BiValMap;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandPlaceholders {

    private HashMap<String, String> placeholders;

    public void init(String commandName, String cmd, BiValMap<String, Boolean, Integer> args) {

        placeholders = Maps.newHashMap();

        placeholders.put("%name%", commandName);

        Map<Integer, StringBuilder> builders = new HashMap<>();

        int lenght = args.keySet().size();
        for (String s : args.keySet()) {
            lenght++;
            StringBuilder this_ = new StringBuilder();

            this_.append(args.get(s).getFirstVal() ? "<" : "[").append(s).append(args.get(s).getFirstVal() ? ">" : "]");
            if (!(lenght == args.keySet().size())) {
                this_.append(" ");

                builders.put(args.get(s).getSecondVal(), this_);
            /*
            arguments.append((args.get(s) ? "<" : "[") + s + (args.get(s) ? ">" : "]"));
            if(!(lenght == args.keySet().size())){
                arguments.append(" ");
            }*/
            }
        }

        StringBuilder arguments = new StringBuilder();
        lenght = 0;
        for(StringBuilder sb : builders.values()){
            lenght++;
            arguments.append(sb.toString()).append(lenght == builders.keySet().size() ? "" : " ");
        }

        placeholders.put("%args%", arguments.toString());

        placeholders.put("%command%", "/" + cmd.toLowerCase());

    }

    public String getValueFor(String key) {
        return placeholders.getOrDefault(key, ChatColor.RED + "ERROR" + ChatColor.RESET);
    }

    public HashMap<String, String> getReplacements() {
        return placeholders;
    }

    public Set<String> getPlaceholders() {
        return placeholders.keySet();
    }

}
