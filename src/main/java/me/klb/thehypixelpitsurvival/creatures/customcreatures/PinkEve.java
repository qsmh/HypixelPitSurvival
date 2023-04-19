package me.klb.thehypixelpitsurvival.creatures.customcreatures;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks.PinkEveDogSpawnAttack;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks.PinkEveLightingStrikeAttack;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks.PinkEvePotionAttack;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks.PinkEveSlimeSpawnAttack;
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
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
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
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class PinkEve implements Listener {
    private final static double EXPLOSION_RADIUS = 1;
    private static final HashMap<UUID, Integer> Cooldown = new HashMap<>();
    public static void SpawnPinkEve(LivingEntity entity){
        ItemStack customHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) customHead.getItemMeta();

        assert meta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzhlNDM5YTZiOTUyNjQwOTE0NGU5MDRkNTMzNzk0NTYxNWVmYmUxODI0ZjBjODVjYTg5ODQ2MTY0YzgwOWUzNSJ9fX0"));
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

        ItemStack Axe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta axeMeta = Axe.getItemMeta();

        assert axeMeta != null;
        axeMeta.addEnchant(Enchantment.DAMAGE_ALL, 35, true);
        axeMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
        axeMeta.setUnbreakable(true);

        Axe.setItemMeta(axeMeta);
        entity.getEquipment().setItemInMainHand(Axe);

        ItemStack[] armor = new ItemStack[]{Chestplate, Leggings, Boots};

        for(ItemStack i : armor){
            ItemMeta itemMeta = i.getItemMeta();
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;

            assert itemMeta != null;
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 35, true);
            itemMeta.addEnchant(Enchantment.THORNS, 25, true);
            leatherArmorMeta.setColor(Color.AQUA);
            itemMeta.setUnbreakable(true);

            i.setItemMeta(itemMeta);
        }

        entity.getEquipment().setChestplate(Chestplate);
        entity.getEquipment().setLeggings(Leggings);
        entity.getEquipment().setBoots(Boots);

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.35);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(150.0);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(64);
        entity.setHealth(150.0);

        entity.setCustomName(ChatColor.AQUA + "PinkEv3");
        entity.setCustomNameVisible(true);
        entity.setSilent(true);

        PersistentDataContainer data = entity.getPersistentDataContainer();
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING, "PinkEve");
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("PinkEveGoldReward"));
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("PinkEveXPReward"));
        entity.setPersistent(false); // This will make it despawn
    }

    public static void AttackNumberOne(Entity damager){ // Lighting strike attack
        Cooldown.put(damager.getUniqueId(), 0);

        PinkEveLightingStrikeAttack.runAttack((Player) damager, () -> { //Lambda expression
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 80);
        });
    }

    public static void AttackNumberTwo(Entity entity, Entity damager){ // Dog/Cooper attack
        Cooldown.put(damager.getUniqueId(), 0);

        PinkEveDogSpawnAttack.runAttack((Player) damager, entity, () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 160);
        });
    }

    public static void AttackNumberThree(Entity entity, Entity damager){ // Potion attack
        Cooldown.put(damager.getUniqueId(), 0);

        PinkEvePotionAttack.runAttack((Player) damager, (LivingEntity) entity, () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 80);
        });
    }

    public static void AttackNumberFour(Entity entity, Entity damager){ // Slime attack
        Cooldown.put(damager.getUniqueId(), 0);

        PinkEveSlimeSpawnAttack.runAttack((Player) damager, entity, () -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Cooldown.remove(damager.getUniqueId());
                }
            }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 180);
        });
    }

    @EventHandler
    public static void PinkEveDamaged(EntityDamageByEntityEvent event) {
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

        if (Objects.equals(entityData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING), "PinkEve")){ // PinkEve got damaged by an entity
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
                case 5,6,7:
                    AttackNumberThree(entity, damager);
                    break;
                case 9:
                    AttackNumberFour(entity, damager);
                    break;
            }
        }

        if (Objects.equals(damagerData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING), "PinkEve")){ // PinkEve damaged an entity
            World damagerWorld = damager.getWorld();
            damagerWorld.playSound(entity.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 1);
        }
    }

    @EventHandler
    public static void PinkEveDied(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Entity killer  = entity.getKiller();

        PersistentDataContainer entityData = entity.getPersistentDataContainer();

        if (Objects.equals(entityData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING), "PinkEve")) { // PinkEve died
            if(killer == null){ return; }

            if(killer instanceof Player){
                event.setDroppedExp(0);
                event.getDrops().clear();

                Random ran = new Random();
                int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveDeathChatLines").size()); // Not returning message; only number

                killer.sendMessage(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveChatString") + ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveDeathChatLines").get(randomMessage));

                World entityWorld = entity.getWorld();

                ItemStack funkyFeather = itemManager.funkyFeather;
                funkyFeather.setAmount(3);

                entityWorld.playSound(entity.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 0.9F);
                entityWorld.dropItem(entity.getLocation(), funkyFeather);

                final int[] count = {0};

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        Firework firework = (Firework) entityWorld.spawnEntity(entity.getLocation(), EntityType.FIREWORK);

                        FireworkEffect effect = FireworkEffect.builder()
                                .withColor(Color.AQUA)
                                .withFade(Color.AQUA)
                                .with(FireworkEffect.Type.BALL_LARGE)
                                .trail(true)
                                .flicker(true)
                                .build();
                        FireworkMeta meta = firework.getFireworkMeta();
                        meta.addEffect(effect);
                        meta.setPower(3);
                        firework.setFireworkMeta(meta);

                        count[0]++;
                        if(count[0] == 5){
                            cancel();
                        }

                    }
                }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 3, 5);

                ItemStack dyeStack = new ItemStack(Material.LIGHT_BLUE_DYE);

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

                    ItemStack uberDrop = UberDrop.createUberDrop();
                    entityWorld.dropItem(entity.getLocation(), uberDrop);

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
    public static void onSlimeSplit(SlimeSplitEvent event){
        Slime slime = event.getEntity();

        if(Objects.equals(slime.getName(), ChatColor.GREEN + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveSlimeName"))){
            event.setCancelled(true);
        }
    }
}
