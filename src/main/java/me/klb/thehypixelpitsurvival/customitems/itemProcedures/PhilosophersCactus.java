package me.klb.thehypixelpitsurvival.customitems.itemProcedures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhilosophersCactus implements Listener {

    public static void createPhilosophersCactusInventory(Player player){
        Inventory philosophersCactusInventory = Bukkit.createInventory(player, 27, "Philosopher's Cactus");

        philosophersCactusInventory.setItem(11, itemManager.redFresh);
        philosophersCactusInventory.setItem(12, itemManager.yellowFresh);
        philosophersCactusInventory.setItem(13, itemManager.blueFresh);
        philosophersCactusInventory.setItem(14, itemManager.orangeFresh);
        philosophersCactusInventory.setItem(15, itemManager.greenFresh);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Consume the " + ChatColor.GREEN + "Philosopher's");
        lore.add(ChatColor.GREEN + "Cactus " + ChatColor.GRAY + "to obtain fresh");
        lore.add(ChatColor.GRAY + "pants of this color.");
        lore.add("");
        lore.add(ChatColor.YELLOW + "Click to pet the cactus!");

        int startIndex = 11;
        int endIndex = 15;

        for (int i = startIndex; i <= endIndex; i++) {
            ItemStack fresh = philosophersCactusInventory.getItem(i);

            if (fresh == null){
                return;
            }

            ItemMeta freshMeta = fresh.getItemMeta();

            if (freshMeta == null) {
                return;
            }

            freshMeta.setLore(lore);
            fresh.setItemMeta(freshMeta);
        }

        player.openInventory(philosophersCactusInventory);
    }

    public void giveFreshPants(InventoryClickEvent event, String color){
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        ItemStack fresh;
        assert currentItem != null;

        ItemMeta currentItemMeta = currentItem.getItemMeta();
        boolean givenFresh;
        givenFresh = false;

        switch (color){
            case "red":
                fresh = itemManager.redFresh;

                fresh.setAmount(1);
                player.getInventory().addItem(fresh);

                player.updateInventory();

                givenFresh = true;
                break;
            case "orange":
                fresh = itemManager.orangeFresh;

                fresh.setAmount(1);
                player.getInventory().addItem(fresh);

                player.updateInventory();

                givenFresh = true;
                break;
            case "yellow":
                fresh = itemManager.yellowFresh;

                fresh.setAmount(1);
                player.getInventory().addItem(fresh);

                player.updateInventory();

                givenFresh = true;
                break;
            case "green":
                fresh = itemManager.greenFresh;

                fresh.setAmount(1);
                player.getInventory().addItem(fresh);

                player.updateInventory();

                givenFresh = true;
                break;
            case "blue":
                fresh = itemManager.blueFresh;

                fresh.setAmount(1);
                player.getInventory().addItem(fresh);

                player.updateInventory();

                givenFresh = true;
                break;
        }

        if (givenFresh){
            assert currentItemMeta != null;

            player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "THE CACTUS HAS SPOKEN! " + ChatColor.RESET + ChatColor.GRAY + "Obtained " + currentItemMeta.getDisplayName() + "!");
            player.playSound(player, Sound.ENTITY_SILVERFISH_AMBIENT, 3F, 1.35F);
            player.playSound(player, Sound.ENTITY_SILVERFISH_AMBIENT, 2F, 1.15F);
            player.playSound(player, Sound.ENTITY_SILVERFISH_AMBIENT, 3F, 1.35F);
            player.playSound(player, Sound.ENTITY_SILVERFISH_AMBIENT, 2F, 1.15F);

            removePhilosophersCactus(player);
            player.closeInventory();
        }
    }
    public void removePhilosophersCactus(Player player){
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();

                assert itemMeta != null;
                PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();

                if (itemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)) {
                    String customItemType = itemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING);
                    if (Objects.equals(customItemType, "PhilosophersCactus")) {
                        item.setAmount(item.getAmount() - 1);

                        player.updateInventory();
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (!event.getView().getTitle().equals("Philosopher's Cactus")){
            return;
        }

        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null || currentItem.getItemMeta() == null){
            return;
        }

        String[] displayNameParts = currentItem.getItemMeta().getDisplayName().split(" ");

        if (displayNameParts.length >= 2) {
            String freshColor = displayNameParts[1].toLowerCase();
            giveFreshPants(event, freshColor);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (event.getItem() == null){
                return;
            }

            ItemStack philosophersCactus = event.getItem();
            assert philosophersCactus != null;

            ItemMeta philoMeta = philosophersCactus.getItemMeta();
            assert philoMeta != null;

            PersistentDataContainer philoData = philoMeta.getPersistentDataContainer();

            if (!philoData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)){
                return;
            }

            String customItemString = philoData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING);

            assert customItemString != null;
            if (customItemString.equals("PhilosophersCactus")){
                createPhilosophersCactusInventory(event.getPlayer());
            }
        }
    }
}
