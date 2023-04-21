package me.klb.thehypixelpitsurvival.commands;

import me.klb.thehypixelpitsurvival.other.TitleBuilder;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class godCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 0){
                if(player.isInvulnerable()){ // True statement
                    player.setInvulnerable(false);
                    player.sendMessage(ChatColor.RED + "Disabled " + ChatColor.GOLD + "godmode.");
                }else{
                    player.setInvulnerable(true);
                    player.sendMessage(ChatColor.RED + "Enabled " + ChatColor.GOLD + "godmode.");
                }
            }else if(args.length == 1){
                if((player.getServer().getPlayer(args[0]) != null)){ // Same as targetPlayer.IsOnline()
                    Player targetPlayer = player.getServer().getPlayer(args[0]);

                    assert targetPlayer != null;
                    if(targetPlayer.isInvulnerable()){
                        targetPlayer.setInvulnerable(false);
                        player.sendMessage(ChatColor.RED + "Disabled " + ChatColor.GOLD + "godmode " + ChatColor.RED + "for " + ChatColor.GOLD + targetPlayer.getDisplayName() + ".");
                    }else{
                        targetPlayer.setInvulnerable(true);
                        player.sendMessage(ChatColor.RED + "Enabled " + ChatColor.GOLD + "godmode " + ChatColor.RED + "for " + ChatColor.GOLD + targetPlayer.getDisplayName() + ".");
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