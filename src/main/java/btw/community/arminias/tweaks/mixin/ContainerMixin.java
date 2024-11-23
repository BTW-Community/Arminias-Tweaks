package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.ActionLogger;
import btw.community.arminias.tweaks.LoggingSupplier;
import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Container.class)
public class ContainerMixin {

    @Unique
    private final String className = this.getClass().getSimpleName();

    @Inject(method = "slotClick", at = @At("RETURN"))
    public void slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer, CallbackInfoReturnable<ItemStack> cir) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && !par4EntityPlayer.worldObj.isRemote && cir.getReturnValue() != null) {
            ActionLogger.logAction(par4EntityPlayer.dimension,"- slot: %s clicked slot %s with %s (%s) in %s, player at %s %s %s", par4EntityPlayer.getEntityName(), par1, ((LoggingSupplier) (Object) cir.getReturnValue()).tweaks$getLoggingSupplier(), cir.getReturnValue().stackSize, className, (int) par4EntityPlayer.posX, (int) par4EntityPlayer.posY, (int) par4EntityPlayer.posZ);
        }
    }
}
