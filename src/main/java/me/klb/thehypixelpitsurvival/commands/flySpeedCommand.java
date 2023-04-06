package me.klb.thehypixelpitsurvival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flySpeedCommand implements CommandExecutor {

    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 1){
                if(player.getAllowFlight()){
                    if(isNumeric(args[0])){
                        int flightSpeedInt = Integer.parseInt(args[0]);

                        float flightSpeedFloatArg = Integer.parseInt(args[0]);
                        float flightSpeedFloat = flightSpeedFloatArg / 10;

                        if (flightSpeedFloat <= 1.0 && flightSpeedFloat >= -1.0){
                            player.setFlySpeed(flightSpeedFloat);
                            player.sendMessage(ChatColor.RED + "Set" + ChatColor.GOLD + " flight speed" + ChatColor.RED + " to " + ChatColor.GOLD + flightSpeedInt + ".");
                        }else{
                            player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + "Flight speed" + ChatColor.RED + " must be between " + ChatColor.GOLD + "-10 " + ChatColor.RED + "and " + ChatColor.GOLD + "10.");
                        }
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + "You" + ChatColor.RED + " cannot fly.");
                }
            }else if(args.length == 2){
                if((player.getServer().getPlayer(args[0]) != null)){ // Same as targetPlayer.IsOnline()
                    Player targetPlayer = player.getServer().getPlayer(args[0]);

                    if(targetPlayer.getAllowFlight()){
                        if(isNumeric(args[1])){
                            int flightSpeedInt = Integer.parseInt(args[1]);

                            float flightSpeedFloatArg = Integer.parseInt(args[1]);
                            float flightSpeedFloat = flightSpeedFloatArg / 10;

                            if (flightSpeedFloat <= 1.0 && flightSpeedFloat >= -1.0){
                                targetPlayer.setFlySpeed(flightSpeedFloat);
                                player.sendMessage(ChatColor.RED + "Set" + ChatColor.GOLD + " flight speed" + ChatColor.RED + " to " + ChatColor.GOLD + flightSpeedInt + ChatColor.RED + " to " + ChatColor.GOLD + targetPlayer.getDisplayName() + ".");
                            }else{
                                player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + "Flight speed" + ChatColor.RED + " must be between " + ChatColor.GOLD + "-10 " + ChatColor.RED + "and " + ChatColor.GOLD + "10.");
                            }
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GOLD + targetPlayer.getDisplayName() + ChatColor.RED + " cannot fly.");
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
