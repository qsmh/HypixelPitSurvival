package me.klb.thehypixelpitsurvival.other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TitleBuilder {
    public static void diedTitle(final @NonNull Player player) {
        player.sendTitle(ChatColor.RED + "YOU DIED", "", 1, 50, 10);
    }
}