package me.klb.thehypixelpitsurvival.customitems.itemProcedures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class FirstAidEgg implements Listener {

    private final HashMap<UUID, Integer> Cooldown = new HashMap<>();

    @EventHandler
    public void onAidEggClicked(PlayerInteractEvent event){
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!event.hasItem() || Objects.requireNonNull(event.getItem()).getItemMeta() == null) {
            return;
        }

        ItemStack item = event.getItem();

        assert item != null;
        PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();

        if (!itemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)){
            return;
        }

        if (Objects.equals(itemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING), "AidEgg")){ // First Aid-Egg
            if(Cooldown.containsKey(player.getUniqueId())){
                event.setCancelled(true);

                return;
            }

            event.setCancelled(true); // Stops the spawning of the spawn_egg
            item.setAmount(1); // If stacked it will set to 1

            if(player.getHealth() + 5 > 20){ // 2.5 for 10 hearts, 5 for 20
                player.setHealth(20);

            }else {
                player.setHealth(player.getHealth() + 5);
            }

            world.playSound(player, Sound.ENTITY_CAT_HISS, 3, 4);
            onAidEggCooldown(event);
        }else if (Objects.equals(itemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING), "AidEggCooldown")){ // First-Aid Egg On Cooldown
            event.setCancelled(true);
        }
    }

    public void onAidEggCooldown(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        Cooldown.put(player.getUniqueId(), 0);

        if(player.getInventory().getItemInMainHand().equals(item)){
            player.getInventory().setItemInMainHand(itemManager.firstAidEggCooldown);
        }else if(player.getInventory().getItemInOffHand().equals(item)){
            player.getInventory().setItemInOffHand(itemManager.firstAidEggCooldown);
        }

        player.updateInventory();
        startReturnAidEgg(event);
    }

    public void returnAidEgg(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack[] contents = player.getInventory().getContents();


        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];

            if(item != null && item.hasItemMeta()){
                ItemMeta itemMeta = item.getItemMeta();

                assert itemMeta != null;
                PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();

                if (Objects.equals(itemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING), "AidEggCooldown")){
                    player.getInventory().setItem(i, itemManager.firstAidEgg);
                }
            }
        }

        player.updateInventory();
        Cooldown.remove(player.getUniqueId());
    }

    public void startReturnAidEgg(PlayerInteractEvent event){
        new BukkitRunnable() {
            @Override
            public void run() {
                returnAidEgg(event);
            }
        }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 200); // delay of 200 ticks (10 seconds)
    }
}
