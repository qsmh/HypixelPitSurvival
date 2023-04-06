package me.klb.thehypixelpitsurvival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 0){
                if(player.getAllowFlight()){
                    player.setAllowFlight(false);
                    player.sendMessage(ChatColor.RED + "Disabled " + ChatColor.GOLD + "flight.");
                }else{
                    player.setAllowFlight(true);
                    player.sendMessage(ChatColor.RED + "Enabled " + ChatColor.GOLD + "flight.");
                }
            }else if(args.length == 1){
                if((player.getServer().getPlayer(args[0]) != null)){ // Same as targetPlayer.IsOnline()
                    Player targetPlayer = player.getServer().getPlayer(args[0]);

                    if(targetPlayer.getAllowFlight()){

                        targetPlayer.setAllowFlight(false);
                        player.sendMessage(ChatColor.RED + "Disabled " + ChatColor.GOLD + "flight " + ChatColor.RED + "for " + ChatColor.GOLD + targetPlayer.getDisplayName() + ".");
                    }else{
                        targetPlayer.setAllowFlight(true);
                        player.sendMessage(ChatColor.RED + "Enabled " + ChatColor.GOLD + "flight " + ChatColor.RED + "for " + ChatColor.GOLD + targetPlayer.getDisplayName() + ".");
                    }
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
