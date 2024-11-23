package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.BiomeEndDecorator;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.EntityDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeEndDecorator.class)
public abstract class BiomeEndDecoratorMixin extends BiomeDecorator {
    public BiomeEndDecoratorMixin(BiomeGenBase par1BiomeGenBase) {
        super(par1BiomeGenBase);
    }

    @Inject(method = "decorate", at = @At("RETURN"))
    private void decorateMixin(CallbackInfo ci) {
        if (TweaksAddon.TWO_DRAGONS_IN_END) {
            if (this.chunk_X == 0 && this.chunk_Z == 0) {
                EntityDragon entityDragon = new EntityDragon(this.currentWorld);
                entityDragon.setLocationAndAngles(0.0, 128.0, 0.0, this.randomGenerator.nextFloat() * 360.0f, 0.0f);
                this.currentWorld.spawnEntityInWorld(entityDragon);
            }
        }
    }
}
