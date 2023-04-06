package me.klb.thehypixelpitsurvival.commands;

import me.klb.thehypixelpitsurvival.creatures.customcreatures.PinkEve;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.UwUKieran;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks.PinkEveDogSpawnAttack;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks.PinkEveSlimeSpawnAttack;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class spawnCustomEntityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Boolean entitySpawned = true;
            String entityName = args[0];
            Player player = (Player) sender;

            if(args.length == 1){
                switch (entityName.toLowerCase()){
                    case "pinkev3", "pinkeve":
                        spawnPinkEve(player);
                        break;
                    case "uwu_kieran", "uwukieran":
                        spawnUwUKieran(player);
                        break;
                    case "wolf", "dog":
                        PinkEveDogSpawnAttack.createWolf(player, player);
                        break;
                    case "jimpy":
                        PinkEveSlimeSpawnAttack.createJimpy(player, player);
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + "Invalid entity.");
                        entitySpawned = false;
                        break;
                }

                if (entitySpawned){
                    player.sendMessage(ChatColor.RED + "Spawned " + ChatColor.GOLD + entityName + ".");
                }
            }else{
                player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + "Invalid arguments.");
            }
        }

        return true;
    }

    public void spawnPinkEve(Player player){
        World world = player.getWorld();
        LivingEntity entity = (LivingEntity) world.spawnEntity(player.getLocation(), EntityType.SKELETON);

        PinkEve.SpawnPinkEve(entity);
    }

    public void spawnUwUKieran(Player player){
        World world = player.getWorld();
        LivingEntity entity = (LivingEntity) world.spawnEntity(player.getLocation(), EntityType.SKELETON);

        UwUKieran.SpawnUwUKieran(entity);
    }
}
