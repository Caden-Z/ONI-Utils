package wintersteve25.oniutils.api.functional;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface IPlacementCondition extends BiPredicate<BlockPlaceContext, BlockState> {
}
