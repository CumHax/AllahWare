package allah.owns.me.allahware.hacks.combat;

import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import cf.warriorcrystal.other.wurstplusthree.BlockUtil;
import cf.warriorcrystal.other.wurstplusthree.InventoryUtil;
import cf.warriorcrystal.other.wurstplusthree.MappingUtil;
import cf.warriorcrystal.other.wurstplusthree.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Burrow extends Hack {
	
    public Burrow() {
        super(Category.COMBAT);

        this.name        = "Burrow"; //Commands and Clickgui
        this.tag         = "InstantBurrow"; //Config and Arraylist
        this.description = "";
    }

    Setting rotate = create("Rotate", "Rotate", true);

    Setting type = create("Type", "Type", "Packet", combobox("Packet", "Normal"));
    Setting block = create("Block", "Block", "All", combobox("All", "EChest", "Chest"));
    Setting force = create("Force", "Force", 1.5, -5.0, 10.0);
    Setting instant = create("Instant", "Instant", true);
    Setting center = create("Center", "Center", false);
    Setting bypass = create("Bypass", "Bypass", false);

    int swapBlock = -1;
    Vec3d centerBlock = Vec3d.ZERO;
    BlockPos oldPos;
    Block blockW = Blocks.OBSIDIAN;
    boolean flag;

    @Override
    public void enable() {
        flag = false;

        mc.player.motionX = 0;
        mc.player.motionZ = 0;

        centerBlock = this.getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (centerBlock != Vec3d.ZERO && center.get_value(true)) {
            double x_diff = Math.abs(centerBlock.x - mc.player.posX);
            double z_diff = Math.abs(centerBlock.z - mc.player.posZ);
            if (x_diff <= 0.1 && z_diff <= 0.1) {
                centerBlock = Vec3d.ZERO;
            } else {
                double motion_x = centerBlock.x - mc.player.posX;
                double motion_z = centerBlock.z - mc.player.posZ;
                mc.player.motionX = motion_x / 2;
                mc.player.motionZ = motion_z / 2;
            }
        }

        oldPos = PlayerUtil.getPlayerPos();
        if(block.in("All"))
                swapBlock = PlayerUtil.findObiInHotbar();

            if(block.in("EChest"))
                swapBlock = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if(block.in("Chest"))
                swapBlock = InventoryUtil.findHotbarBlock(BlockChest.class);


        if (swapBlock == -1) {
            this.disable();
            return;
        }
        if (instant.get_value(true)) {
            this.setTimer(50f);
        }
        if (type.in("Normal")) {
            mc.player.jump();
        }
    }

    @Override
    public void update() {
        if (type.in("Normal")) {
            if (mc.player.posY > (oldPos.getY() + 1.04)) {
                int old = mc.player.inventory.currentItem;
                this.switchToSlot(swapBlock);
                BlockUtil.placeBlock(oldPos, EnumHand.MAIN_HAND, rotate.get_value(true), true, false);
                this.switchToSlot(old);
                mc.player.motionY = force.get_value(1);
                toggle();
            }
        } else {
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.41999998688698,
                            mc.player.posZ,
                            true
                    )
            );
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 0.7531999805211997,
                            mc.player.posZ,
                            true
                    )
            );
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.00133597911214,
                            mc.player.posZ,
                            true
                    )
            );
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + 1.16610926093821,
                            mc.player.posZ,
                            true
                    )
            );
            int old = mc.player.inventory.currentItem;
            this.switchToSlot(swapBlock);
            BlockUtil.placeBlock(oldPos, EnumHand.MAIN_HAND, rotate.get_value(true), true, false);
            this.switchToSlot(old);
            mc.player.connection.sendPacket(
                    new CPacketPlayer.Position(
                            mc.player.posX,
                            mc.player.posY + force.get_value(1),
                            mc.player.posZ,
                            false
                    )
            );
            if(bypass.get_value(true) && !mc.player.isSneaking()) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                mc.player.setSneaking(true);
                mc.playerController.updateController();
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                mc.player.setSneaking(false);
                mc.playerController.updateController();
            }
            toggle();
        }
    }

    @Override
    public void disable(){
        if(instant.get_value(true)){
            this.setTimer(1f);
        }
    }

    private void switchToSlot(final int slot) {
        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        mc.player.inventory.currentItem = slot;
        mc.playerController.updateController();
    }

    private void setTimer(float value) {
        try {
            Field timer = Minecraft.class.getDeclaredField(MappingUtil.timer);
            timer.setAccessible(true);
            Field tickLength = Timer.class.getDeclaredField(MappingUtil.tickLength);
            tickLength.setAccessible(true);
            tickLength.setFloat(timer.get(mc), 50.0F / value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Vec3d getCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D ;

        return new Vec3d(x, y, z);
    }

    public void setBlock(Block b){
        this.blockW = b;
    }

    public Block getBlock(){
        return this.blockW;
    }

}
