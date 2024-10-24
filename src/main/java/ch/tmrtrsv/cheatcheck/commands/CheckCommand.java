package ch.tmrtrsv.cheatcheck.commands;

import ch.tmrtrsv.cheatcheck.CheatCheck;
import ch.tmrtrsv.cheatcheck.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand implements CommandExecutor {

    private final CheatCheck plugin;

    public CheckCommand(CheatCheck plugin) {
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
            senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.check_usage")));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.player_not_found")));
            return true;
        }
        if (targetPlayer == senderPlayer) {
            senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.cannot_check_self")));
            return true;
        }

        plugin.getEventListener().getCheckedPlayers().add(targetPlayer.getUniqueId());

        senderPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.successful_check_sender").replace("{player}", targetPlayer.getName())));
        targetPlayer.sendMessage(Utils.color(plugin.getConfig().getString("messages.successful_check_target")));

        int displayTime = plugin.getConfig().getInt("messages.center_screen_display_time");
        targetPlayer.sendTitle(Utils.color(plugin.getConfig().getString("messages.center_screen_message")),
                Utils.color(plugin.getConfig().getString("messages.center_screen_subtitle")), 10, 70, displayTime);
        return true;
    }
}