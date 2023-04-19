package me.klb.thehypixelpitsurvival.mystics.aquapants.functions;

import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class onPlayerFishedListener implements Listener {
    @EventHandler
    public void onPLayerFished(PlayerFishEvent event){
        // I know this is supposed to be in the 'listeners' package, but this feels right, lol

        Player player = event.getPlayer();
        Entity entityCaught = event.getCaught();
        FishHook fishHook = event.getHook();

        if (entityCaught == null){
            return;
        }

        if (!(entityCaught instanceof Item)){
            return;
        }

        Random random = new Random();
        int randomInt = random.nextInt(11);

        if (randomInt == 1){
            Item caughtItem  = (Item) entityCaught;
            ItemStack aquaPants =  itemManager.aquaFresh;
            ItemMeta aquaPantsMeta = aquaPants.getItemMeta();

            caughtItem.setItemStack(aquaPants);
            player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 2, 0.75F);
            player.spawnParticle(Particle.WATER_SPLASH, fishHook.getLocation(), 15);
            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "RARE! " + ChatColor.RESET + ChatColor.GRAY + "You found " + ChatColor.AQUA + aquaPantsMeta.getDisplayName() + ".");
        }
    }
}