package wintersteve25.oniutils.common.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;
import wintersteve25.oniutils.common.registration.PlayerMovingEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class TriggerPlayerMovePacket {
    private final UUID player;
    private final PlayerMovingEvent.MovementTypes movementTypes;

    public TriggerPlayerMovePacket(UUID player, PlayerMovingEvent.MovementTypes movementTypes) {
        this.player = player;
        this.movementTypes = movementTypes;
    }

    public TriggerPlayerMovePacket(FriendlyByteBuf buffer) {
        this.player = buffer.readUUID();
        this.movementTypes = Enum.valueOf(PlayerMovingEvent.MovementTypes.class, buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(player);
        buffer.writeUtf(movementTypes.toString());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player playerEntity = ctx.get().getSender().getCommandSenderWorld().getPlayerByUUID(player);
            MinecraftForge.EVENT_BUS.post(new PlayerMovingEvent(movementTypes, playerEntity));
        });
        ctx.get().setPacketHandled(true);
    }
}
