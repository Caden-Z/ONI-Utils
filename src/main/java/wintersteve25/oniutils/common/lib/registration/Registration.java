package wintersteve25.oniutils.common.lib.registration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.chunk.germ.GermsCapability;
import wintersteve25.oniutils.common.chunk.germ.GermEventsHandler;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.lib.helper.ONIBlocksHelper;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ONIUtils.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ONIUtils.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        ONIBlocks.register();
        ONIBlocksHelper.register();

        ONIUtils.LOGGER.info("ONIUtils Registration Completed");
    }
}
