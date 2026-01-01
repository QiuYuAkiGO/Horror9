package net.qiuyu.horror9.items.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.qiuyu.horror9.Horror9;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class HuntingHornItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HuntingHornItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    /**
     * 必须重写此方法并返回一个非零值，右键才能“按住”
     * 72000 tick = 1小时，足够长了
     */
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * 定义右键按住时的动作，比如 BOW(拉弓), SPEAR(投矛), BLOCK(格挡) 或 NONE
     */
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    /**
     * 处理右键交互
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        // 能够读取到上文设置好的对应按键
        if (pLevel.isClientSide) {
            // 通过 Proxy 安全读取 ModKeyBindings 中注册的按键状态
            if (Horror9.PROXY.isNoteKey2Down()) {
                // 逻辑已由全局事件监听器 HuntingHornClientEvents 处理发包
                // 此处保留作为按键读取示例
            }
        }

        // 普通右键：开始“使用”物品（进入按住状态），这对检测“左键+右键”至关重要
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player) {
            Level level = player.level();
            
            if (level.isClientSide) {
                // 判定：左键 + 右键 (通过读取 ModKeyBindings 中的 Shift + 中键 映射)
                if (Horror9.PROXY.isNoteKey3Down() && player.isUsingItem()) {
                    return true;
                }

                // 检查是否按下设置好的 Shift + 左键
                if (Horror9.PROXY.isNoteKey1Down()) {
                    return true;
                }
            }
        }
        return super.onEntitySwing(stack, entity);
    }

    /**
     * 添加音符的具体实现，可由网络消息或本地调用
     */
    public void addNote(ItemStack stack, Player player, int noteType) {
        if (player.level().isClientSide) return;

        HornType type = HornType.values()[noteType % HornType.values().length];
        playSeqAdd(stack, player, type);
    }

    private void playSeqAdd(ItemStack stack, Player player, HornType type){
        CompoundTag nbt = stack.getOrCreateTag();
        ListTag list = nbt.getList("PlaySeq", Tag.TAG_STRING);
        list.add(StringTag.valueOf(type.name()));
        
        // 保持序列长度，例如最多 4 个音符
        if (list.size() > 4) {
            list.remove(0);
        }
        
        nbt.put("PlaySeq", list);
        
        // 发送动作条提示
        player.displayClientMessage(Component.literal("§6演奏音符: §f" + type.name()), true);
        
        playSeqCheck(stack, player);
    }

    private void playSeqCheck(ItemStack stack, Player player){
        // 这里可以获取当前序列并进行旋律检查
        ListTag list = stack.getOrCreateTag().getList("PlaySeq", Tag.TAG_STRING);
        if (list.size() >= 3) {
            // startPlaying();
        }
    }

    private void startPlaying(){
        // 触发演奏成功逻辑
    }

    private void playSeqRemove(ItemStack stack){
        stack.getOrCreateTag().remove("PlaySeq");
    }

    public enum HornType{
        RED,
        GREEN,
        BLUE
    }
}
