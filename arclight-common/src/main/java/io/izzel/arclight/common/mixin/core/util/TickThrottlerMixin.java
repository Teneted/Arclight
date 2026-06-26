package io.izzel.arclight.common.mixin.core.util;

import io.izzel.arclight.common.bridge.core.util.TickThrottlerBridge;
import net.minecraft.util.TickThrottler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(TickThrottler.class)
public class TickThrottlerMixin implements TickThrottlerBridge {

    @Shadow
    @Final
    private int incrementStep;
    @Shadow
    @Final
    private int threshold;
    @Unique
    private final AtomicInteger arclight$count = new AtomicInteger(); // CraftBukkit - multithreaded field

    /**
     * @author wdog5734
     * @reason Bukkit
     */
    @Overwrite
    public void increment() {
        this.arclight$count.addAndGet(this.incrementStep); // CraftBukkit - use thread-safe field access instead
    }

    /**
     * @author wdog5734
     * @reason Bukkit
     */
    @Overwrite
    public void tick() {
        // CraftBukkit start
        for (int val; (val = this.arclight$count.get()) > 0 && !arclight$count.compareAndSet(val, val - 1); ) ;
        /* Use thread-safe field access instead
        if (this.count > 0) {
            --this.count;
        }
        */
        // CraftBukkit end
    }

    /**
     * @author wdog5734
     * @reason Bukkit
     */
    @Overwrite
    public boolean isUnderThreshold() {
        // CraftBukkit start - use thread-safe field access instead
        return this.threshold <= 0 || this.arclight$count.get() < this.threshold;
    }

    @Override
    public boolean isIncrementAndUnderThreshold() {
        return isIncrementAndUnderThreshold(this.incrementStep, this.threshold);
    }

    @Override
    public boolean isIncrementAndUnderThreshold(int incrementStep, int threshold) {
        return this.arclight$count.addAndGet(incrementStep) < threshold;
        // CraftBukkit end
    }
}
