package btw.community.arminias.tweaks.mixin;


import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.EntityDragon;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityDragon.class)
public abstract class EntityDragonMixin extends EntityLiving {
    public EntityDragonMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "createEnderPortal", at = @At("HEAD"), cancellable = true)
    private void createEnderPortalMixin(int par1, int par2, CallbackInfo ci) {
        if (TweaksAddon.TWO_DRAGONS_IN_END && this.worldObj.countEntitiesThatApplyToSpawnCap(EntityDragon.class) > 1) {
            ci.cancel();
        }
    }
}
