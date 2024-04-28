package cn.ksmcbrigade.vmr;

import cn.ksmcbrigade.vmr.BuiltInModules.MEMZ;
import cn.ksmcbrigade.vmr.BuiltInModules.ModulesList;
import cn.ksmcbrigade.vmr.BuiltInModules.RainbowGui;
import cn.ksmcbrigade.vmr.module.Module;
import cn.ksmcbrigade.vmr.uitls.ModuleUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

@Mod(VapeManagerReborn.MODID)
@Mod.EventBusSubscriber
public class VapeManagerReborn {

    public static boolean init = false;

    public static File configFile = new File("config/vmr/enabledList.json");
    public static File KeyboardConfigFile = new File("config/vmr/keys.json");
    public static ArrayList<Module> modules = new ArrayList<>();
    public static final String MODID = "vmr";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static int ScreenKey = KeyEvent.VK_V;

    public static KeyMapping screenKey = new KeyMapping("options.accessibility.title", GLFW.GLFW_KEY_V,"key.categories.gameplay");

    public static ModulesList listModule;
    public static RainbowGui rainbow;
    public static MEMZ memz = new MEMZ();  //only VFX,this is a egg. use win api.

    static {
        try {
            listModule = new ModulesList();
            rainbow = new RainbowGui();

        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public VapeManagerReborn() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        if(Boolean.parseBoolean(System.getProperty("java.awt.headless"))){
            System.setProperty("java.awt.headless","false");
        }
        if(!init){
            init();
        }
    }

    public static void init() throws IOException {

        if(Boolean.parseBoolean(System.getProperty("java.awt.headless"))){
            System.setProperty("java.awt.headless","false");
        }

        new File("config").mkdirs();
        new File("config/vmr").mkdirs();
        new File("config/vmr/modules").mkdirs();

        ModuleUtils.add(listModule);
        ModuleUtils.add(rainbow);
        ModuleUtils.add(memz);

        if(!configFile.exists()){
            Files.writeString(configFile.toPath(),"[]");
        }

        JsonArray array = JsonParser.parseString(Files.readString(configFile.toPath())).getAsJsonArray();
        array.forEach(k -> {
            Module module = ModuleUtils.get(k.getAsString());
            if(module!=null){
                try {
                    module.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ModuleUtils.save();

        if(!KeyboardConfigFile.exists()){
            JsonObject keysMap = new JsonObject();
            keysMap.addProperty("NModuleConfiguration",ScreenKey);
            modules.forEach(e -> keysMap.addProperty(e.name,e.key));
            Files.writeString(KeyboardConfigFile.toPath(),keysMap.toString());
        }

        JsonObject keysMap = JsonParser.parseString(Files.readString(KeyboardConfigFile.toPath())).getAsJsonObject();

        ScreenKey = keysMap.get("NModuleConfiguration").getAsInt();

        keysMap.keySet().forEach(k -> {
            Module module = ModuleUtils.get(k);
            if(module!=null){
                module.setKey(keysMap.get(k).getAsInt());
            }
        });

        ModuleUtils.saveKeys();

        init = true;

        LOGGER.info("Vape Manager Reborn is done!");
    }

    @SubscribeEvent
    public void registerKeys(RegisterKeyMappingsEvent event){
        event.register(screenKey);
    }
}
