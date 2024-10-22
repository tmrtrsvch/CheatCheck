package ch.tmrtrsv.cheatcheck;

import ch.tmrtrsv.cheatcheck.utils.Utils;
import org.bukkit.plugin.java.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;

public class CheatCheck extends JavaPlugin implements CommandExecutor, Listener {

    private final Set<UUID> checkedPlayers;
    private FileConfiguration config;

    public CheatCheck() {
        this.checkedPlayers = new HashSet<>();
    }

    public void onEnable() {
        sendCredit();

        this.saveDefaultConfig();
        this.config = this.getConfig();
        this.getCommand("check").setExecutor(this);
        this.getCommand("uncheck").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        this.checkedPlayers.clear();
        sendCredit();
    }

    private void sendCredit() {
        getLogger().info(Utils.color(""));
        getLogger().info(Utils.color("&f &#FF2900C&#FC2600h&#FA2300e&#F72000a&#F41D00t&#F11A00C&#EF1700h&#EC1500e&#E91200c&#E70F00k &#E10900v&#DE06001&#DC0300.&#D900000"));
        getLogger().info(Utils.color("&f Автор: &#FB3908Т&#FC2B06и&#FD1D04м&#FE0E02у&#FF0000р"));
        getLogger().info(Utils.color("&f Телеграм: &#008DFF@&#0086FFt&#007FFFm&#0078FFr&#0071FFt&#006BFFr&#0064FFs&#005DFFv&#0056FFc&#004FFFh"));
        getLogger().info(Utils.color(""));
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("check") && sender instanceof Player) {
            final Player senderPlayer = (Player)sender;
            if (!senderPlayer.hasPermission("cheatcheck.use")) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.no_permission")));
                return true;
            }
            if (args.length != 1) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.check_usage")));
                return true;
            }
            final Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.player_not_found")));
                return true;
            }
            if (targetPlayer == senderPlayer) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.cannot_check_self")));
                return true;
            }
            this.checkedPlayers.add(targetPlayer.getUniqueId());
            senderPlayer.sendMessage(Utils.color(this.config.getString("messages.successful_check_sender").replace("{player}", targetPlayer.getName())));
            targetPlayer.sendMessage(Utils.color(this.config.getString("messages.successful_check_target")));
            final int displayTime = this.config.getInt("messages.center_screen_display_time");
            targetPlayer.sendTitle(Utils.color(this.config.getString("messages.center_screen_message")), Utils.color(this.config.getString("messages.center_screen_subtitle")), 10, 70, displayTime);
            return true;
        }
        else {
            if (!command.getName().equalsIgnoreCase("uncheck") || !(sender instanceof Player)) {
                return false;
            }
            final Player senderPlayer = (Player)sender;
            if (!senderPlayer.hasPermission("cheatcheck.use")) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.no_permission")));
                return true;
            }
            if (args.length != 1) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.uncheck_usage")));
                return true;
            }
            final Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.player_not_found")));
                return true;
            }
            if (!this.checkedPlayers.contains(targetPlayer.getUniqueId())) {
                senderPlayer.sendMessage(Utils.color(this.config.getString("messages.not_on_check")));
                return true;
            }
            this.checkedPlayers.remove(targetPlayer.getUniqueId());
            senderPlayer.sendMessage(Utils.color(this.config.getString("messages.successful_uncheck_sender").replace("{player}", targetPlayer.getName())));
            targetPlayer.sendMessage(Utils.color(this.config.getString("messages.successful_uncheck_target")));
            return true;
        }
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.checkedPlayers.contains(player.getUniqueId())) {
            event.setTo(event.getFrom());
        }
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (this.checkedPlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(Utils.color(this.config.getString("messages.cannot_use_commands")));
        }
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            if (this.checkedPlayers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}