package me.klb.thehypixelpitsurvival.customitems.itemMetaData;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class itemManager {
    public static ItemStack funkyFeather;
    public static ItemStack perunsStick;
    public static ItemStack firstAidEgg;
    public static ItemStack firstAidEggCooldown;
    public static ItemStack philosophersCactus;
    public static ItemStack chunkOfVile;
    public static ItemStack yummyBone;
    public static ItemStack redFresh;
    public static ItemStack orangeFresh;
    public static ItemStack yellowFresh;
    public static ItemStack greenFresh;
    public static ItemStack blueFresh;
    public static ItemStack aquaFresh;

    public static void initItemMetaData(){
        createFunkyFeather();
        createPerunsStick();
        createFirstAidEgg();
        createFirstAidEggCooldown();
        createPhilosophersCactus();
        createChunkOfVile();
        createYummyBone();
        createRedFresh();
        createOrangeFresh();
        createYellowFresh();
        createGreenFresh();
        createBlueFresh();
        createAquaFresh();
    }

    private static void createFunkyFeather(){
        ItemStack item = new ItemStack(Material.FEATHER, 64);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_AQUA + "Funky Feather");
        List<String> lore = new ArrayList<>();
        lore.add(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"));
        lore.add(ChatColor.GRAY + "Protects your inventory but");
        lore.add(ChatColor.GRAY + "gets consumed on death.");
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "FunkyFeather");

        item.setItemMeta(meta);
        funkyFeather = item;
    }

    private static void createPerunsStick(){
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Perun's Stick");
        List<String> lore = new ArrayList<>();
        lore.add(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"));
        lore.add(ChatColor.GRAY + "Summons lighting wherever you look");
        lore.add(ChatColor.GRAY + "when you right-click.");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "PerunStick");

        item.setItemMeta(meta);
        perunsStick = item;
    }

    private static void createFirstAidEgg(){
        ItemStack item = new ItemStack(Material.MOOSHROOM_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "First-Aid Egg");
        List<String> lore = new ArrayList<>();
        lore.add(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"));
        lore.add(ChatColor.GRAY + "heals " + ChatColor.RED + "2.5❤");
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "AidEgg");

        item.setItemMeta(meta);
        firstAidEgg = item;
    }

    private static void createFirstAidEggCooldown(){
        ItemStack item = new ItemStack(Material.SKELETON_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "First-Aid Egg");
        List<String> lore = new ArrayList<>();
        lore.add(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"));
        lore.add(ChatColor.GRAY + "heals " + ChatColor.RED + "2.5❤");
        lore.add(ChatColor.GRAY + "On Cooldown!");
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "AidEggCooldown");

        item.setItemMeta(meta);
        firstAidEggCooldown = item;
    }

    private static void createPhilosophersCactus(){
        ItemStack item = new ItemStack(Material.CACTUS, 64);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Philosopher's Cactus");
        List<String> lore = new ArrayList<>();
        lore.add(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"));
        lore.add(ChatColor.GRAY + "Right-click while holding");
        lore.add(ChatColor.GRAY + "this item to summon fresh");
        lore.add(ChatColor.RED + "P" + ChatColor.GOLD + "a" + ChatColor.YELLOW + "n" + ChatColor.GREEN + "t" + ChatColor.BLUE + "s" + ChatColor.RESET + ChatColor.GRAY + " of your");
        lore.add(ChatColor.GRAY + "choice.");
        lore.add("");
        lore.add(ChatColor.GRAY + "(Special pants excluded)");
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "PhilosophersCactus");

        item.setItemMeta(meta);
        philosophersCactus = item;
    }

    private static void createChunkOfVile(){
        ItemStack item = new ItemStack(Material.COAL, 64);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Chunk of Vile");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add("");
        lore.add(ChatColor.RED + "Heretic Artifact");
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "ChunkOfVile");

        item.setItemMeta(meta);
        chunkOfVile = item;
    }

    private static void createYummyBone(){
        ItemStack item = new ItemStack(Material.BONE, 64);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.WHITE + "Yummy Bone");
        List<String> lore = new ArrayList<>();
        lore.add(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"));
        lore.add(ChatColor.GRAY + "Tame wolves instantly!");
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "YummyBone");

        item.setItemMeta(meta);
        yummyBone = item;
    }
    private static void createRedFresh(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Color.RED);

        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.RED + "Fresh Red Pants");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add("");
        lore.add(ChatColor.RED + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore"));
        lore.add(ChatColor.RED + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore2"));
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "RedFresh");
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING, "Fresh");

        item.setItemMeta(meta);
        redFresh = item;
    }

    private static void createOrangeFresh(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Color.ORANGE);

        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.GOLD + "Fresh Orange Pants");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add("");
        lore.add(ChatColor.GOLD + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore"));
        lore.add(ChatColor.GOLD + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore2"));
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "OrangeFresh");
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING, "Fresh");

        item.setItemMeta(meta);
        orangeFresh = item;
    }

    private static void createYellowFresh(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Color.YELLOW);

        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.YELLOW + "Fresh Yellow Pants");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add("");
        lore.add(ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore"));
        lore.add(ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore2"));
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "YellowFresh");
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING, "Fresh");

        item.setItemMeta(meta);
        yellowFresh = item;
    }

    private static void createGreenFresh(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Color.LIME);

        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.GREEN + "Fresh Green Pants");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add("");
        lore.add(ChatColor.GREEN + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore"));
        lore.add(ChatColor.GREEN + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore2"));
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "GreenFresh");
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING, "Fresh");

        item.setItemMeta(meta);
        greenFresh = item;
    }

    private static void createBlueFresh(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Color.BLUE);

        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.BLUE + "Fresh Blue Pants");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add("");
        lore.add(ChatColor.BLUE + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore"));
        lore.add(ChatColor.BLUE + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore2"));
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "BlueFresh");
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING, "Fresh");

        item.setItemMeta(meta);
        blueFresh = item;
    }

    private static void createAquaFresh(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Color.AQUA);

        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.AQUA + "Fresh Aqua Pants");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kept on death");
        lore.add("");
        lore.add(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshPants-Lore"));
        lore.add(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshAqua-Lore"));
        lore.add(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("freshAqua-Lore2"));
        meta.setLore(lore);

        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING, "AquaFresh");
        itemData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticItem"), PersistentDataType.STRING, "Fresh");

        item.setItemMeta(meta);
        aquaFresh = item;
    }
}
