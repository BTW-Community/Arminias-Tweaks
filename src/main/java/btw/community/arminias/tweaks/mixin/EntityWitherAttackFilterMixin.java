package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityDragon;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.EntityWitherAttackFilter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityWitherAttackFilter.class)
public class EntityWitherAttackFilterMixin {
    @Inject(method = "isEntityApplicable", at = @At("RETURN"), cancellable = true)
    private void noEndermenTargetInEnd(Entity par1, CallbackInfoReturnable<Boolean> cir) {
        if (TweaksAddon.WITHER_SPAWNABLE_IN_END && par1.dimension == 1 && (par1 instanceof EntityEnderman || par1 instanceof EntityDragon)) {
            cir.setReturnValue(false);
        }
    }
}
