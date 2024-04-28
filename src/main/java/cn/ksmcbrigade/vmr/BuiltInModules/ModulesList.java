package cn.ksmcbrigade.vmr.BuiltInModules;

import cn.ksmcbrigade.vmr.uitls.ModuleUtils;
import cn.ksmcbrigade.vmr.module.Config;
import cn.ksmcbrigade.vmr.module.Module;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ModulesList extends Module {
    public ModulesList() throws IOException {
        super("ModulesList",true);
        JsonObject data = new JsonObject();
        data.addProperty("color",Color.WHITE.getRGB());
        this.setConfig(new Config(new File(this.getName()),data));
    }

    @Override
    public void enabled(Minecraft MC) throws Exception {
        RainbowGui.setColor(0);
    }

    @Override
    public void renderGame(GuiGraphics pose) throws Exception {
        Minecraft MC = Minecraft.getInstance();
        Font font = MC.font;
        Module[] mods = ModuleUtils.getNewShotAll(true);
        for(int i=0;i<mods.length;i++){
            JsonElement color;
            if(!ModuleUtils.enabled("RainbowGui")){
                color = getConfig().get("color");
            }
            else{
                color = RainbowGui.getColor().get("c");
            }
            /*System.out.println(color!=null);
            if(color!=null){
                System.out.println(color.getAsInt());
            }*/
            pose.drawString(font,mods[i].getName(),MC.getWindow().getGuiScaledWidth()-font.width(mods[i].getName())-2, 2 + i * font.lineHeight,color==null?Color.WHITE.getRGB():color.getAsInt());
        }
        //System.out.println("module enabled!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
