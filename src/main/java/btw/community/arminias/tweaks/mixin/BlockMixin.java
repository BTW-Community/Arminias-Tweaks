package btw.community.arminias.tweaks.mixin;


import btw.community.arminias.tweaks.LoggingSupplier;
import net.minecraft.src.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Supplier;

@Mixin(Block.class)
public abstract class BlockMixin implements LoggingSupplier {
    @Shadow public abstract String getLocalizedName();

    @Unique
    private final Supplier<String> loggingSupplier = this::getLocalizedName;

    @Override
    public Supplier<String> tweaks$getLoggingSupplier() {
        return loggingSupplier;
    }
}
