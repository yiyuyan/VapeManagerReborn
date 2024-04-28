package cn.ksmcbrigade.vmr.uitls;

import cn.ksmcbrigade.vmr.VapeManagerReborn;
import cn.ksmcbrigade.vmr.module.Module;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.components.EditBox;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ModuleUtils {
    public static boolean has(String name){
        for(Module module: VapeManagerReborn.modules){
            if(module.name.equals(name)){
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static Module get(String name){
        for(Module module:VapeManagerReborn.modules){
            if(module.name.equals(name)){
                return module;
            }
        }
        return null;
    }

    public static void set(String name,boolean enabled) throws Exception {
        for(Module module:VapeManagerReborn.modules){
            if(module.name.equals(name)){
                module.setEnabled(enabled);
                break;
            }
        }
    }

    public static boolean enabled(String name){
        for(Module module:VapeManagerReborn.modules){
            if(module.name.equals(name) && module.enabled){
                return true;
            }
        }
        return false;
    }

    public static void add(Module module){
        VapeManagerReborn.modules.add(module);
    }

    public static void del(Module module){
        VapeManagerReborn.modules.remove(module);
    }

    public static void del(int module){
        VapeManagerReborn.modules.remove(module);
    }

    public static ArrayList<Module> getAll(boolean enabled){
        return enabled?new ArrayList<>(VapeManagerReborn.modules.stream().filter(m -> m.enabled).toList()):VapeManagerReborn.modules;
    }

    public static Module[] getNewShotAll(boolean enabled){
        ArrayList<Module> modules = new ArrayList<>();
        if(enabled){
            modules.addAll(VapeManagerReborn.modules.stream().filter(m -> m.enabled).toList());
        }
        else{
            modules.addAll(VapeManagerReborn.modules);
        }
        Module[] newModules = modules.toArray(new Module[0]);
        Arrays.sort(newModules, Comparator.comparing(Module::length).reversed());
        return newModules;
    }

    public static void save() throws IOException{
        JsonArray modules = new JsonArray();
        VapeManagerReborn.modules.stream().filter(m -> m.enabled).toList().forEach(m -> modules.add(m.name));
        Files.writeString(VapeManagerReborn.configFile.toPath(),modules.toString());
    }

    public static void saveKeys() throws IOException{
        JsonObject keysMap = new JsonObject();
        keysMap.addProperty("NModuleConfiguration",VapeManagerReborn.ScreenKey);
        VapeManagerReborn.modules.forEach(e -> keysMap.addProperty(e.name,e.key));
        Files.writeString(VapeManagerReborn.KeyboardConfigFile.toPath(),keysMap.toString());
    }
}
