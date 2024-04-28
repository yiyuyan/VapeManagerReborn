package cn.ksmcbrigade.vmr.uitls;

import net.minecraft.client.gui.components.EditBox;

import java.lang.reflect.Field;

import static java.awt.event.KeyEvent.*;
import static org.lwjgl.glfw.GLFW.*;

public class OtherUtils {
    public static boolean hasEditBox(Class<?> clazz) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().equals(EditBox.class)) {
                    field.setAccessible(true);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int convertGLFWKeyToVKKey(int glfwKey) {
        return switch (glfwKey) {
            case GLFW_KEY_SPACE -> VK_SPACE;
            case GLFW_KEY_APOSTROPHE -> VK_QUOTE;
            case GLFW_KEY_COMMA -> VK_COMMA;
            case GLFW_KEY_MINUS -> VK_MINUS;
            case GLFW_KEY_PERIOD -> VK_PERIOD;
            case GLFW_KEY_SLASH -> VK_SLASH;
            case GLFW_KEY_0 -> VK_0;
            case GLFW_KEY_1 -> VK_1;
            case GLFW_KEY_2 -> VK_2;
            case GLFW_KEY_3 -> VK_3;
            case GLFW_KEY_4 -> VK_4;
            case GLFW_KEY_5 -> VK_5;
            case GLFW_KEY_6 -> VK_6;
            case GLFW_KEY_7 -> VK_7;
            case GLFW_KEY_8 -> VK_8;
            case GLFW_KEY_9 -> VK_9;
            case GLFW_KEY_SEMICOLON -> VK_SEMICOLON;
            case GLFW_KEY_EQUAL, GLFW_KEY_KP_EQUAL -> VK_EQUALS;
            case GLFW_KEY_A -> VK_A;
            case GLFW_KEY_B -> VK_B;
            case GLFW_KEY_C -> VK_C;
            case GLFW_KEY_D -> VK_D;
            case GLFW_KEY_E -> VK_E;
            case GLFW_KEY_F -> VK_F;
            case GLFW_KEY_G -> VK_G;
            case GLFW_KEY_H -> VK_H;
            case GLFW_KEY_I -> VK_I;
            case GLFW_KEY_J -> VK_J;
            case GLFW_KEY_K -> VK_K;
            case GLFW_KEY_L -> VK_L;
            case GLFW_KEY_M -> VK_M;
            case GLFW_KEY_N -> VK_N;
            case GLFW_KEY_O -> VK_O;
            case GLFW_KEY_P -> VK_P;
            case GLFW_KEY_Q -> VK_Q;
            case GLFW_KEY_R -> VK_R;
            case GLFW_KEY_S -> VK_S;
            case GLFW_KEY_T -> VK_T;
            case GLFW_KEY_U -> VK_U;
            case GLFW_KEY_V -> VK_V;
            case GLFW_KEY_W -> VK_W;
            case GLFW_KEY_X -> VK_X;
            case GLFW_KEY_Y -> VK_Y;
            case GLFW_KEY_Z -> VK_Z;
            case GLFW_KEY_LEFT_BRACKET -> VK_OPEN_BRACKET;
            case GLFW_KEY_BACKSLASH -> VK_BACK_SLASH;
            case GLFW_KEY_RIGHT_BRACKET -> VK_CLOSE_BRACKET;
            case GLFW_KEY_GRAVE_ACCENT -> VK_BACK_QUOTE;
            case GLFW_KEY_ESCAPE -> VK_ESCAPE;
            case GLFW_KEY_ENTER, GLFW_KEY_KP_ENTER -> VK_ENTER;
            case GLFW_KEY_TAB -> VK_TAB;
            case GLFW_KEY_BACKSPACE -> VK_BACK_SPACE;
            case GLFW_KEY_INSERT -> VK_INSERT;
            case GLFW_KEY_DELETE -> VK_DELETE;
            case GLFW_KEY_RIGHT -> VK_RIGHT;
            case GLFW_KEY_LEFT -> VK_LEFT;
            case GLFW_KEY_DOWN -> VK_DOWN;
            case GLFW_KEY_UP -> VK_UP;
            case GLFW_KEY_PAGE_UP -> VK_PAGE_UP;
            case GLFW_KEY_PAGE_DOWN -> VK_PAGE_DOWN;
            case GLFW_KEY_HOME -> VK_HOME;
            case GLFW_KEY_END -> VK_END;
            case GLFW_KEY_CAPS_LOCK -> VK_CAPS_LOCK;
            case GLFW_KEY_SCROLL_LOCK -> VK_SCROLL_LOCK;
            case GLFW_KEY_NUM_LOCK -> VK_NUM_LOCK;
            case GLFW_KEY_PRINT_SCREEN -> VK_PRINTSCREEN;
            case GLFW_KEY_PAUSE -> VK_PAUSE;
            case GLFW_KEY_F1 -> VK_F1;
            case GLFW_KEY_F2 -> VK_F2;
            case GLFW_KEY_F3 -> VK_F3;
            case GLFW_KEY_F4 -> VK_F4;
            case GLFW_KEY_F5 -> VK_F5;
            case GLFW_KEY_F6 -> VK_F6;
            case GLFW_KEY_F7 -> VK_F7;
            case GLFW_KEY_F8 -> VK_F8;
            case GLFW_KEY_F9 -> VK_F9;
            case GLFW_KEY_F10 -> VK_F10;
            case GLFW_KEY_F11 -> VK_F11;
            case GLFW_KEY_F12 -> VK_F12;
            case GLFW_KEY_F13 -> VK_F13;
            case GLFW_KEY_F14 -> VK_F14;
            case GLFW_KEY_F15 -> VK_F15;
            case GLFW_KEY_F16 -> VK_F16;
            case GLFW_KEY_F17 -> VK_F17;
            case GLFW_KEY_F18 -> VK_F18;
            case GLFW_KEY_F19 -> VK_F19;
            case GLFW_KEY_F20 -> VK_F20;
            case GLFW_KEY_F21 -> VK_F21;
            case GLFW_KEY_F22 -> VK_F22;
            case GLFW_KEY_F23 -> VK_F23;
            case GLFW_KEY_F24 -> VK_F24;
            case GLFW_KEY_KP_0 -> VK_NUMPAD0;
            case GLFW_KEY_KP_1 -> VK_NUMPAD1;
            case GLFW_KEY_KP_2 -> VK_NUMPAD2;
            case GLFW_KEY_KP_3 -> VK_NUMPAD3;
            case GLFW_KEY_KP_4 -> VK_NUMPAD4;
            case GLFW_KEY_KP_5 -> VK_NUMPAD5;
            case GLFW_KEY_KP_6 -> VK_NUMPAD6;
            case GLFW_KEY_KP_7 -> VK_NUMPAD7;
            case GLFW_KEY_KP_8 -> VK_NUMPAD8;
            case GLFW_KEY_KP_9 -> VK_NUMPAD9;
            case GLFW_KEY_KP_DECIMAL -> VK_DECIMAL;
            case GLFW_KEY_KP_DIVIDE -> VK_DIVIDE;
            case GLFW_KEY_KP_MULTIPLY -> VK_MULTIPLY;
            case GLFW_KEY_KP_SUBTRACT -> VK_SUBTRACT;
            case GLFW_KEY_KP_ADD -> VK_ADD;
            case GLFW_KEY_LEFT_SHIFT, GLFW_KEY_RIGHT_SHIFT -> VK_SHIFT;
            case GLFW_KEY_LEFT_CONTROL, GLFW_KEY_RIGHT_CONTROL -> VK_CONTROL;
            case GLFW_KEY_LEFT_ALT, GLFW_KEY_RIGHT_ALT -> VK_ALT;
            case GLFW_KEY_MENU -> VK_CONTEXT_MENU;
            default -> VK_UNDEFINED;
        };
    }
}

