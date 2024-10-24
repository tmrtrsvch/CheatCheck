package ch.tmrtrsv.cheatcheck.commands;

import ch.tmrtrsv.cheatcheck.CheatCheck;
import ch.tmrtrsv.cheatcheck.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UncheckCommand implements CommandExecutor {

    private final CheatCheck plugin;

    public UncheckCommand(CheatCheck plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player senderPlayer = (Player) sender;
        if (!senderPlayer.hasPermission("cheatcheck.use")) {
            senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.no_permission")));
            return true;
        }
        if (args.length != 1) {
            senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.uncheck_usage")));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.player_not_found")));
            return true;
        }
        if (!plugin.getEventListener().getCheckedPlayers().contains(targetPlayer.getUniqueId())) {
            senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.not_on_check")));
            return true;
        }

        plugin.getEventListener().getCheckedPlayers().remove(targetPlayer.getUniqueId());

        senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.successful_uncheck_sender").replace("{player}", targetPlayer.getName())));
        targetPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.successful_uncheck_target")));
        return true;
    }
}