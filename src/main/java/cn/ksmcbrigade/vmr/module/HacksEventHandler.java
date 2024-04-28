package cn.ksmcbrigade.vmr.module;

import cn.ksmcbrigade.vmr.VapeManagerReborn;
import cn.ksmcbrigade.vmr.uitls.JNAUtils;
import cn.ksmcbrigade.vmr.uitls.ModuleUtils;
import cn.ksmcbrigade.vmr.uitls.OtherUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT,modid = VapeManagerReborn.MODID)
public class HacksEventHandler {

    public HacksEventHandler(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void render(TickEvent.RenderTickEvent event) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                module.render();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void render(RenderGuiOverlayEvent.Pre event) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
            //System.out.println("rendering game!!!!!!!!!!!!!!!!!!!!!!");
        }

        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                module.renderGame(event.getGuiGraphics());
                //System.out.println("module !!!!!!!!!!!!!!!!!!!!!!!!!!! :rendering game!!!!!!!!!!!!!!!!!!!!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SubscribeEvent()
    public static void key(InputEvent.Key event) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        if(Boolean.parseBoolean(System.getProperty("java.awt.headless"))){
            System.setProperty("java.awt.headless","false");
        }

        Minecraft MC = Minecraft.getInstance();

        if(JNAUtils.isPressed(VapeManagerReborn.ScreenKey) && MC.screen!=null && !OtherUtils.hasEditBox(MC.screen.getClass())){
            MC.setScreen(new AccessibilityOptionsScreen(MC.screen,MC.options));
        }

        VapeManagerReborn.modules.stream().toList().forEach(module -> {
            try {
                if(module.enabled){
                    module.keyInput(event.getKey(),false);
                }

                if(module.key!=-1 && JNAUtils.isPressed(module.key)){
                    module.setEnabled(!module.enabled);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SubscribeEvent()
    public static void client(TickEvent.ClientTickEvent event) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        Minecraft MC = Minecraft.getInstance();
        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                module.clientTick(MC);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SubscribeEvent()
    public static void level(TickEvent.LevelTickEvent event) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        Minecraft MC = Minecraft.getInstance();
        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                module.worldTick(MC,event.level);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SubscribeEvent()
    public static void player(TickEvent.PlayerTickEvent event) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        Minecraft MC = Minecraft.getInstance();
        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                module.playerTick(MC,event.player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SubscribeEvent
    public static void screen(ScreenshotEvent event) throws IOException {
        while (!VapeManagerReborn.init){
            VapeManagerReborn.init();
        }

        JsonObject data = new JsonObject();
        data.addProperty("result",event.getResultMessage().getString());
        data.addProperty("file",event.getScreenshotFile().getPath());
        VapeManagerReborn.modules.stream().filter(module -> module.enabled).toList().forEach(module -> {
            try {
                JsonObject newData = module.screenshot(data);
                if(newData!=data){
                    event.setResultMessage(Component.nullToEmpty(newData.get("result").getAsString()));
                    event.setScreenshotFile(new File(newData.get("file").getAsString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
