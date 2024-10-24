package ch.tmrtrsv.cheatcheck.tabcompleters;

import org.bukkit.command.TabCompleter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("cheatcheck") && args.length == 1) {
            if (sender.hasPermission("cheatcheck.reload")) {
                completions.add("reload");
            }
        }

        return completions;
    }
}