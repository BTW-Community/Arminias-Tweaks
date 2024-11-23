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

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase {
    public EntityPlayerMixin(World par1World) {
        super(par1World);
    }

    @Shadow public abstract String getEntityName();

    @Inject(method = "dropPlayerItemWithRandomChoice", at = @At(value = "RETURN", ordinal = 2))
    private void dropPlayerItemWithRandomChoiceMixin(ItemStack par1ItemStack, boolean par2, CallbackInfoReturnable<EntityItem> cir) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && !worldObj.isRemote && cir.getReturnValue() != null) {
            EntityItem returnValue = cir.getReturnValue();
            ActionLogger.logAction(dimension,"- drop: %s dropped %s at %s %s %s", this.getEntityName(), ((LoggingSupplier) (Object) returnValue.getEntityItem()).tweaks$getLoggingSupplier(), (int) returnValue.posX, (int) returnValue.posY, (int) returnValue.posZ);
        }
    }

    @Inject(method = "interactWith", at = @At(value = "RETURN"))
    private void interactWithMixin(Entity par1Entity, CallbackInfoReturnable<Boolean> cir) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && !worldObj.isRemote && cir.getReturnValue()) {
            ActionLogger.logAction(dimension,"- interact: %s interacted with %s at %s %s %s", this.getEntityName(), par1Entity.getEntityName(), (int) par1Entity.posX, (int) par1Entity.posY, (int) par1Entity.posZ);
        }
    }

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addExhaustion(F)V"))
    private void attackTargetEntityWithCurrentItemMixin(Entity par1Entity, CallbackInfo ci) {
        if (TweaksAddon.DO_LOGGING_OF_ACTIONS && !worldObj.isRemote) {
            ActionLogger.logAction(dimension,"- attack: %s attacked %s at %s %s %s", this.getEntityName(), par1Entity.getEntityName(), (int) par1Entity.posX, (int) par1Entity.posY, (int) par1Entity.posZ);
        }
    }
}
