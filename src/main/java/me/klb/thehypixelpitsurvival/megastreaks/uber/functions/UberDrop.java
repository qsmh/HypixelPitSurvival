package me.klb.thehypixelpitsurvival.megastreaks.uber.functions;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class  UberDrop implements Listener {
    private static final HashMap<String, List<String>> UberDropItems = new HashMap<>();
    private static final Random ran = new Random();

    public static void initUberDropItems(){
        List<String> drops = new ArrayList<>();
        drops.add(ChatColor.GREEN + "3 Funky Feathers");
        drops.add(ChatColor.GREEN + "5 Funky Feathers");
        drops.add(ChatColor.GREEN + "10 Funky Feathers");
        drops.add(ChatColor.GREEN + "1 Philosopher's Cactus");
        drops.add(ChatColor.GREEN + "10 Philosopher's Cactus");
        drops.add(ChatColor.GREEN + "15 Philosopher's Cactus");
        drops.add(ChatColor.DARK_PURPLE + "16 Chunk of Vile");
        drops.add(ChatColor.DARK_PURPLE + "32 Chunk of Vile");
        drops.add(ChatColor.GOLD + "1 Perun's Stick");

        UberDropItems.put("UberDrop", drops);
    }

    public static ItemStack createUberDrop(){
        String uberDropItemDrop = getUberDropItem();
        ItemStack uberDropItem = new ItemStack(Material.ENDER_CHEST);
        ItemMeta uberDropMeta = uberDropItem.getItemMeta();

        assert uberDropMeta != null;
        uberDropMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Uberdrop");

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add(ChatColor.GRAY + "Contains: " + uberDropItemDrop);
        lore.add("");
        lore.add(ChatColor.YELLOW + "Place down to open!");

        PersistentDataContainer uberDropData = uberDropMeta.getPersistentDataContainer();
        uberDropData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "UberDrop"), PersistentDataType.STRING, uberDropItemDrop);

        uberDropMeta.setLore(lore);
        uberDropItem.setItemMeta(uberDropMeta);

        return uberDropItem;
    }

    public static String getUberDropItem(){
        List<String> drops = UberDropItems.get("UberDrop");

        return drops.get(ran.nextInt(drops.size()));
    }

    @EventHandler
    public void onUberPlaced(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack UberDropItem = event.getItemInHand();
        Block UberDropBlock = event.getBlock();
        ItemMeta UberDropItemMeta = UberDropItem.getItemMeta();
        assert UberDropItemMeta != null;
        PersistentDataContainer UberDropData = UberDropItemMeta.getPersistentDataContainer();

        if (!UberDropData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "UberDrop"), PersistentDataType.STRING)){
            return;
        }

        event.setCancelled(true);

        World world = player.getWorld();

        String[] args = Objects.requireNonNull(UberDropData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "UberDrop"), PersistentDataType.STRING)).split(" ");
        if (args.length == 3 && args[1].equalsIgnoreCase("Funky")) {
            try {
                int amount = Integer.parseInt(args[0].substring(2));
                ItemStack funkyFeather = itemManager.funkyFeather.clone();
                funkyFeather.setAmount(amount);
                player.getInventory().addItem(funkyFeather);
                UberDropItem.setAmount(UberDropItem.getAmount() - 1);
                player.updateInventory();
                return;
            } catch (NumberFormatException ignored) {

            }
        }

        if (args.length == 3 && args[1].equalsIgnoreCase("Philosopher's")) {
            try {
                int amount = Integer.parseInt(args[0].substring(2));
                ItemStack funkyFeather = itemManager.philosophersCactus.clone();
                funkyFeather.setAmount(amount);
                player.getInventory().addItem(funkyFeather);
                UberDropItem.setAmount(UberDropItem.getAmount() - 1);
                player.updateInventory();
            } catch (NumberFormatException ignored) {

            }
        }

        if (args.length == 3 && args[1].equalsIgnoreCase("Perun's")) {
            try {
                int amount = Integer.parseInt(args[0].substring(2));
                ItemStack funkyFeather = itemManager.perunsStick.clone();
                funkyFeather.setAmount(amount);
                player.getInventory().addItem(funkyFeather);
                UberDropItem.setAmount(UberDropItem.getAmount() - 1);
                player.updateInventory();
            } catch (NumberFormatException ignored) {

            }
        }

        if (args.length == 4 && args[3].equalsIgnoreCase("Vile")) {
            try {
                int amount = Integer.parseInt(args[0].substring(2));
                ItemStack funkyFeather = itemManager.chunkOfVile.clone();
                funkyFeather.setAmount(amount);
                player.getInventory().addItem(funkyFeather);
                UberDropItem.setAmount(UberDropItem.getAmount() - 1);
                player.updateInventory();
            } catch (NumberFormatException ignored) {

            }
        }

        world.spawnParticle(Particle.EXPLOSION_LARGE, UberDropBlock.getLocation(), 1);
        world.playSound(UberDropBlock.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1.3F);
    }
}
