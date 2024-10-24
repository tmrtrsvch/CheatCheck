package ch.tmrtrsv.cheatcheck.commands;

import ch.tmrtrsv.cheatcheck.CheatCheck;
import ch.tmrtrsv.cheatcheck.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private final CheatCheck plugin;

    public ReloadCommand(CheatCheck plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("cheatcheck.reload")) {
            sender.sendMessage(Utils.color(plugin.getConfig().getString("messages.no_permission")));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(Utils.color(plugin.getConfig().getString("messages.config_reloaded")));
        } else {
            sender.sendMessage(Utils.color(plugin.getConfig().getString("messages.reload_usage")));
        }

        return true;
    }
}