package wintersteve25.oniutils.common.utils.helpers;

import mekanism.common.util.WorldUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.blocks.bounding.ONIBoundingBlock;
import wintersteve25.oniutils.common.contents.base.blocks.bounding.ONIBoundingTE;
import wintersteve25.oniutils.common.registries.ONIBlocks;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class MiscHelper {

    public static final int INT_MAX = Integer.MAX_VALUE;
    public static final double ONEPIXEL = 1D / 16;
    public static final Item.Properties DEFAULT_ITEM_PROPERTY = ONIUtils.defaultProperties();

    public static String langToReg(String lang) {
        return lang.toLowerCase().replace(' ', '_').replace('-', '_');
    }

    public static double randomInRange(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public static float randomInRange(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }

    public static int randomInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    /**
     * Method modified from https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/common/util/WorldUtils.java#L537
     */
    public static void makeBoundingBlock(@Nullable LevelAccessor world, BlockPos boundingLocation, BlockPos orig) {
        if (world != null) {
            ONIBoundingBlock boundingBlock = ONIBlocks.Misc.BOUNDING_BLOCK.getBlock();
            BlockState newState = boundingBlock.defaultBlockState();
            world.setBlock(boundingLocation, newState, 3);
            if (!world.isClientSide()) {
                ONIBoundingTE tile = (ONIBoundingTE) WorldUtils.getTileEntity((Class) ONIBoundingTE.class, (BlockGetter) world, boundingLocation);
                if (tile != null) {
                    tile.setMainLocation(orig);
                } else {
                    ONIUtils.LOGGER.warn("Unable to find Bounding Block Tile at: {}", boundingLocation);
                }
            }
        }
    }

    public static <K, V> HashMap<K, V> newHashmap(List<K> keys, List<V> values) {
        HashMap<K, V> map = new HashMap<>();

        for (int i = 0; i < keys.size(); i++) {
            try {
                map.put(keys.get(i), values.get(i));
            } catch (ArrayIndexOutOfBoundsException e) {
                return map;
            }
        }

        return map;
    }

    /**
     * @return returns a new list that contains all the object in the first list that isn't in the second list
     */
    public static <E> List<E> filterList(Collection<E> list, Collection<E> other) {
        return list.stream().filter(obj -> !other.contains(obj)).collect(Collectors.toList());
    }

    public static <T, E> List<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static CompoundTag writeBlockPos(BlockPos pos) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("posX", pos.getX());
        nbt.putInt("posY", pos.getY());
        nbt.putInt("posZ", pos.getZ());
        return nbt;
    }

    public static BlockPos readBlockPos(CompoundTag pos) {
        return new BlockPos(pos.getInt("posX"), pos.getInt("posY"), pos.getInt("posZ"));
    }

    public static boolean chanceHandling(int chance) {
        Random rand = new Random();
        double randN = rand.nextDouble();

        return randN < (double) chance / 100;
    }

    public static boolean chanceHandling(float chance) {
        Random rand = new Random();
        double randN = rand.nextDouble();

        return randN < (double) chance;
    }
}
