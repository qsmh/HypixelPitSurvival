package me.klb.thehypixelpitsurvival;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ActionBarHearts {
    private static final String HEALTH_ICON = ChatColor.DARK_RED + "❤"; // Heart icon for full health
    private static final String EMPTY_HEALTH_ICON = ChatColor.BLACK + "❤"; // Heart icon for empty health
    private static final String HALF_HEALTH_ICON = ChatColor.RED + "❤"; // Heart icon for half health
    private static final String ABSORPTION_ICON = ChatColor.GOLD + "❤"; // Heart icon for absorption health

    public static void runActionBarHearts(EntityDamageByEntityEvent event, Boolean shotProjectile) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        // Creates a new BukkitRunnable, which will be executed later
        new BukkitRunnable() {
            @Override
            public void run() {
                double health = livingEntity.getHealth();
                double maxHealth = Objects.requireNonNull(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
                if (health > maxHealth) {
                    health = maxHealth;
                }

                double totalHealth = health + livingEntity.getAbsorptionAmount();
                double hearts = health / 2;

                if (hearts > 10) {
                    hearts = 10;
                }

                // Calculates the number of filled hearts, half-hearts and empty hears
                int filledHearts = (int) Math.floor(hearts);
                int filledHalfHearts = (int) Math.round((hearts % 1) * 2);
                int emptyHearts = 10 - filledHearts - (filledHalfHearts > 0 ? 1 : 0);

                // Creates a StringBuilder to create the health display message
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.RESET);
                sb.append(ChatColor.GRAY).append(entity.getName()).append(" ");
                sb.append(ChatColor.RESET);

                // Adds filled hearts to the display message
                sb.append(HEALTH_ICON.repeat(Math.max(0, filledHearts)));

                // Adds half-filled hearts to the display message
                if (filledHalfHearts > 0) {
                    sb.append(HALF_HEALTH_ICON);
                }

                // Adds empty hearts to the display message
                sb.append(EMPTY_HEALTH_ICON.repeat(Math.max(0, emptyHearts)));

                boolean hasExtraHealth = false;
                double extraHealth = health - (filledHearts + (filledHalfHearts > 0 ? 0.5 : 0)) * 2;

                if (totalHealth > (filledHearts + (filledHalfHearts > 0 ? 0.5 : 0)) * 2 && (int) extraHealth >= 1) {
                    sb.append(ChatColor.RED + " +" + (int) extraHealth + ChatColor.DARK_RED + " ❤");
                    hasExtraHealth = true;
                }

                // Adds absorption hearts to the display message
                if (livingEntity.getAbsorptionAmount() > 0) {
                    sb.append(ChatColor.RESET);
                    if(hasExtraHealth){ sb.append(" "); }
                    for (int i = 0; i < livingEntity.getAbsorptionAmount() ; i++) {
                        if (i == 10) {
                            break;
                        }
                        sb.append(ABSORPTION_ICON);
                    }
                }

                if ((int) (livingEntity.getAbsorptionAmount() / 2) > 10) {
                    sb.append(ChatColor.GOLD + " +" + (int) ((livingEntity.getAbsorptionAmount() / 2) - 10) + " ❤");
                }

                Player player;

               if(shotProjectile){
                   player = (Player) ((Projectile) event.getDamager()).getShooter();
               }else{
                   player = (event.getDamager() instanceof Player ? (Player) event.getDamager() : null);
               }
                if (player != null) {
                    if (totalHealth <= 0) { // If the entity has no health left, displays a kill message
                        String deathMessage = ChatColor.GRAY + entity.getName() + " " + ChatColor.GREEN + ChatColor.BOLD + "KILL!";
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(deathMessage));
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(sb.toString()));
                    }
                }
            }
        }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 2); // Executes the BukkitRunnable 2 ticks later
    }
}
