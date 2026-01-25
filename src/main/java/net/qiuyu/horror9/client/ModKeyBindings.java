package net.qiuyu.horror9.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.qiuyu.horror9.Horror9;
import org.lwjgl.glfw.GLFW;

/**
 * 模组按键绑定注册类
 */
@EventBusSubscriber(modid = Horror9.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyBindings {
    // 按键分类名称
    public static final String KEY_CATEGORY_HORROR9 = "key.category." + Horror9.MODID;

    // 按键翻译键
    public static final String KEY_CTRL_LEFT_CLICK = "key." + Horror9.MODID + ".ctrl_left_click";
    public static final String KEY_CTRL_RIGHT_CLICK = "key." + Horror9.MODID + ".ctrl_right_click";
    public static final String KEY_CTRL_MIDDLE_CLICK = "key." + Horror9.MODID + ".ctrl_middle_click";

    // 1. Ctrl + 左键
    public static final KeyMapping CTRL_LEFT_CLICK_MAPPING = new KeyMapping(
            KEY_CTRL_LEFT_CLICK,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_LEFT,
            KEY_CATEGORY_HORROR9
    );

    // 2. Ctrl + 右键
    public static final KeyMapping CTRL_RIGHT_CLICK_MAPPING = new KeyMapping(
            KEY_CTRL_RIGHT_CLICK,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_RIGHT,
            KEY_CATEGORY_HORROR9
    );

    // 3. Ctrl + 左键 + 右键
    // 注意：Minecraft 的 KeyMapping 系统默认不支持一个映射对应两个物理按键（除了修饰符）。
    // 这里将其作为一个独立的逻辑按键注册，默认绑定为 Ctrl + 鼠标中键 (Middle Click)，
    // 玩家可以在按键设置中自行更改此项绑定。
    public static final KeyMapping CTRL_MIDDLE_CLICK_MAPPING = new KeyMapping(
            KEY_CTRL_MIDDLE_CLICK,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
            KEY_CATEGORY_HORROR9
    );

    /**
     * 注册按键映射事件
     * @param event RegisterKeyMappingsEvent
     */
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CTRL_LEFT_CLICK_MAPPING);
        event.register(CTRL_RIGHT_CLICK_MAPPING);
        event.register(CTRL_MIDDLE_CLICK_MAPPING);
    }
}
