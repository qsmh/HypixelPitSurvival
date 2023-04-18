package me.klb.thehypixelpitsurvival.mysticwell;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MysticWellEnchanting implements Listener {

    public static Object[] calculateEnchanting(ItemStack currentItem, Player player){
        Integer goldCost = null;
        String itemTier = null;
        String canAfford = "true";

        ItemMeta currentItemMeta = currentItem.getItemMeta();
        assert currentItemMeta != null;

        PersistentDataContainer currentItemData = currentItemMeta.getPersistentDataContainer();

        if (!currentItemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING)){
            return null;
        }

        String mysticItemData = currentItemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING);

        switch (mysticItemData){ // Ignore highlighted stuff here
            case "Fresh":
                goldCost = 1000;
                itemTier = "I";
                break;
            case "Fresh 1":
                goldCost = 4000;
                itemTier = "II";
                break;
            case "Fresh 2":
                goldCost = 8000;
                itemTier = "III";
                break;
        }

        // Continue here if player has something special that takes off some price for the gold

        PersistentDataContainer data = player.getPersistentDataContainer();
        Double goldBalance = data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE);

        if (goldCost > goldBalance){
            canAfford = "false";
        }

        return new Object[]{goldCost, itemTier, canAfford};
    }
    public static void createMysticWellInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 45, "Mystic Well");

        ItemStack mysticWell = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta mysticWellMeta = mysticWell.getItemMeta();
        assert mysticWellMeta != null;

        PersistentDataContainer mysticWellData = mysticWellMeta.getPersistentDataContainer();
        mysticWellData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticWellEnchanting"), PersistentDataType.STRING, "true");

        mysticWellMeta.setDisplayName(TheHypixelPitSurvival.getPlugin().getConfig().getString("mystic-well-displayname"));
        mysticWell.setItemMeta(mysticWellMeta);

        inventory.setItem(24, mysticWell);

        inventory.setItem(10, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(11, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(12, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(19, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(21, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(28, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(29, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        player.openInventory(inventory);
    }

    @EventHandler
    public void onMysticWellInventoryClose(InventoryCloseEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        if (inventory.getItem(20) == null){
            return;
        }

        ItemStack item = inventory.getItem(20);
        player.getInventory().addItem(item);
    }

    @EventHandler
    public void onMysticWellInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        Inventory inventoryClicked = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        ItemStack slotItem = inventory.getItem(20);

        if (event.getView().getTitle().equals("Mystic Well")){
            if (currentItem == null){
                event.setCancelled(true);
                return;
            }

            if (slotItem != null){
                if (inventory == inventoryClicked){
                    if (currentItem.isSimilar(slotItem)){
                        player.getInventory().addItem(currentItem);
                        inventory.setItem(20, null);
                        player.updateInventory();
                    }else{
                        event.setCancelled(true);
                    }

                    return;
                }
            }

            ItemMeta currentItemMeta = currentItem.getItemMeta();
            assert currentItemMeta != null;

            PersistentDataContainer currentItemData = currentItemMeta.getPersistentDataContainer();

            if (currentItemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING)){
                if (slotItem != null){
                    event.setCancelled(true);
                    return;
                }

                inventory.setItem(20, currentItem);
                event.setCurrentItem(null);
                player.updateInventory();
            }else{
                event.setCancelled(true);
                return;
            }

            Object[] result = calculateEnchanting(currentItem, player);

            if (result == null){ // This never should happen
                return;
            }

            Integer goldCost = (Integer) result[0];
            String itemTier = (String) result[0]; // Ignore highlighted

            ItemStack mysticWell = inventory.getItem(24);

            if (mysticWell == null){ // Also should never happen
                return;
            }

            ItemMeta mysticWellMeta = mysticWell.getItemMeta();
            List<String> lore = new ArrayList<>();
            DecimalFormat costFormat = new DecimalFormat("#,###");


            lore.add(ChatColor.GRAY + "Upgrade: " + ChatColor.GREEN + "Tier " + itemTier);
            lore.add(ChatColor.GRAY + "Cost: " + ChatColor.GOLD + costFormat.format(goldCost.toString()) + "g");


        }
    }
}
