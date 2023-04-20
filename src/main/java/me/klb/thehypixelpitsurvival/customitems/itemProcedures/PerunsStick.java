package me.klb.thehypixelpitsurvival.customitems.itemProcedures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static me.klb.thehypixelpitsurvival.megastreaks.uber.functions.UberDrop.createUberDrop;

public class PerunsStick implements Listener {
    private final HashMap<UUID, Long> cooldown; // Needs to be a long because of the System.currentTimeMillis
    // Final doesn't need to be added, I think? Let's just make sure it works, so we'll keep

    public PerunsStick(){
        this.cooldown = new HashMap<>();
    }
    @EventHandler
    public void initPerunsStick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        World world = player.getWorld();
        ItemStack item = event.getItem();

        int cooldownint = TheHypixelPitSurvival.getPlugin().getConfig().getInt("PerunsStickCooldown") * 1000;

        if (item != null && item.hasItemMeta()) {
            if(Objects.equals(item.getItemMeta(), itemManager.perunsStick.getItemMeta())){
                if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
                    if (!cooldown.containsKey(player.getUniqueId()) || System.currentTimeMillis() - cooldown.get(player.getUniqueId()) > cooldownint) {
                        cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                        if(player.getTargetBlockExact(100) != null){
                            Block targetBlock = player.getTargetBlockExact(100);

                            assert targetBlock != null;
                            world.strikeLightning(targetBlock.getLocation());
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "Cooldown: Wait " + ChatColor.GOLD + (cooldownint - (System.currentTimeMillis() - cooldown.get(player.getUniqueId()))) / 1000 + ChatColor.RED + " seconds.");
                    }
                }
            }
        }
    }
}