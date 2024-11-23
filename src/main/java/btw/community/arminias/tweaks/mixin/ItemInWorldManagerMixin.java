package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.ActionLogger;
import btw.community.arminias.tweaks.LoggingSupplier;
import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemInWorldManager.class)
public class ItemInWorldManagerMixin {
    @Shadow public EntityPlayerMP thisPlayerMP;

    @Inject(method = "tryHarvestBlock(IIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ItemInWorldManager;removeBlock(III)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void tryHarvestBlockMixin(int i, int j, int k, int iFromSide, CallbackInfoReturnable<Boolean> cir, int blockId) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && blockId != 0) {
            ActionLogger.logAction(thisPlayerMP.dimension,"- break: %s broke %s at %s %s %s", this.thisPlayerMP.getEntityName(), ((LoggingSupplier) Block.blocksList[blockId]).tweaks$getLoggingSupplier(), i, j, k);
        }
    }

    @Inject(method = "survivalTryHarvestBlock(IIII)Z", at = @At(value = "RETURN", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void survivalTryHarvestBlockMixin(int i, int j, int k, int iFromSide, CallbackInfoReturnable<Boolean> cir, int iBlockID, Block block, int iMetadata, ItemStack currentStack, boolean bRemovingBlock, boolean bConvertingBlock) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && (bRemovingBlock || bConvertingBlock)) {
            ActionLogger.logAction(thisPlayerMP.dimension,"- break: %s broke %s at %s %s %s", this.thisPlayerMP.getEntityName(), ((LoggingSupplier) block).tweaks$getLoggingSupplier(), i, j, k);
        }
    }

    @Inject(method = "activateBlockOrUseItem", at = @At(value = "RETURN", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void activateBlockOrUseItemMixin(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10, CallbackInfoReturnable<Boolean> cir, int blockId) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS) {
            ActionLogger.logAction(thisPlayerMP.dimension,"- activate: %s activated %s at %s %s %s", this.thisPlayerMP.getEntityName(), ((LoggingSupplier) Block.blocksList[blockId]).tweaks$getLoggingSupplier(), par4, par5, par6);
        }
    }
}
