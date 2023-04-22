package me.klb.thehypixelpitsurvival.creatures.customcreatures;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks.UwUKieranBhoppingAttack;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks.UwUKieranPigmanAttack;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks.UwUKieranTntAttack;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks.UwUKieranVenomAttack;
import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import me.klb.thehypixelpitsurvival.megastreaks.uber.functions.UberDrop;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.*;

public class UwUKieran implements Listener {
    private final static double EXPLOSION_RADIUS = 1;
    private static final HashMap<UUID, Integer> Cooldown = new HashMap<>();
    public static void SpawnUwUKieran(LivingEntity entity){
        ItemStack customHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) customHead.getItemMeta();

        assert meta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWFjZDI4NzA3MTg4NzIyMDdhN2Q0NjE0MTkzMzY5Zjg0MTgyOTA1ZWMzNWExZmJjZDNiYWEwNDVlZGE1MWQ5ZSJ9fX0"));
// Replace "texture_value_here" with the base64-encoded texture value for the desired skin, which you can obtain from a website like https://minecraft-heads.com/custom/heads-generator or use a texture library API.

        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


        customHead.setItemMeta(meta);
        Objects.requireNonNull(entity.getEquipment()).setHelmet(customHead);
        ItemStack Chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack Boots = new ItemStack(Material.LEATHER_BOOTS);

        ItemStack Axe = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta axeMeta = Axe.getItemMeta();

        assert axeMeta != null;
        axeMeta.addEnchant(Enchantment.DAMAGE_ALL, 45, true);
        axeMeta.addEnchant(Enchantment.FIRE_ASPECT, 10, false);
        axeMeta.setUnbreakable(true);

        Axe.setItemMeta(axeMeta);
        entity.getEquipment().setItemInMainHand(Axe);

        ItemStack[] armor = new ItemStack[]{Chestplate, Leggings, Boots};

        for(ItemStack i : armor){
            ItemMeta itemMeta = i.getItemMeta();
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;

            assert itemMeta != null;
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 40, true);
            itemMeta.addEnchant(Enchantment.THORNS, 20, true);
            leatherArmorMeta.setColor(Color.YELLOW);
            itemMeta.setUnbreakable(true);

