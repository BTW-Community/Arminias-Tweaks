package btw.community.arminias.tweaks.mixin;


import btw.community.arminias.tweaks.ActionLogger;
import btw.community.arminias.tweaks.LoggingSupplier;
import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ContainerChest.class)
public class ContainerChestMixin {
    @Inject(method = "transferStackInSlot", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void transferStackInSlotMixin(EntityPlayer player, int par2, CallbackInfoReturnable<ItemStack> cir, ItemStack itemStack) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && !player.worldObj.isRemote && itemStack != null) {
            ActionLogger.logAction(player.dimension,"- slot: %s stack-moved %s from chest, player at %s %s %s", player.getEntityName(), ((LoggingSupplier) (Object) itemStack).tweaks$getLoggingSupplier(), (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

    @Inject(method = "onContainerClosed", at = @At("RETURN"))
    private void onContainerClosedMixin(EntityPlayer player, CallbackInfo ci) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && !player.worldObj.isRemote) {
            ActionLogger.logAction(player.dimension,"- close: %s closed chest, player at %s %s %s", player.getEntityName(), (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

}
