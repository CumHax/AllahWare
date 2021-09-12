//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.movement;

import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.client.settings.KeyBinding;
import allah.owns.me.allahware.hacks.Category;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import allah.owns.me.allahware.hacks.Hack;

public class AutoEat extends Hack
{
    private int lastSlot;
    private boolean eating;
    
    private boolean isValid(final ItemStack stack, final int food) {
        return stack.getItem() instanceof ItemFood && 20 - food >= ((ItemFood)stack.getItem()).getHealAmount(stack);
    }
    
    public AutoEat() {
        super(Category.MOVEMENT);
        this.lastSlot = -1;
        this.eating = false;
        this.name = "AutoEat";
        this.tag = "AutoEat";
        this.description = "Automatically eat when hungry";
    }
    
    @Override
    public void update() {
        if (this.eating && !AutoEat.mc.player.isHandActive()) {
            if (this.lastSlot != -1) {
                AutoEat.mc.player.inventory.currentItem = this.lastSlot;
                this.lastSlot = -1;
            }
            this.eating = false;
            KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            return;
        }
        if (this.eating) {
            return;
        }
        final FoodStats stats = AutoEat.mc.player.getFoodStats();
        if (this.isValid(AutoEat.mc.player.getHeldItemOffhand(), stats.getFoodLevel())) {
            AutoEat.mc.player.setActiveHand(EnumHand.OFF_HAND);
            this.eating = true;
            KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            AutoEat.mc.playerController.processRightClick((EntityPlayer)AutoEat.mc.player, (World)AutoEat.mc.world, EnumHand.MAIN_HAND);
        }
        else {
            for (int i = 0; i < 9; ++i) {
                if (this.isValid(AutoEat.mc.player.inventory.getStackInSlot(i), stats.getFoodLevel())) {
                    this.lastSlot = AutoEat.mc.player.inventory.currentItem;
                    AutoEat.mc.player.inventory.currentItem = i;
                    this.eating = true;
                    KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    AutoEat.mc.playerController.processRightClick((EntityPlayer)AutoEat.mc.player, (World)AutoEat.mc.world, EnumHand.MAIN_HAND);
                    return;
                }
            }
        }
    }
}
