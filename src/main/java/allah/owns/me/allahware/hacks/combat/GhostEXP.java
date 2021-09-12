//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.combat;

import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Hack;

public class GhostEXP extends Hack
{
    Setting takeOffVal;
    Setting lookPitch;
    Setting allowTakeOff;
    Setting delay;
    private int delay_count;
    int prvSlot;
    
    public GhostEXP() {
        super(Category.COMBAT);

        this.name = "GhostEXP";
        this.tag = "GhostEXP";
        this.description = "EXP's with your third arm.";

        this.takeOffVal = this.create("ArmourHealth", "ArmourHP", 15, 1, 90);
        this.lookPitch = this.create("Pitch", "Pitch", 90, 10, 360);
        this.allowTakeOff = this.create("RemoveArmour", "RemoveArmour", false);
        this.delay = this.create("Delay", "Delay", 1, 0, 4);
    }
    
    @Override
    protected void enable() {
        this.delay_count = 0;
    }
    
    @Override
    public void update() {
        final int oldPitch = (int)GhostEXP.mc.player.rotationPitch;
        this.prvSlot = GhostEXP.mc.player.inventory.currentItem;
        GhostEXP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.findExpInHotbar()));
        GhostEXP.mc.player.rotationPitch = (float)this.lookPitch.get_value(90);
        GhostEXP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(GhostEXP.mc.player.rotationYaw, (float)this.lookPitch.get_value(90), true));
        GhostEXP.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        GhostEXP.mc.player.rotationPitch = (float)oldPitch;
        GhostEXP.mc.player.inventory.currentItem = this.prvSlot;
        GhostEXP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.prvSlot));
        if (this.allowTakeOff.get_value(false)) {
            this.takeArmorOff();
        }
    }
    
    private int findExpInHotbar() {
        int slot = 0;
        for (int i = 0; i < 9; ++i) {
            if (GhostEXP.mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    private ItemStack getArmor(final int first) {
        return (ItemStack)GhostEXP.mc.player.inventoryContainer.getInventory().get(first);
    }
    
    private void takeArmorOff() {
        for (int slot = 5; slot <= 8; ++slot) {
            final ItemStack item = this.getArmor(slot);
            final double max_dam = item.getMaxDamage();
            final double dam_left = item.getMaxDamage() - item.getItemDamage();
            final double percent = dam_left / max_dam * 100.0;
            if (percent >= this.takeOffVal.get_value(15) && !item.equals(Items.AIR)) {
                if (!this.notInInv(Items.AIR)) {
                    return;
                }
                if (this.delay_count < this.delay.get_value(1)) {
                    ++this.delay_count;
                    return;
                }
                this.delay_count = 0;
                GhostEXP.mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)GhostEXP.mc.player);
            }
        }
    }
    
    public Boolean notInInv(final Item itemOfChoice) {
        int n = 0;
        if (itemOfChoice == GhostEXP.mc.player.getHeldItemOffhand().getItem()) {
            return true;
        }
        for (int i = 35; i >= 0; --i) {
            final Item item = GhostEXP.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == itemOfChoice) {
                return true;
            }
            if (item != itemOfChoice) {
                ++n;
            }
        }
        if (n >= 35) {
            return false;
        }
        return true;
    }
}
