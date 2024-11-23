package btw.community.arminias.tweaks.mixin;

import btw.BTWAddon;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.impl.entrypoint.EntrypointContainerImpl;
import net.minecraft.src.StringTranslate;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.regex.Pattern;

@Environment(EnvType.SERVER)
@Mixin(StringTranslate.class)
public class StringTranslateMixin {
    @Unique
    private static Field typeField;

    @Shadow private Map languageList;

    @Shadow @Final private static Pattern field_111053_a;

    @Shadow @Final private static Splitter field_135065_b;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypointContainers("btw:addon", BTWAddon.class).forEach(this::initLang);
    }

    @Unique
    private void initLang(EntrypointContainer<BTWAddon> entrypointContainer) {
        try {
            if (typeField == null) {
                try {
                    typeField = EntrypointContainerImpl.class.getDeclaredField("type");
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                typeField.setAccessible(true);
            }
            Class<?> type = (Class<?>) typeField.get(entrypointContainer);
            System.out.println("(Arminias' Tweaks) Searching language file for " + entrypointContainer.getProvider().getMetadata().getId());
            InputStream var1 = type.getResourceAsStream("/assets/" + entrypointContainer.getProvider().getMetadata().getId() + "/lang/en_US.lang");
            if (var1 != null) {
                System.out.println("(Arminias' Tweaks) Found language file found for " + entrypointContainer.getProvider().getMetadata().getId());
                for (String var3 : IOUtils.readLines(var1, Charsets.UTF_8)) {
                    String[] var4;
                    if (var3.isEmpty() || var3.charAt(0) == '#' || (var4 = Iterables.toArray(field_135065_b.split(var3), String.class)) == null || var4.length != 2)
                        continue;
                    String var5 = var4[0];
                    String var6 = field_111053_a.matcher(var4[1]).replaceAll("%$1s");
                    this.languageList.put(var5, var6);
                }
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
