package cn.ksmcbrigade.vmr.BuiltInModules;

import cn.ksmcbrigade.vmr.uitls.ModuleUtils;
import cn.ksmcbrigade.vmr.module.Config;
import cn.ksmcbrigade.vmr.module.Module;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class RainbowGui extends Module {

    public static ArrayList<Color> colors;

    protected static int sleep = 300;

    public static int nowS = 300;
    public static int now = 0;

    public static boolean down = false;

    public RainbowGui() throws IOException, IllegalAccessException {
        super("RainbowGui");
        JsonObject data = new JsonObject();
        data.addProperty("sleep",300);
        setConfig(new Config(new File("RainbowGui"),data));
        colors = new ArrayList<>();
        Field[] fields = Color.class.getDeclaredFields();
        for(Field field:fields){
            if(Modifier.isStatic(field.getModifiers()) && field.getType().equals(Color.class)){
                colors.add(((Color)field.get(null)));
            }
        }
        now = colors.size()-1;
    }

    @Override
    public void enabled(Minecraft MC) throws Exception {
        JsonElement sl = getConfig().get("sleep");
        sleep = sl!=null?sl.getAsInt():300;
        nowS = sl!=null?sl.getAsInt():300;
        if(!ModuleUtils.enabled("ModulesList")){
            ModuleUtils.set("ModulesList",true);
        }
    }

    public static JsonObject getColor(){
        JsonObject re = new JsonObject();
        if(now<0){
            down = false;
        }
        if(now==colors.size()){
            now--;
            nowS = sleep;
            down = true;
        }
        else{
            if(nowS!=0){
                nowS--;
            }
            else{
                if(down){
                    now--;
                }
                else{
                    now++;
                }
                nowS = sleep;
            }
        }
        re.addProperty("c",colors.get(now==-1?0:now==colors.size()?(colors.size()-1):now).getRGB());
        if(now==-1){
            now = 0;
            down = false;
        }
        if(now==colors.size()){
            now = colors.size()-1;
            down = true;
        }
        return re;
    }

    public static void setColor(int SColor){
        now = SColor;
        nowS = sleep;
    }
}
