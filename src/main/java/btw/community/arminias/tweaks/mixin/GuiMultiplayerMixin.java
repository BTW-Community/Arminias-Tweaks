package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.GuiMultiplayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiMultiplayer.class)
public class GuiMultiplayerMixin {
    @Redirect(method = "func_74017_b", at = @At(value = "NEW", target = "([C)Ljava/lang/String;"))
    private static String redirectStringConstructor(char[] value) {
        return new String(value).replace(TweaksAddon.ADDON_CHECK_MESSAGE, "");
    }
}
