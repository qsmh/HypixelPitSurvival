package me.klb.thehypixelpitsurvival.mysticwell;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MysticWellEnchanting implements Listener {
    public static void createMysticWellInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 45, "Mystic Well");

        ItemStack mysticWell = new ItemStack(Material.ENCHANTING_TABLE);
        ItemMeta mysticWellMeta = mysticWell.getItemMeta();

        assert mysticWellMeta != null;

        mysticWellMeta.setDisplayName(TheHypixelPitSurvival.getPlugin().getConfig().getString("mystic-well-displayname"));

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
                        return;
                    }else{
                        event.setCancelled(true);
                        return;
                    }
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
            }
        }
    }
}
