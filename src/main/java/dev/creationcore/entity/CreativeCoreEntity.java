package dev.creationcore.entity;

import dev.creationcore.registry.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class CreativeCoreEntity extends ItemEntity {
    private static final double CONVERSION_RADIUS = 5.0D;
    private static final double CONVERSION_RADIUS_SQR = CONVERSION_RADIUS * CONVERSION_RADIUS;

    public CreativeCoreEntity(EntityType<? extends ItemEntity> type, Level level) {
        super(type, level);
        initialize();
    }

    public CreativeCoreEntity(Level level, double x, double y, double z) {
        this(dev.creationcore.registry.ModEntities.CREATIVE_CORE.get(), level);
        setPos(x, y, z);
        setItem(new ItemStack(ModItems.CREATIVE_CORE.get()));
        initialize();
    }

    private void initialize() {
        setNoGravity(true);
        setInvulnerable(true);
        setGlowingTag(true);
        setNeverPickUp();
        setUnlimitedLifetime();
        setDeltaMovement(0, 0, 0);
    }

    @Override
    public void tick() {
        super.tick();

        // The entity form is only a persistent, highlighted world marker. It must not
        // behave like a normal dropped item until a player approaches it.
        setNoGravity(true);
        setInvulnerable(true);
        setGlowingTag(true);
        setNeverPickUp();
        setUnlimitedLifetime();
        setDeltaMovement(0, 0, 0);

        if (!level().isClientSide) {
            boolean playerNearby = !level().getEntitiesOfClass(
                    Player.class,
                    getBoundingBox().inflate(CONVERSION_RADIUS),
                    player -> !player.isSpectator() && player.distanceToSqr(this) <= CONVERSION_RADIUS_SQR
            ).isEmpty();

            if (playerNearby) {
                convertToDroppedItem();
            }
        }
    }

    private void convertToDroppedItem() {
        ItemEntity dropped = new ItemEntity(level(), getX(), getY(), getZ(),
                new ItemStack(ModItems.CREATIVE_CORE.get()));
        dropped.setDeltaMovement(0, 0, 0);
        dropped.setNoPickUpDelay();

        // Only remove the persistent core after the replacement item was accepted by
        // the level, preventing the core from disappearing if entity spawning fails.
        if (level().addFreshEntity(dropped)) {
            discard();
        }
    }

    /**
     * Eye-of-Ender-like selection behaviour: the entity form cannot be targeted by
     * the player's crosshair or normal melee interaction.
     */
    @Override
    public boolean isPickable() {
        return false;
    }

    /** Prevent arrows, tridents and other projectile hit tests from selecting it. */
    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }

    /** Stop the player attack pipeline before damage/interaction code is entered. */
    @Override
    public boolean isAttackable() {
        return false;
    }

    /** Additional guard for attack interactions from players or modded callers. */
    @Override
    public boolean skipAttackInteraction(Entity entity) {
        return true;
    }

    /** Final damage guard for non-standard/modded damage paths. */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }
}
