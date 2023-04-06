package me.klb.thehypixelpitsurvival.creatures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.PinkEve;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;

public class ZombieSpawn {
    public static void runZombieSpawn(CreatureSpawnEvent event){
        int chainmailZombie = 70;
        int ironZombie = 40;

        Zombie entity = (Zombie) event.getEntity();

        Random random = new Random();
        int randomNumber = random.nextInt(141); // Random number 0-149

        if(randomNumber < chainmailZombie){
            Objects.requireNonNull(entity.getEquipment()).setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

            if(randomNumber > ironZombie && randomNumber < chainmailZombie - 5){
                ItemStack Sword = new ItemStack(Material.STONE_SWORD);
                entity.getEquipment().setItemInMainHand(Sword);
            }

            if(randomNumber > ironZombie && randomNumber < chainmailZombie - 15){
                ItemStack Sword = new ItemStack(Material.STONE_AXE);
                entity.getEquipment().setItemInMainHand(Sword);
            }

        }

        if(randomNumber < ironZombie){
            entity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));

            PersistentDataContainer data1 = entity.getPersistentDataContainer();
            data1.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("ironZombieGoldReward"));
            data1.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("ironZombieXPReward"));

            if(randomNumber < ironZombie - 5){
                ItemStack Sword = new ItemStack(Material.IRON_SWORD);
                entity.getEquipment().setItemInMainHand(Sword);
            }

            if(randomNumber < ironZombie - 15){
                ItemStack Sword = new ItemStack(Material.IRON_AXE);
                entity.getEquipment().setItemInMainHand(Sword);
            }

            if(randomNumber < ironZombie - 20){
                ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
                entity.getEquipment().setItemInMainHand(Sword);
            }

            if(randomNumber < ironZombie - 25){
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000 * 20, 0));
            }

            if(randomNumber < ironZombie - 30){
                ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);

                ItemMeta SwordMeta = Sword.getItemMeta();
                assert SwordMeta != null;
                SwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);

                Sword.setItemMeta(SwordMeta);
                entity.getEquipment().setItemInMainHand(Sword);
                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                PersistentDataContainer data2 = entity.getPersistentDataContainer();
                data1.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("1/4diamondZombieXPReward"));
                data2.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("1/4diamondZombieGoldReward"));
            }

            if(randomNumber < ironZombie - 35){
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000 * 20, 1));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 10000000 * 20, 1));
            }
        }
    }
}
