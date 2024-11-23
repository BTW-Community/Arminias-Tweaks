package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.TweaksAddon;
import btw.entity.UrnEntity;
import net.minecraft.src.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(UrnEntity.class)
public class UrnEntityMixin {
    @Redirect(method = "attemptToCreateWither", at = @At(value = "FIELD", target = "Lnet/minecraft/src/WorldProvider;dimensionId:I"))
    private static int redirectDimensionId(WorldProvider instance) {
        if (TweaksAddon.WITHER_SPAWNABLE_IN_END && instance.dimensionId == 1) {
            return 0;
        }
        return instance.dimensionId;
    }
}