            i.setItemMeta(itemMeta);
        }

        entity.getEquipment().setChestplate(Chestplate);
        entity.getEquipment().setLeggings(Leggings);
        entity.getEquipment().setBoots(Boots);

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.24);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(250.0);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(128);
        entity.setHealth(250.0);

        entity.setCustomName(ChatColor.YELLOW + "UwU_Kieran");
        entity.setCustomNameVisible(true);
        entity.setSilent(true);

        PersistentDataContainer data = entity.getPersistentDataContainer();
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING, "UwUKieran");
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("UwUKieranGoldReward"));
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("UwUKieranXPReward"));

        entity.setPersistent(false); // This will make it despawn
    }

    public static void AttackNumberOne(Entity damager){ // Venom attack
        Cooldown.put(damager.getUniqueId(), 0);

        UwUKieranVenomAttack.runAttack((Player) damager, () -> { //Lambda expression
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), TheHypixelPitSurvival.getPlugin().getConfig().getInt("VenomTime") * 20L);
        });
    }

    public static void AttackNumberTwo(Entity entity, Entity damager){ // Bhop attack
        Cooldown.put(damager.getUniqueId(), 0);

        UwUKieranBhoppingAttack.runAttack((Player) damager, entity, () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 10);
        });
    }

    public static void AttackNumberThree(Entity entity, Entity damager){ // Pigman attack
        Cooldown.put(damager.getUniqueId(), 0);

        UwUKieranPigmanAttack.runAttack((Player) damager, entity, () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 15 * 20L);
        });
    }

    public static void AttackNumberFour(Entity damager){ // Tnt attack
        Cooldown.put(damager.getUniqueId(), 0);

        UwUKieranTntAttack.runAttack((Player) damager, () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 5 * 20L);
        });
    }

    @EventHandler
    public static void UwUKieranDamaged(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player) {
            damager = (Entity) ((Projectile) damager).getShooter();
        }

        if(!entity.getType().equals(EntityType.PLAYER) && !damager.getType().equals(EntityType.PLAYER)){ // If a player did not hit PinkEve return
            return;
        }

        PersistentDataContainer entityData = entity.getPersistentDataContainer();
        PersistentDataContainer damagerData = damager.getPersistentDataContainer();

        if (Objects.equals(entityData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING), "UwUKieran")){ // PinkEve got damaged by an entity
            World entityWorld = entity.getWorld();
            entityWorld.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 1);

            if(Cooldown.containsKey(damager.getUniqueId())){
                return;
            }

            Random random = new Random();

            int randomNumber = random.nextInt(10);

            switch (randomNumber){ // Do not enhance switch statement
                case 0,1,2:
                    AttackNumberOne(damager);
                    break;
                case 3,4,8:
                    AttackNumberTwo(entity, damager);
                    break;
                case 5,6:
                    AttackNumberThree(entity, damager);
                    break;
                case 7,9:
                    AttackNumberFour(damager);
                    break;
            }
        }

        if (Objects.equals(damagerData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING), "UwUKieran")){ // PinkEve damaged an entity
            World damagerWorld = damager.getWorld();
            damagerWorld.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 1);
        }
    }

    @EventHandler
    public static void UwUKieranDied(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Entity killer  = entity.getKiller();

        PersistentDataContainer entityData = entity.getPersistentDataContainer();

        if (Objects.equals(entityData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING), "UwUKieran")) {
            if(killer == null){ return; }

            if(killer instanceof Player){
                event.setDroppedExp(0);
                event.getDrops().clear();

                Random ran = new Random();
                int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranDeathChatLines").size()); // Not returning message; only number

                killer.sendMessage(ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getString("UwUKieranChatString") + ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranDeathChatLines").get(randomMessage));

                World entityWorld = entity.getWorld();

                entityWorld.playSound(entity.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 0.9F);

                ItemStack uberDrop = UberDrop.createUberDrop();
                entityWorld.dropItem(entity.getLocation(), uberDrop);

                uberDrop = UberDrop.createUberDrop();
                entityWorld.dropItem(entity.getLocation(), uberDrop);

                final int[] count = {0};

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        Firework firework = (Firework) entityWorld.spawnEntity(entity.getLocation(), EntityType.FIREWORK);

                        FireworkEffect effect = FireworkEffect.builder()
                                .withColor(Color.YELLOW)
                                .withFade(Color.YELLOW)
                                .with(FireworkEffect.Type.BURST)
                                .trail(true)
                                .flicker(true)
                                .build();
                        FireworkMeta meta = firework.getFireworkMeta();
                        meta.addEffect(effect);
                        meta.setPower(3);
                        firework.setFireworkMeta(meta);

                        count[0]++;
                        if(count[0] == 10){
                            cancel();
                        }

                    }
                }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 3, 5);

                ItemStack dyeStack = new ItemStack(Material.YELLOW_DYE);

                Random random = new Random();
                for (int i = 0; i < 100; i++) {
                    Vector direction = new Vector(
                            random.nextDouble() * (EXPLOSION_RADIUS * 2) - EXPLOSION_RADIUS, // X
                            random.nextDouble() * (EXPLOSION_RADIUS * 2) - EXPLOSION_RADIUS + 1, // Y
                            random.nextDouble() * (EXPLOSION_RADIUS * 2) - EXPLOSION_RADIUS // Z
                    );
                    direction.normalize();
                    Item dyeItem = event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), dyeStack);
                    dyeItem.setPickupDelay(Integer.MAX_VALUE);;
                    dyeItem.setVelocity(direction);
                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            dyeItem.remove();
                        }
                    }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 200);
                }
            }
        }
    }
    @EventHandler
    public void pigmanDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        PersistentDataContainer data = entity.getPersistentDataContainer();

        if (!data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING)){
            return;
        }

        if (Objects.equals(data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING), "Pigman")){
            event.setDroppedExp(0);
            event.getDrops().clear();
        }
    }
}
