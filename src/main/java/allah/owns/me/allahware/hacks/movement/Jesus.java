//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.movement;

import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import allah.owns.me.allahware.hacks.Wrapper;
import net.minecraft.entity.Entity;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class Jesus extends Hack
{
    public Jesus() {
        super(Category.MOVEMENT);
        this.name = "Jesus";
        this.tag = "Jesus";
        this.description = "walk on water";
    }
    
    @Override
    public void update() {
        if (Jesus.mc.world != null) {
            if (isInWater((Entity)Jesus.mc.player) && !Jesus.mc.player.isSneaking()) {
                Jesus.mc.player.motionY = 0.1;
                if (Jesus.mc.player.getRidingEntity() != null) {
                    Jesus.mc.player.getRidingEntity().motionY = 0.2;
                }
            }
            if (isAboveWater((Entity)Wrapper.getPlayer()) && !isInWater((Entity)Wrapper.getPlayer()) && !isAboveLand((Entity)Wrapper.getPlayer()) && !Jesus.mc.player.isSneaking()) {
                Jesus.mc.player.motionY = 0.0;
                Jesus.mc.player.onGround = true;
            }
        }
    }
    
    public static boolean isInWater(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY + 0.01;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (Jesus.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean isAboveWater(final Entity entity) {
        final double y = entity.posY - 0.03;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (Jesus.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean isAboveLand(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY - 0.01;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (Jesus.mc.world.getBlockState(pos).getBlock().isFullBlock(Jesus.mc.world.getBlockState(pos))) {
                    return true;
                }
            }
        }
        return false;
    }
}
