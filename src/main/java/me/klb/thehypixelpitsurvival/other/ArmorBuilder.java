package me.klb.thehypixelpitsurvival.other;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArmorBuilder {
    public static ItemStack getHelmet(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        return playerInventory.getHelmet();
    }

    public static ItemStack getChestplate(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        return playerInventory.getChestplate();
    }

    public static ItemStack getLeggings(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        return playerInventory.getLeggings();
    }

    public static ItemStack getBoots(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        return playerInventory.getBoots();
    }

    public static int hasArmorMaterialDiamond(Player player){
        int count = 0;

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (ItemStack itemStack : armorContents) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {

                Material itemType = itemStack.getType();
                if (itemType == Material.DIAMOND_HELMET ||
                        itemType == Material.DIAMOND_CHESTPLATE ||
                        itemType == Material.DIAMOND_LEGGINGS ||
                        itemType == Material.DIAMOND_BOOTS) {
                    count++;
                }
            }
        }

        return count;
    }
}
