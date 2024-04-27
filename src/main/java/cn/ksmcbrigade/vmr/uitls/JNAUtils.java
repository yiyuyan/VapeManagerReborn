package cn.ksmcbrigade.vmr.uitls;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

public class JNAUtils {

    public interface JNAInstance extends Library {
        JNAInstance INSTANCE = Native.load("user32", JNAInstance.class, W32APIOptions.DEFAULT_OPTIONS);
        JNAInstance INSTANCE2 = Native.load("gdi32", JNAInstance.class);

        //user32
        short GetAsyncKeyState(int vKey);
        WinDef.HDC GetDC(WinDef.HWND hWnd);

        //win user
        WinDef.HDC GetWindowDC(WinDef.HWND hWnd);
        int GetSystemMetrics(int nIndex);
        WinDef.HICON LoadIconA(int hInstance,int lpIconName);
        WinDef.BOOL DrawIcon(WinDef.HDC hdc, int X, int Y, WinDef.HICON hIcon);

        //win gdi
        WinDef.BOOL BitBlt(WinDef.HDC hdc, int x, int y, int cx, int cy, WinDef.HDC hdcSrc, int x1, int y1, WinDef.DWORD rop);

    }

    public static boolean isPressed(int key){
        if(User32.INSTANCE==null){
            return false;
        }
        try {
            short state = JNAInstance.INSTANCE.GetAsyncKeyState(key);
            return (state & 0x8000) != 0;
        }
        catch (Exception e){
            return false;
        }
    }
}
