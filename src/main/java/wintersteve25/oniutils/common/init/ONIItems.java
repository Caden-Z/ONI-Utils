package wintersteve25.oniutils.common.init;

import net.minecraft.world.item.Item;
import wintersteve25.oniutils.common.contents.base.ONIBaseItem;
import wintersteve25.oniutils.common.contents.base.ONIBaseItemArmor;
import wintersteve25.oniutils.common.contents.base.ONIIRegistryObject;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import wintersteve25.oniutils.common.contents.modules.items.gadgets.ONIToolItems;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;

import java.util.ArrayList;
import java.util.List;

public class ONIItems {

    public static final ONIBaseItemArmor GAS_GOGGLES = ONIToolItems.GAS_GOGGLES.build();
    public static final ONIBaseItem WIRE_CUTTER = ONIToolItems.WIRE_CUTTER.build();

    public static final List<ONIIRegistryObject<Item>> itemRegistryList = new ArrayList<>();

    public static void register() {
        registerModifications();
        GAS_GOGGLES.init(itemRegistryList);
        WIRE_CUTTER.init(itemRegistryList);
    }

    private static void registerModifications() {
        for (EnumModifications modifications : EnumModifications.values()) {
            for (int i = 1; i < modifications.getTiers()+1; i++) {
                ONIModificationItem.create("Modification " + modifications.getName() + " Tier " + i, modifications.getColor(), modifications.getBonusEachTier() * i, modifications, modifications.getTooltips());
            }
        }
    }
}