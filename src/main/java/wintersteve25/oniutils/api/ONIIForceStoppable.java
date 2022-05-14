package wintersteve25.oniutils.api;

/**
 * Should be implemented on a {@link net.minecraft.world.level.block.entity.BlockEntity}
 */
public interface ONIIForceStoppable extends ONIIWorkable {
    boolean getForceStopped();

    void setForceStopped(boolean forceStopped);

    boolean isInverted();

    void toggleInverted();
}
