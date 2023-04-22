package me.klb.thehypixelpitsurvival;

import me.klb.thehypixelpitsurvival.commands.*;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.PinkEve;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.UwUKieran;
import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import me.klb.thehypixelpitsurvival.customitems.itemProcedures.FirstAidEgg;
import me.klb.thehypixelpitsurvival.customitems.itemProcedures.PerunsStick;
import me.klb.thehypixelpitsurvival.customitems.itemProcedures.PhilosophersCactus;
import me.klb.thehypixelpitsurvival.customitems.itemProcedures.YummyBone;
import me.klb.thehypixelpitsurvival.fixes.HologramRemover;
import me.klb.thehypixelpitsurvival.listeners.*;
import me.klb.thehypixelpitsurvival.megastreaks.uber.functions.UberDrop;
import me.klb.thehypixelpitsurvival.mystics.aquapants.functions.onPlayerFishedListener;
import me.klb.thehypixelpitsurvival.mystics.darkpants.functions.DarkPantsVenom;
import me.klb.thehypixelpitsurvival.mysticwell.MysticWell;
import me.klb.thehypixelpitsurvival.mysticwell.MysticWellEnchanting;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public final class TheHypixelPitSurvival extends JavaPlugin implements Listener {

    private static TheHypixelPitSurvival plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("HPS: This plugin is starting.");

        plugin = this;

        getServer().getPluginManager().registerEvents(this, this); // Need this for listener events/@EventHandler
        getServer().getPluginManager().registerEvents(new onPlayerLeaveBed(), this);
        getServer().getPluginManager().registerEvents(new onJoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new onPlayerFallDamage(), this);
        getServer().getPluginManager().registerEvents(new onPlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new onPlayerRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new onCreatureSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new onEntityDeathListener(), this);
        getServer().getPluginManager().registerEvents(new onEntityTargetListener(), this);
        getServer().getPluginManager().registerEvents(new onEntityDamagedListener(), this);
        getServer().getPluginManager().registerEvents(new onItemApplied(), this); // Enchanting, applying item to anvil situations

        // Custom Entities
        getServer().getPluginManager().registerEvents(new PinkEve(), this);
        getServer().getPluginManager().registerEvents(new UwUKieran(), this);

        // Mystics, Fresh, etc
        getServer().getPluginManager().registerEvents(new MysticWell(), this);
        getServer().getPluginManager().registerEvents(new MysticWellEnchanting(), this);
        getServer().getPluginManager().registerEvents(new PhilosophersCactus(), this);
        getServer().getPluginManager().registerEvents(new DarkPantsVenom(), this);
        getServer().getPluginManager().registerEvents(new onPlayerFishedListener(), this);

        // Megastreaks, megastreak items, etc
        getServer().getPluginManager().registerEvents(new UberDrop(), this);

        getServer().getPluginManager().registerEvents(new PerunsStick(), this);
        getServer().getPluginManager().registerEvents(new FirstAidEgg(), this);
        getServer().getPluginManager().registerEvents(new YummyBone(), this);

        // Fixes
        getServer().getPluginManager().registerEvents(new HologramRemover(), this);

        // Recipies
        MysticWell.addRecipeMysticWell();

        itemManager.initItemMetaData(); // Sets up ItemMetaData
        UberDrop.initUberDropItems();

        getCommand("smite").setExecutor(new smiteCommand()); // Need this for commands
        getCommand("fly").setExecutor(new flyCommand());
        getCommand("flyspeed").setExecutor(new flySpeedCommand());
        getCommand("god").setExecutor(new godCommand());
        getCommand("menu").setExecutor(new menuCommand());
        getCommand("spawn").setExecutor(new spawnCustomEntityCommand());
        getCommand("show").setExecutor(new showCommand());

        System.out.println("HPS: This plugin has successfully loaded.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("HPS: This plugin has stopped.");
    }

    public String returnCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getEntity() instanceof LivingEntity) {
            LivingEntity damagedEntity = (LivingEntity) event.getEntity();
            Entity attacker = ((EntityDamageByEntityEvent) event).getDamager();
            if (attacker instanceof LivingEntity && ((LivingEntity) attacker).getEquipment().getItemInMainHand().getType() == Material.SPONGE) {
                Double Damage = event.getDamage();
                event.setCancelled(true);
                event.setDamage(0.0); // no damage
                if (damagedEntity instanceof Player) {
                    Player player = (Player) damagedEntity;
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1)); // Custom flying velocity
                    Bukkit.getScheduler().runTaskLater(this, () -> {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                    }, 40L); // Custom fly duration in ticks
                } else {
                    Vector velocity = damagedEntity.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize().multiply(1.25).setY(0.75); // Custom flying velocity with reduced y velocity
                    double maxDistance = 3 - 1.3;
                    double distance = attacker.getLocation().distance(damagedEntity.getLocation());
                    if (distance > maxDistance) {
                        velocity = velocity.multiply(maxDistance / distance);
                    }
                    double maxHeight = attacker.getLocation().getY() + 2.0;
                    if (damagedEntity.getLocation().getY() > maxHeight) {
                        velocity.setY(Math.min(velocity.getY(), (maxHeight - attacker.getLocation().getY()) / distance * velocity.length()));
                    }
                 //   velocity = applyLimits(velocity, damagedEntity.getLocation(), attacker);
                    damagedEntity.setVelocity(velocity);

                    damagedEntity.damage(Damage);
                }
            }
        }
    }
    private Vector applyLimits(Vector velocity, Location location, Entity player) { // THIS DOESN'T WORK LOL
        double xzDistance = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getZ(), 2));
        double yDistance = velocity.getY();
        if (yDistance > 3) { // Maximum height limit
            velocity.setY(0);
        }
        if (xzDistance > 2.5) { // Maximum xz distance limit
            double ratio = 1.0 / xzDistance;
            velocity.setX(velocity.getX() * ratio);
            velocity.setZ(velocity.getZ() * ratio);
        }
        return velocity;
    }

    public static TheHypixelPitSurvival getPlugin() {
        return plugin;
    }
}
