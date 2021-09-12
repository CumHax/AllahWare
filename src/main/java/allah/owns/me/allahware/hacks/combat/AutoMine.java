package allah.owns.me.allahware.hacks.combat;

import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import allah.owns.me.allahware.util.BreakUtil;
import allah.owns.me.allahware.util.EntityUtil;
import allah.owns.me.allahware.util.MessageUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.BlockPos;

public class AutoMine extends Hack {

    public AutoMine() {
        super(Category.COMBAT);

        this.name        = "Auto Mine";
        this.tag         = "AutoMine";
        this.description = "jumpy is now never going to use the client again";
    }

    Setting end_crystal = create("End Crystal", "MineEndCrystal", false);
    Setting range = create("Range", "MineRange", 4, 0, 6);
    Setting ray_trace = create("Ray Trace", "MineRayTrace", false);
    Setting swap = create("Swap to Pick","MineSwap", true);
    
    private BlockPos target_block = null;

    @Override
    protected void enable() {
        target_block = null;

        for (EntityPlayer player : mc.world.playerEntities) {
            if (mc.player.getDistance(player) > range.get_value(1)) continue;

            BlockPos p = EntityUtil.is_cityable(player, end_crystal.get_value(true));

            if (p != null) {
                target_block = p;
            }
        }

        if (target_block == null) {
            MessageUtil.send_client_message("cannot find block");
            this.set_active(false);
            return;
        }

        int pickSlot = findPickaxe();
        if (swap.get_value(true) && pickSlot != -1) {
            mc.player.inventory.currentItem = pickSlot;
        }

        BreakUtil.set_current_block(target_block);
    }

    @Override
    public void update() {
        BreakUtil.update(range.get_value(1), ray_trace.get_value(true));
        if(mc.world.getBlockState(target_block).getBlock() instanceof BlockAir) {
            this.set_active(false);
        }
    }

    @Override
    protected void disable() {
        BreakUtil.set_current_block(null);
    }

    private int findPickaxe() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemPickaxe) {
                return i;
            }
        }

        return -1;
    }
}
