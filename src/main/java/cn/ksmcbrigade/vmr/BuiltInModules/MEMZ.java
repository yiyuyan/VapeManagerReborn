package cn.ksmcbrigade.vmr.BuiltInModules;

import cn.ksmcbrigade.vmr.module.Module;
import cn.ksmcbrigade.vmr.uitls.JNAUtils;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Random;

public class MEMZ extends Module {

    public int sleep = 0; //120
    public int sleep2 = 0; //15

    public MEMZ() {
        super("MEMZ");
        if(Boolean.parseBoolean(System.getProperty("java.awt.headless"))){
            System.setProperty("java.awt.headless","false");
        }
    }

    @Override
    public void playerTick(Minecraft MC, @Nullable Player player) throws Exception {
        User32 INSTANCE = User32.INSTANCE;
        JNAUtils.JNAInstance INSTANCE2 = JNAUtils.JNAInstance.INSTANCE;
        if(sleep==0){
            sleep = 120;
            JNAUtils.JNAInstance INSTANCE3 = JNAUtils.JNAInstance.INSTANCE2;
            if(INSTANCE!=null && INSTANCE2!=null){

                int cx = INSTANCE2.GetSystemMetrics(WinUser.SM_CXSCREEN);
                int cy = INSTANCE2.GetSystemMetrics(WinUser.SM_CYSCREEN);

                WinDef.HWND hwnd = INSTANCE.GetDesktopWindow();
                WinDef.HDC hdc = INSTANCE2.GetWindowDC(hwnd);

                INSTANCE3.BitBlt(hdc,0,0,cx,cy,hdc,0,0, new WinDef.DWORD(3342344));
            }
        }
        else{
            sleep--;
        }
        if(sleep2==0){
            sleep2 = 15;
            Random random = new Random();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if(INSTANCE!=null && INSTANCE2!=null){
                INSTANCE2.DrawIcon(INSTANCE.GetDC(new WinDef.HWND(new Pointer(0))),random.nextInt(0,screenSize.height),random.nextInt(0,screenSize.width),INSTANCE2.LoadIconA(0,random.nextInt(32512,32518)));
            }
        }
        else{
            sleep2--;
        }
    }
}
