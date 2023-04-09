package me.klb.thehypixelpitsurvival.fixes;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataChecker {
    public static void checkPersistentData(Entity entity){
        PersistentDataContainer entityData = entity.getPersistentDataContainer();

        if (entityData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "Venomed"), PersistentDataType.STRING)){
            entityData.remove(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "Venomed"));
        }
    }
}
