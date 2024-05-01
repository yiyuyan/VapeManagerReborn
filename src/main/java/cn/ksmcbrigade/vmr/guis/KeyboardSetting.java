package cn.ksmcbrigade.vmr.guis;

import cn.ksmcbrigade.vmr.VapeManagerReborn;
import cn.ksmcbrigade.vmr.module.Module;
import cn.ksmcbrigade.vmr.uitls.JNAUtils;
import cn.ksmcbrigade.vmr.uitls.ModuleUtils;
import cn.ksmcbrigade.vmr.uitls.OtherUtils;
import com.mojang.blaze3d.platform.InputConstants;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class KeyboardSetting extends Screen {

    public boolean n = false;
    public Module module;

    public KeyboardSetting(Module module) {
        super(Component.nullToEmpty("Press anything key to setting the module's key: "+module.getName()));
        this.module = module;
        if(JNAUtils.JNAInstance.INSTANCE!=null){
            WinDef.POINT pos = new WinDef.POINT();
            JNAUtils.JNAInstance.INSTANCE.GetCursorPos(pos);
            JNAUtils.JNAInstance.INSTANCE.mouse_event(KeyEvent.VK_SHIFT,pos.x,pos.y,2,0);
        }
    }

    public KeyboardSetting(boolean n){
        super(Component.nullToEmpty("Press anything key to setting the module configuration's key"));
        this.n = n;
        if(JNAUtils.JNAInstance.INSTANCE!=null){
            WinDef.POINT pos = new WinDef.POINT();
            JNAUtils.JNAInstance.INSTANCE.GetCursorPos(pos);
            JNAUtils.JNAInstance.INSTANCE.mouse_event(KeyEvent.VK_SHIFT,pos.x,pos.y,2,0);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_) {
        super.render(p_281549_, p_281550_, p_282878_, p_282465_);
        Minecraft MC = Minecraft.getInstance();
        p_281549_.drawCenteredString(MC.font,this.title,p_281549_.guiWidth()/2,p_281549_.guiHeight()/2-MC.font.lineHeight,-1);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
        super.keyPressed(p_96552_,p_96553_,p_96554_);
        int key = OtherUtils.convertGLFWKeyToVKKey(p_96552_);
        if(key!=KeyEvent.VK_UNDEFINED && !n){
                try {
                    module.setKey(key);
                    ModuleUtils.saveKeys();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        if(n){
            VapeManagerReborn.ScreenKey = p_96552_;
            VapeManagerReborn.screenKey.setKeyModifierAndCode(VapeManagerReborn.screenKey.getDefaultKeyModifier(), InputConstants.Type.KEYSYM.getOrCreate(VapeManagerReborn.ScreenKey));
        }
        this.onClose();
        return true;
    }
}
