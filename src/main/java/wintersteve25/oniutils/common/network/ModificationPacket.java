package wintersteve25.oniutils.common.network;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationGUI;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.function.Supplier;

public class ModificationPacket {

    private final ItemStack mod;
    private final int bonusData;
    private final byte type;

    public ModificationPacket(ItemStack mod, int bonusData, byte type) {
        this.mod = mod;
        this.bonusData = bonusData;
        this.type = type;
    }

    public ModificationPacket(FriendlyByteBuf buffer) {
        this.mod = buffer.readItem();
        this.bonusData = buffer.readInt();
        this.type = buffer.readByte();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItem(mod);
        buffer.writeInt(bonusData);
        buffer.writeByte(type);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            switch (type) {
                case ONIConstants.PacketType.MODIFICATION_GUI:
                    ONIModificationGUI.open(mod, bonusData);
                    break;
                case ONIConstants.PacketType.MODIFICATION_DATA:
                    ONIModificationItem.setBonusDataToItemStack(ctx.get().getSender(), bonusData);
                    break;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
