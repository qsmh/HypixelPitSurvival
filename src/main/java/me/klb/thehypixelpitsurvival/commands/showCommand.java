package me.klb.thehypixelpitsurvival.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

import static org.bukkit.Bukkit.spigot;

public class showCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.getType() == Material.AIR){
            return true;
        }

        ItemMeta heldItemMeta = heldItem.getItemMeta();

        if (heldItemMeta == null){
            return true;
        }

        String displayName = heldItem.getItemMeta().getDisplayName();

        if (displayName.equals("")){
            return true;
        }

        List<String> lore = heldItem.getItemMeta().getLore();

        ComponentBuilder tooltipBuilder = new ComponentBuilder("");
        tooltipBuilder.append(displayName);

        if (lore != null && !lore.isEmpty()) {
            tooltipBuilder.append("\n").append(String.join("\n", lore));
        }

        TextComponent message = new TextComponent(ChatColor.GREEN.toString() + ChatColor.BOLD + "SHOWOFF! " + ChatColor.RESET + player.getDisplayName() + ChatColor.GRAY + " has: " + ChatColor.RESET + heldItemMeta.getDisplayName());
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltipBuilder.create()));
        spigot().broadcast(message);
        return true;
    }
}
