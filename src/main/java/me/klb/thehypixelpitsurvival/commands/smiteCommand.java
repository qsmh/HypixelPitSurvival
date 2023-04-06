package me.klb.thehypixelpitsurvival.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class smiteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            World world = player.getWorld();

            if(args.length == 0){
                world.strikeLightning(player.getLocation());
                player.sendMessage(ChatColor.RED + "Smited " + ChatColor.GOLD + player.getDisplayName() + ".");
            }else if(args.length == 1){
                if((player.getServer().getPlayer(args[0]) != null)){ // Same as targetPlayer.IsOnline()
                    Player targetPlayer = player.getServer().getPlayer(args[0]);
                    World targetWorld = targetPlayer.getWorld();

                    targetWorld.strikeLightning(targetPlayer.getLocation());
                    player.sendMessage(ChatColor.RED + "Smited " + ChatColor.GOLD+ targetPlayer.getDisplayName() + ".");
                }else{
                    player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + args[0] + ChatColor.RED + " is not online or does not exist.");
                }
            }else{
                player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + "Invalid arguments.");
            }
        }

        return true;
    }
}