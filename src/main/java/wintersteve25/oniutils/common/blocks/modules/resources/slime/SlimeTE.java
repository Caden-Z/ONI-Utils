package wintersteve25.oniutils.common.blocks.modules.resources.slime;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.germ.api.IGermCapProvider;
import wintersteve25.oniutils.common.blocks.base.ONIBaseTE;
import wintersteve25.oniutils.common.capability.germ.GermCapability;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.capability.germ.api.GermStack;
import wintersteve25.oniutils.common.capability.germ.api.IGerms;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SlimeTE extends ONIBaseTE implements ITickableTileEntity, IGermCapProvider {

    private GermStack germHandler = new GermStack();
    private LazyOptional<IGerms> lazyOptional =  LazyOptional.of(() -> germHandler);
    private int germDupTime = ONIConfig.GERM_DUP_SPEED.get();

    public SlimeTE() {
        super(ONIBlocks.SLIME_TE.get());

        germHandler.addGerm(EnumGermTypes.SLIMELUNG, 15000);
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            germDupTime--;
            if (germDupTime < 0) {
                int currentGermAmount = germHandler.getGermAmount();
                germHandler.addGerm(EnumGermTypes.SLIMELUNG, 1);
                germDupTime = ONIConfig.GERM_DUP_SPEED.get();
                ONIUtils.LOGGER.info("germ amount" + currentGermAmount);
            }
        }
    }

    @Override
    protected int totalProgress() {
        return 0;
    }

    @Override
    public void read(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        germHandler.read(p_230337_2_.getCompound("germs"));

        super.read(p_230337_1_, p_230337_2_);
    }

    @Override
    public CompoundNBT write(CompoundNBT p_189515_1_) {
        p_189515_1_.put("germs", germHandler.write());

        return super.write(p_189515_1_);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == GermCapability.GERM_CAPABILITY) {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
