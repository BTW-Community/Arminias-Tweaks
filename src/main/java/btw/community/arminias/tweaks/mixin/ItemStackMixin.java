package btw.community.arminias.tweaks.mixin;


import btw.community.arminias.tweaks.ActionLogger;
import btw.community.arminias.tweaks.LoggingSupplier;
import btw.community.arminias.tweaks.TweaksAddon;
import btw.item.items.PlaceAsBlockItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements LoggingSupplier {
    @Unique
    private final Supplier<String> displayNameGetter = this::getDisplayName;
    @Shadow public abstract String getDisplayName();

    @Shadow public abstract Item getItem();

    @Inject(method = "tryPlaceItemIntoWorld", at = @At(value = "RETURN"))
    private void tryPlaceItemIntoWorldMixin(EntityPlayer par1EntityPlayer, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, CallbackInfoReturnable<Boolean> cir) {
        if (!world.isRemote && TweaksAddon.DO_LOGGING_OF_ACTIONS) {
            if (getItem() instanceof PlaceAsBlockItem) ActionLogger.logAction(par1EntityPlayer.dimension,"- place: %s %s placing %s at %s %s %s", par1EntityPlayer.getEntityName(), cir.getReturnValue() ? "succeeded" : "failed", displayNameGetter, i, j, k);
            else ActionLogger.logAction(par1EntityPlayer.dimension,"- use: %s used %s at %s %s %s", par1EntityPlayer.getEntityName(), displayNameGetter, i, j, k);
        }
    }

    @Inject(method = "useItemRightClick", at = @At(value = "HEAD"))
    private void useItemRightClickMixin(World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
        if (!world.isRemote && TweaksAddon.DO_LOGGING_OF_ACTIONS) {
            ActionLogger.logAction(player.dimension,"- use: %s right-clicked %s at %s %s %s", player.getEntityName(), displayNameGetter, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

    @Override
    public Supplier<String> tweaks$getLoggingSupplier() {
        return displayNameGetter;
    }
}
