package me.klb.thehypixelpitsurvival.listeners;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.fixes.PersistentDataChecker;
import me.klb.thehypixelpitsurvival.itemchecker.ReplaceItemChecker;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class onJoinLeaveListener implements Listener {
    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent e){
        Player player = e.getPlayer();
        PersistentDataContainer data = player.getPersistentDataContainer();
        if(!data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE)){
            data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE, 500.00);
        }
        if(data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE)){
            data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE, 100.00);
        }

        e.setJoinMessage(ChatColor.GREEN + "Welcome, " + player.getDisplayName() + ".");
        createScoreBoard(e);
        ReplaceItemChecker.runItemChecker(player);
        PersistentDataChecker.checkPersistentData(player);
        updateScoreboard(player); // Should always be the last to run
    }

    public void createScoreBoard(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.setDisplayName(ChatColor.GRAY + player.getDisplayName());
        PersistentDataContainer data = player.getPersistentDataContainer();
        Double gold = data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE);
        Double xp = data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE);
        BPlayerBoard board = Netherboard.instance().createBoard(player, "" + TheHypixelPitSurvival.getPlugin().getConfig().getString("server-name"));

        board.setAll(
                ChatColor.GRAY + TheHypixelPitSurvival.getPlugin().returnCurrentDate(),
                "                          ",
                "Prestige: " + ChatColor.YELLOW + "XXXV",
                "Level: " + ChatColor.WHITE + "[" + ChatColor.AQUA + "120" + ChatColor.WHITE + "]",
                "Needed XP: " + ChatColor.AQUA + xp,
                "",
                "Gold: " + ChatColor.GOLD + String.format("%.2f", gold) + "g"
        );
    }

    public void removeScoreboard(PlayerQuitEvent e){
        Player player = e.getPlayer();
        BPlayerBoard board = Netherboard.instance().getBoard(player);

        if(board != null){
            board.delete();
        }
    }

    public void updateScoreboard(Player player){
        BPlayerBoard board = Netherboard.instance().getBoard(player);

        new BukkitRunnable(){
            public void run(){
                if (board != null && player != null){
                    PersistentDataContainer data = player.getPersistentDataContainer();
                    Double gold = data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE);
                    Double xp = data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE);
                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    DecimalFormat normalFormat = new DecimalFormat("#,###");

                    String xpTitleDisplay;
                    String xpDisplay;
                    if (xp <= 0) {
                        xpDisplay = "MAXED!";
                        xpTitleDisplay = "XP: ";
                    } else {
                        xpDisplay = normalFormat.format(xp.intValue());
                        xpTitleDisplay = "Needed XP: ";
                    }

                    board.setAll(
                            ChatColor.GRAY + TheHypixelPitSurvival.getPlugin().returnCurrentDate(),
                            "                          ",
                            "Prestige: " + ChatColor.YELLOW + "XXXV",
                            "Level: " + ChatColor.WHITE + "[" + ChatColor.AQUA + "120" + ChatColor.WHITE + "]",
                            xpTitleDisplay + ChatColor.AQUA + xpDisplay,
                            "",
                            "Gold: " + ChatColor.GOLD + formatter.format(gold) + "g"
                    );
                }
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 0, 8);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();

        e.setQuitMessage(ChatColor.RED + "Goodbye, " + player.getDisplayName() + ".");
        removeScoreboard(e);
    }
}
