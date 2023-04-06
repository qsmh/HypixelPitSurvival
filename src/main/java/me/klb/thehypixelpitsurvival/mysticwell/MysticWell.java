package me.klb.thehypixelpitsurvival.mysticwell;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class MysticWell implements Listener {

    public void mysticWellUI(){

    }

    @EventHandler
    public void onMysticWellOpen(PlayerInteractEvent event){
        if (event.getClickedBlock() == null){
            return;
        }

        if (!(event.getClickedBlock().getState() instanceof TileState tileState)){
            return;
        }


    }

    @EventHandler
    public void onMysticWellPlaced(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        BlockState blockState = block.getState();
        ItemStack item = event.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta == null || !(blockState instanceof TileState tileState)){
            return;
        }

        PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();

        if (!itemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)){
            return;
        }

        if (!Objects.equals(itemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING), "MysticWell")){
            return;
        }

        PersistentDataContainer blockData = tileState.getPersistentDataContainer();
        blockData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomBlock"), PersistentDataType.STRING, "MysticWell");
    }

    @EventHandler
    public void onMysticWellBroken(EntityPickupItemEvent event){
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)){
            return;
        }

        ItemStack item = event.getItem().getItemStack();

        if (!item.hasItemMeta()){
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();
        String itemDisplayName = itemMeta.getDisplayName();

        if (!itemDisplayName.equals(TheHypixelPitSurvival.getPlugin().getConfig().getString("mystic-well-displayname"))){
            return;
        }

        ItemStack mysticWell = createMysticWell(item);
        item.setItemMeta(mysticWell.getItemMeta());
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomBlock"), PersistentDataType.STRING, "MysticWell");
    }

    public static ItemStack createMysticWell(ItemStack item) {
        ItemStack mysticWell = item;
        if (mysticWell == null) {
            mysticWell = new ItemStack(Material.ENCHANTING_TABLE, 1);
        }
        ItemMeta mysticWellMeta = mysticWell.getItemMeta();
        if (mysticWellMeta == null) {
            mysticWellMeta = Bukkit.getItemFactory().getItemMeta(Material.ENCHANTING_TABLE);
        }

        assert mysticWellMeta != null;
        mysticWellMeta.setDisplayName(TheHypixelPitSurvival.getPlugin().getConfig().getString("mystic-well-displayname"));
        List<String> lore = new ArrayList<>();
        lore.add(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"));
        lore.add(ChatColor.GRAY + "Used to enchant mystic items");
        mysticWellMeta.setLore(lore);

        PersistentDataContainer data = mysticWellMeta.getPersistentDataContainer();
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "MysticWell");

        mysticWell.setItemMeta(mysticWellMeta);
        return mysticWell;
    }

    public static void addRecipeMysticWell() {
        NamespacedKey recipeKey = new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticWell");

        ItemStack mysticWell = createMysticWell(null);

        ShapedRecipe mysticWellRecipe = new ShapedRecipe(recipeKey, mysticWell);

        mysticWellRecipe.shape("DDD", "DED", "DDD");

        mysticWellRecipe.setIngredient('E', Material.ENCHANTING_TABLE);
        mysticWellRecipe.setIngredient('D', Material.DIAMOND_BLOCK);

        getServer().addRecipe(mysticWellRecipe);
    }
}
