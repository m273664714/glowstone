package net.glowstone.entity;

import com.google.common.collect.Sets;
import org.bukkit.entity.EntityType;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertFalse;

public class EntityNetworkUtilTest {

    // Entities that have no network IDs
    private static final Set<EntityType> EXCLUSIONS = Sets.immutableEnumSet(
            EntityType.PAINTING,            // Paintings are spawned using SpawnPaintingMessage
            EntityType.EXPERIENCE_ORB,      // Exp Orbs are spawned using SpawnXpOrb
            EntityType.ARROW,               // Arrows use the same ID as TIPPED_ARROW
            EntityType.MINECART_CHEST,      // Minecarts all use the same ID (MINECART)
            EntityType.MINECART_COMMAND,
            EntityType.MINECART_FURNACE,
            EntityType.MINECART_HOPPER,
            EntityType.MINECART_MOB_SPAWNER,
            EntityType.MINECART_TNT,
            EntityType.LIGHTNING,           // Lightning is spawned using SpawnLightningMessage
            EntityType.PLAYER,              // Players are spawned using SpawnPlayerMessage
            EntityType.UNKNOWN
    );

    @Test
    public void testAllEntitiesCovered() {
        EntityType[] allTypes = EntityType.values();
        for (EntityType type : allTypes) {
            if (EXCLUSIONS.contains(type)) {
                continue;
            }

            assertFalse("Entity type '" + type + "' has no registered network ID.",
                    EntityNetworkUtil.getMobId(type) == -1
                            && EntityNetworkUtil.getObjectId(type) == -1);
        }
    }
}
