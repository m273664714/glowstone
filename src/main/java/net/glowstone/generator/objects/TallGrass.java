package net.glowstone.generator.objects;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.LongGrass;

import java.util.Random;

public class TallGrass implements TerrainObject {

    private final LongGrass grassType;

    public TallGrass(LongGrass grassType) {
        this.grassType = grassType;
    }

    @Override
    public boolean generate(World world, Random random, int sourceX, int sourceY, int sourceZ) {
        Block thisBlock;
        do {
            thisBlock = world.getBlockAt(sourceX, sourceY, sourceZ);
            sourceY--;
        } while ((thisBlock.isEmpty() || thisBlock.getType() == Material.LEGACY_LEAVES) && sourceY > 0); // TODO: 1.13
        sourceY++;
        boolean succeeded = false;
        for (int i = 0; i < 128; i++) {
            int x = sourceX + random.nextInt(8) - random.nextInt(8);
            int z = sourceZ + random.nextInt(8) - random.nextInt(8);
            int y = sourceY + random.nextInt(4) - random.nextInt(4);

            Block block = world.getBlockAt(x, y, z);
            Material blockTypeBelow = block.getRelative(BlockFace.DOWN).getType();
            if (y < 255 && block.getType() == Material.AIR && (
                    blockTypeBelow == Material.GRASS_BLOCK || blockTypeBelow == Material.DIRT)) {
                BlockState state = block.getState();
                // TODO: 1.13 grass types
                state.setType(Material.TALL_GRASS);
                state.setData(grassType);
                state.update(true);
                succeeded = true;
            }
        }
        return succeeded;
    }
}
