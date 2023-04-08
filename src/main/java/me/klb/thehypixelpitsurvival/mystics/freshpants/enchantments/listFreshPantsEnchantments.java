package me.klb.thehypixelpitsurvival.mystics.freshpants.enchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listFreshPantsEnchantments {
    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> enchantments = new ArrayList<>();

        Map<String, Object> enchantment = new HashMap<>();
        enchantment.put("name", "Sharpness");
        List<String> lore = new ArrayList<>();
        lore.add("Increases melee damage");
        enchantment.put("description", lore);
        enchantment.put("level", 3);
        enchantments.add(enchantment);

        enchantment = new HashMap<>();
        enchantment.put("name", "Fire Aspect");
        lore = new ArrayList<>();
        lore.add("Sets targets on fire");
        enchantment.put("description", lore);
        enchantment.put("level", 2);
        enchantments.add(enchantment);

        return enchantments;
    }
}
