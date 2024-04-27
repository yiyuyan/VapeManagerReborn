package cn.ksmcbrigade.vmr.mixin;

import cn.ksmcbrigade.vmr.VapeManagerReborn;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "close",at = @At("HEAD"))
    public void close(CallbackInfo ci) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        Minecraft MC = Minecraft.getInstance();
        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                module.MCClose(MC);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Inject(method = "crash",at = @At("HEAD"))
    private static void crash(CallbackInfo ci) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        Minecraft MC = Minecraft.getInstance();
        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                module.MCClose(MC);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
