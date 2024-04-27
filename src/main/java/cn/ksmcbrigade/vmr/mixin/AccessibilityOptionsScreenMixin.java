package cn.ksmcbrigade.vmr.mixin;

import cn.ksmcbrigade.vmr.VapeManagerReborn;
import cn.ksmcbrigade.vmr.module.Module;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {
    @Inject(method = "options",at = @At("RETURN"), cancellable = true)
    private static void getOptions(Options p_232691_, CallbackInfoReturnable<OptionInstance<?>[]> cir){
        ArrayList<OptionInstance<?>> hackOptions = new ArrayList<>();
        for(Module module:VapeManagerReborn.modules){
            hackOptions.add(OptionInstance.createBoolean(module.name,OptionInstance.noTooltip(),module.enabled,(zt) -> {
                try {
                    module.setEnabled(zt.booleanValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }
        hackOptions.addAll(List.of(cir.getReturnValue()));
        cir.setReturnValue(hackOptions.toArray(new OptionInstance[0]));
    }
}
