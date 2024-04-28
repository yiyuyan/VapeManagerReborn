package cn.ksmcbrigade.vmr.module;

import cn.ksmcbrigade.vmr.uitls.ModuleUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.io.File;

public class Module {
    public final String name;
    public boolean enabled = false;
    public int key = -1;
    private Config config;

    public Module(String name, boolean enabled, int key, @Nullable Config config,boolean nameConfig){
        this.name = name;
        this.enabled = enabled;
        this.key = key;
        this.config = config;
        if(config!=null && nameConfig){
            this.config.file = new File(this.name);
        }
    }

    public Module(String name, boolean enabled, int key, Config config){
        this(name,enabled,key,config,true);
    }

    public Module(String name, boolean enabled, int key){
        this(name,enabled,key,null,false);
    }

    public Module(String name, boolean enabled){
        this(name,enabled,-1,null,false);
    }

    public Module(String name){
        this(name,false,-1,null,false);
    }

    public void setEnabled(boolean enabled) throws Exception {
        this.enabled = enabled;
        Minecraft MC = Minecraft.getInstance();
        if(enabled){
            enabled(MC);
        }
        else{
            disabled(MC);
        }
        ModuleUtils.save();
    }

    public String getName(){return I18n.get(this.name);}

    public int getKey() {return key;}

    public void setKey(int key) {this.key = key;}

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public int length(){
        return getName().length();
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", enabled=" + enabled +
                ", key=" + key +
                ", config=" + config +
                '}';
    }

    public void enabled(Minecraft MC) throws Exception{}

    public void render() throws Exception{}
    public void renderGame(GuiGraphics pose) throws Exception{}
    public void keyInput(int key, boolean screen) throws Exception{}

    public JsonObject screenshot(JsonObject data) throws Exception{return data;}

    public void clientTick(Minecraft MC) throws Exception{}
    public void worldTick(Minecraft MC,@Nullable Level world) throws Exception{}
    public void playerTick(Minecraft MC,@Nullable Player player) throws Exception{}
    public void screenTick(Minecraft MC) throws Exception{}

    public void disabled(Minecraft MC) throws Exception{}

    public void MCClose(Minecraft MC) throws Exception{}
}
