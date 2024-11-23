package btw.community.arminias.tweaks.mixin;

import btw.community.arminias.tweaks.TweaksAddon;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NetLoginHandler.class)
public class NetLoginHandlerMixin {
    @Inject(method = "handleServerPing", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TcpConnection;getSocket()Ljava/net/Socket;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void constructStringBuilder(Packet254ServerPing par1Packet254ServerPing, CallbackInfo ci, ServerConfigurationManager var2, StringBuilder instance)
    {
        instance.insert(0, TweaksAddon.ADDON_CHECK_MESSAGE);
        //instance.append(TweaksAddon.ADDON_CHECK_MESSAGE + "§1\u000078\u00001.6.4\u0000A Minecraft Server\u00000\u000020");// + "\u00a8" + this.mcServer.getMOTD() + "\u00a8" + this.mcServer.getConfigurationManager().getCurrentPlayerCount() + "\u00a8" + this.mcServer.getConfigurationManager().getMaxPlayers());
    }
}
