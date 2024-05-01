package cn.ksmcbrigade.vmr.mixin;

import cn.ksmcbrigade.vmr.VapeManagerReborn;
import cn.ksmcbrigade.vmr.guis.KeyboardSetting;
import cn.ksmcbrigade.vmr.module.Config;
import cn.ksmcbrigade.vmr.module.Module;
import cn.ksmcbrigade.vmr.uitls.JNAUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mixin(AccessibilityOptionsScreen.class)
public abstract class AccessibilityOptionsScreenMixin extends SimpleOptionsSubScreen {
    public AccessibilityOptionsScreenMixin(Screen p_232763_, Options p_232764_, Component p_232765_, OptionInstance<?>[] p_232766_) {
        super(p_232763_, p_232764_, p_232765_, p_232766_);
    }

    @Inject(method = "options",at = @At("RETURN"), cancellable = true)
    private static void getOptions(Options p_232691_, CallbackInfoReturnable<OptionInstance<?>[]> cir){
        ArrayList<OptionInstance<?>> hackOptions = new ArrayList<>();
        Minecraft MC = Minecraft.getInstance();
        for(Module module:VapeManagerReborn.modules){
            hackOptions.add(OptionInstance.createBoolean(module.getName()+" ("+module.key+")",OptionInstance.noTooltip(),module.enabled,(zt) -> {
                try {
                    boolean shift = JNAUtils.isPressed(KeyEvent.VK_SHIFT);
                    boolean ctrl = JNAUtils.isPressed(KeyEvent.VK_CONTROL);
                    if(!shift && !ctrl){
                        module.setEnabled(zt.booleanValue());
                    }
                    else if(shift){
                        MC.setScreen(new KeyboardSetting(module));
                    }
                    else {
                        if(module.getConfig()!=null){
                            File pathFile = new File(Config.configDir,module.getConfig().file.getPath()+".json");
                            Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","notepad.exe",pathFile.getPath()});
                        }
                        module.setEnabled(zt.booleanValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }
        hackOptions.add(OptionInstance.createBoolean("Module configuration's key: "+VapeManagerReborn.ScreenKey,OptionInstance.noTooltip(),true,(zt) -> {
            try {
                MC.setScreen(new KeyboardSetting(true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        hackOptions.addAll(List.of(cir.getReturnValue()));
        cir.setReturnValue(hackOptions.toArray(new OptionInstance[0]));
    }
}
