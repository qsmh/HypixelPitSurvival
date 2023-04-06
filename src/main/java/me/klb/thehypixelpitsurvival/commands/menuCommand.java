package me.klb.thehypixelpitsurvival.commands;

import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class menuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player player){
            Inventory menu = Bukkit.createInventory(player, 27, "Admin Menu");

            ItemStack illegalTotems = new ItemStack(Material.TOTEM_OF_UNDYING, 64);
            ItemStack funkyFeather = itemManager.funkyFeather;
            ItemStack perunsStick = itemManager.perunsStick;
            ItemStack firstAidEgg = itemManager.firstAidEgg;
            ItemStack philosophersCactus = itemManager.philosophersCactus;
            ItemStack chunkOfVile = itemManager.chunkOfVile;
            ItemStack yummyBone = itemManager.yummyBone;

            ItemStack[] contents = {illegalTotems, perunsStick, firstAidEgg, funkyFeather, philosophersCactus, chunkOfVile, yummyBone};
            menu.addItem(contents);
            player.openInventory(menu);
        }

        return true;
    }
}
