package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerMP.class)
public class EntityPlayerMPMixin {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityPlayerMP;initialInvulnerability:I"))
    private void setInitialInvulnerability(EntityPlayerMP instance, int value) {
        if (TweaksAddon.JOIN_INVULNERABILITY_OFF) {
            instance.initialInvulnerability = 0;
        }
    }

    @Inject(method = "canCommandSenderUseCommand", at = @At("RETURN"), cancellable = true)
    private void canCommandSenderUseCommandMixin(int par1, String par2Str, CallbackInfoReturnable<Boolean> cir) {
        if (par2Str.equals("global") || par2Str.equals("g")) {
            cir.setReturnValue(true);
        }
    }
}
