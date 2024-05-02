package cn.ksmcbrigade.vmr.module;

import cn.ksmcbrigade.vmr.VapeManagerReborn;
import cn.ksmcbrigade.vmr.command.Command;
import cn.ksmcbrigade.vmr.uitls.CommandUtils;
import cn.ksmcbrigade.vmr.uitls.JNAUtils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

        if(VapeManagerReborn.screenKey.isDown()){
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

        try {
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
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @SubscribeEvent()
    public static void command(ClientChatEvent event) throws Exception {
        String message = event.getMessage();
        if(message.startsWith(".")){
            Minecraft MC = Minecraft.getInstance();
            Player player = MC.player;
            String name = CommandUtils.getName(message);
            Command command = CommandUtils.get(name);
            if(command==null && player!=null){
                player.sendSystemMessage(Component.nullToEmpty("Can't found the command: "+name));
            }
            else if(player!=null){
                String[] args = CommandUtils.getArgs(message);
                if(args.length>=command.length){
                    command.onCommand(MC,player,args);
                }
                else{
                    player.sendSystemMessage(Component.nullToEmpty("The command requires at least {} args: ".replace("{}",String.valueOf(command.length))+name));
                }
            }
            event.setCanceled(true);
        }
    }
}
