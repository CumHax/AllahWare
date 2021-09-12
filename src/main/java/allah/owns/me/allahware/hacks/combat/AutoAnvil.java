//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.combat;

import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.block.BlockAnvil;
import allah.owns.me.allahware.util.BlockInteractHelper;
import java.util.List;
import allah.owns.me.allahware.util.FriendUtil;
import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import allah.owns.me.allahware.util.MessageUtil;
import allah.owns.me.allahware.hacks.Category;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Hack;

public class AutoAnvil extends Hack
{
    Setting break_mode;
    Setting target;
    Setting tick_delay;
    Setting blocks_per_tick;
    Setting range;
    Setting decrease;
    Setting h_distance;
    Setting min_h;
    Setting fail_stop;
    Setting anti_crystal;
    Setting rotate;
    Setting fast_anvil;
    Setting chatMsg;
    private boolean isSneaking;
    private boolean firstRun;
    private boolean noMaterials;
    private boolean hasMoved;
    private boolean isHole;
    private boolean enoughSpace;
    private boolean blockUp;
    private int oldSlot;
    private int[] slot_mat;
    private double[] enemyCoords;
    Double[][] sur_block;
    private int noKick;
    int[][] model;
    private int blocksPlaced;
    private int delayTimeTicks;
    private int offsetSteps;
    private boolean pick_d;
    private EntityPlayer aimTarget;
    private static ArrayList<Vec3d> to_place;
    
    public AutoAnvil() {
        super(Category.COMBAT);
        this.break_mode = this.create("Break Mode", "Break Mode", "Pickaxe", this.combobox("Pickaxe", "Feet", "None"));
        this.target = this.create("Target", "Target", "Nearest", this.combobox("Nearest", "Looking"));
        this.tick_delay = this.create("Tick Delay", "Tick Delay", 5, 1, 10);
        this.blocks_per_tick = this.create("Blocks Per Tick", "BPS", 5, 1, 10);
        this.range = this.create("Range", "Range", 4, 0, 6);
        this.decrease = this.create("Decrease", "Decrease", 4, 0, 8);
        this.h_distance = this.create("H Distance", "HDistance", 7, 1, 10);
        this.min_h = this.create("Min H", "MinH", 3, 1, 10);
        this.fail_stop = this.create("Fail Stop", "FailStop", 2, 1, 10);
        this.anti_crystal = this.create("AntiCrystal", "AntiCrystal", false);
        this.rotate = this.create("Rotate", "Rotate", true);
        this.fast_anvil = this.create("FastAnvil", "FastAnvil", true);
        this.chatMsg = this.create("Chat Messages", "messages", true);
        this.isSneaking = false;
        this.firstRun = false;
        this.noMaterials = false;
        this.hasMoved = false;
        this.isHole = true;
        this.enoughSpace = true;
        this.blockUp = false;
        this.oldSlot = -1;
        this.slot_mat = new int[] { -1, -1, -1, -1 };
        this.model = new int[][] { { 1, 1, 0 }, { -1, 1, 0 }, { 0, 1, 1 }, { 0, 1, -1 } };
        this.blocksPlaced = 0;
        this.delayTimeTicks = 0;
        this.offsetSteps = 0;
        this.pick_d = false;
        this.name = "Auto Anvil";
        this.tag = "AutoAnvil";
        this.description = "automatically anvils an opponent";
    }
    
    @Override
    protected void enable() {
        if (this.break_mode.in("Pickaxe")) {
            this.pick_d = true;
        }
        this.blocksPlaced = 0;
        this.isHole = true;
        final boolean b = false;
        this.blockUp = b;
        this.hasMoved = b;
        this.firstRun = true;
        this.slot_mat = new int[] { -1, -1, -1, -1 };
        AutoAnvil.to_place = new ArrayList<Vec3d>();
        if (AutoAnvil.mc.player == null) {
            this.set_disable();
        }
        else {
            this.oldSlot = AutoAnvil.mc.player.inventory.currentItem;
        }
    }
    
    @Override
    protected void disable() {
        if (AutoAnvil.mc.player != null) {
            if (this.chatMsg.get_value(true)) {
                MessageUtil.send_client_error_message("AutoAnvil off");
                if (this.noMaterials) {
                    MessageUtil.send_client_error_message("No Materials Detected");
                }
                else if (!this.isHole) {
                    MessageUtil.send_client_error_message("Enemy is not in a hole");
                }
                else if (!this.enoughSpace) {
                    MessageUtil.send_client_error_message("Not enough space");
                }
                else if (this.hasMoved) {
                    MessageUtil.send_client_error_message("Enemy moved away from the hole");
                }
                else if (this.blockUp) {
                    MessageUtil.send_client_error_message("Enemy head blocked");
                }
                else {
                    MessageUtil.send_client_error_message("");
                }
            }
            if (this.isSneaking) {
                AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoAnvil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
            if (this.oldSlot != AutoAnvil.mc.player.inventory.currentItem && this.oldSlot != -1) {
                AutoAnvil.mc.player.inventory.currentItem = this.oldSlot;
                this.oldSlot = -1;
            }
            this.noMaterials = false;
            this.firstRun = true;
        }
    }
    
    @Override
    public void update() {
        if (AutoAnvil.mc.player == null) {
            this.set_disable();
        }
        else {
            if (this.firstRun) {
                if (this.target.in("Nearest")) {
                    this.aimTarget = findClosestTarget(this.range.get_value(1), this.aimTarget);
                }
                else if (this.target.in("Looking")) {
                    this.aimTarget = findLookingPlayer(this.range.get_value(1));
                }
                if (this.aimTarget == null) {
                    return;
                }
                this.firstRun = false;
                if (this.getMaterialsSlot()) {
                    if (this.is_in_hole()) {
                        this.enemyCoords = new double[] { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ };
                        this.enoughSpace = this.createStructure();
                    }
                    else {
                        this.isHole = false;
                    }
                }
                else {
                    this.noMaterials = true;
                }
            }
            else {
                if (this.delayTimeTicks < this.tick_delay.get_value(1)) {
                    ++this.delayTimeTicks;
                    return;
                }
                this.delayTimeTicks = 0;
                if ((int)this.enemyCoords[0] != (int)this.aimTarget.posX || (int)this.enemyCoords[2] != (int)this.aimTarget.posZ) {
                    this.hasMoved = true;
                }
                if (!(this.get_block(this.enemyCoords[0], this.enemyCoords[1] + 2.0, this.enemyCoords[2]) instanceof BlockAir) || !(this.get_block(this.enemyCoords[0], this.enemyCoords[1] + 3.0, this.enemyCoords[2]) instanceof BlockAir)) {
                    this.blockUp = true;
                }
            }
            this.blocksPlaced = 0;
            if (!this.noMaterials && this.isHole && this.enoughSpace && !this.hasMoved && !this.blockUp) {
                this.noKick = 0;
                while (this.blocksPlaced <= this.blocks_per_tick.get_value(1)) {
                    final int maxSteps = AutoAnvil.to_place.size();
                    if (this.offsetSteps >= maxSteps) {
                        this.offsetSteps = 0;
                        break;
                    }
                    final BlockPos offsetPos = new BlockPos((Vec3d)AutoAnvil.to_place.get(this.offsetSteps));
                    final BlockPos targetPos = new BlockPos(this.aimTarget.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                    boolean tryPlacing = true;
                    if (this.offsetSteps > 0 && this.offsetSteps < AutoAnvil.to_place.size() - 1) {
                        for (final Entity entity : AutoAnvil.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(targetPos))) {
                            if (entity instanceof EntityPlayer) {
                                tryPlacing = false;
                                break;
                            }
                        }
                    }
                    if (tryPlacing && this.placeBlock(targetPos, this.offsetSteps)) {
                        ++this.blocksPlaced;
                    }
                    ++this.offsetSteps;
                    if (this.isSneaking) {
                        AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoAnvil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        this.isSneaking = false;
                    }
                    if (this.noKick == this.fail_stop.get_value(1)) {
                        break;
                    }
                }
            }
            else {
                this.set_disable();
            }
        }
    }
    
    public static boolean basicChecksEntity(final Entity pl) {
        return pl == AutoAnvil.mc.player || FriendUtil.isFriend(pl.getName()) || pl.isDead;
    }
    
    public static EntityPlayer findLookingPlayer(final double rangeMax) {
        final ArrayList<EntityPlayer> listPlayer = new ArrayList<EntityPlayer>();
        for (final EntityPlayer target : AutoAnvil.mc.world.playerEntities) {
            if (!basicChecksEntity((Entity)target) && AutoAnvil.mc.player.getDistance((Entity)target) <= rangeMax) {
                listPlayer.add(target);
            }
        }
        EntityPlayer target = null;
        final Vec3d positionEyes = AutoAnvil.mc.player.getPositionEyes(AutoAnvil.mc.getRenderPartialTicks());
        final Vec3d rotationEyes = AutoAnvil.mc.player.getLook(AutoAnvil.mc.getRenderPartialTicks());
        final int precision = 2;
        for (int i = 0; i < (int)rangeMax; ++i) {
            for (int j = precision; j > 0; --j) {
                for (final EntityPlayer targetTemp : listPlayer) {
                    final AxisAlignedBB playerBox = targetTemp.getEntityBoundingBox();
                    final double xArray = positionEyes.x + rotationEyes.x * i + rotationEyes.x / j;
                    final double yArray = positionEyes.y + rotationEyes.y * i + rotationEyes.y / j;
                    final double zArray = positionEyes.z + rotationEyes.z * i + rotationEyes.z / j;
                    if (playerBox.maxY >= yArray && playerBox.minY <= yArray && playerBox.maxX >= xArray && playerBox.minX <= xArray && playerBox.maxZ >= zArray && playerBox.minZ <= zArray) {
                        target = targetTemp;
                    }
                }
            }
        }
        return target;
    }
    
    public static EntityPlayer findClosestTarget(final double rangeMax, final EntityPlayer aimTarget) {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)AutoAnvil.mc.world.playerEntities;
        EntityPlayer closestTarget_test = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (!basicChecksEntity((Entity)entityPlayer)) {
                if (aimTarget == null && AutoAnvil.mc.player.getDistance((Entity)entityPlayer) <= rangeMax) {
                    closestTarget_test = entityPlayer;
                }
                else {
                    if (aimTarget == null || AutoAnvil.mc.player.getDistance((Entity)entityPlayer) > rangeMax || AutoAnvil.mc.player.getDistance((Entity)entityPlayer) >= AutoAnvil.mc.player.getDistance((Entity)aimTarget)) {
                        continue;
                    }
                    closestTarget_test = entityPlayer;
                }
            }
        }
        return closestTarget_test;
    }
    
    private boolean placeBlock(final BlockPos pos, final int step) {
        final Block block = AutoAnvil.mc.world.getBlockState(pos).getBlock();
        final EnumFacing side = BlockInteractHelper.getPlaceableSide(pos);
        if (step == AutoAnvil.to_place.size() - 1 && block instanceof BlockAnvil && side != null) {
            AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
            ++this.noKick;
        }
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockInteractHelper.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = AutoAnvil.mc.world.getBlockState(neighbour).getBlock();
        final int utilSlot = (step == 0 && this.break_mode.in("Feet")) ? 2 : ((step == AutoAnvil.to_place.size() - 1) ? 1 : 0);
        if (AutoAnvil.mc.player.inventory.getStackInSlot(this.slot_mat[utilSlot]) != ItemStack.EMPTY) {
            if (AutoAnvil.mc.player.inventory.currentItem != this.slot_mat[utilSlot]) {
                AutoAnvil.mc.player.inventory.currentItem = this.slot_mat[utilSlot];
            }
            if ((!this.isSneaking && BlockInteractHelper.blackList.contains(neighbourBlock)) || BlockInteractHelper.shulkerList.contains(neighbourBlock)) {
                AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoAnvil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                this.isSneaking = true;
            }
            if (this.rotate.get_value(true)) {
                BlockInteractHelper.faceVectorPacketInstant(hitVec);
            }
            final int bef = AutoAnvil.mc.rightClickDelayTimer;
            if (step == AutoAnvil.to_place.size() - 1) {
                final EntityPlayer found = this.getPlayerFromName(this.aimTarget.getGameProfile().getName());
                if (found == null || (int)found.posX != (int)this.enemyCoords[0] || (int)found.posZ != (int)this.enemyCoords[2]) {
                    this.hasMoved = true;
                    return false;
                }
                if (this.fast_anvil.get_value(true)) {
                    AutoAnvil.mc.rightClickDelayTimer = 0;
                }
            }
            AutoAnvil.mc.playerController.processRightClickBlock(AutoAnvil.mc.player, AutoAnvil.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
            AutoAnvil.mc.player.swingArm(EnumHand.MAIN_HAND);
            if (this.fast_anvil.get_value(true) && step == AutoAnvil.to_place.size() - 1) {
                AutoAnvil.mc.rightClickDelayTimer = bef;
            }
            if (this.pick_d && step == AutoAnvil.to_place.size() - 1) {
                final EnumFacing prova = BlockInteractHelper.getPlaceableSide(new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]));
                if (prova != null) {
                    AutoAnvil.mc.player.inventory.currentItem = this.slot_mat[3];
                    AutoAnvil.mc.player.swingArm(EnumHand.MAIN_HAND);
                    AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]), prova));
                    AutoAnvil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(this.enemyCoords[0], this.enemyCoords[1], this.enemyCoords[2]), prova));
                }
            }
            return true;
        }
        return false;
    }
    
    private EntityPlayer getPlayerFromName(final String name) {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)AutoAnvil.mc.world.playerEntities;
        for (final EntityPlayer entityPlayer : playerList) {
            if (entityPlayer.getGameProfile().getName().equals(name)) {
                return entityPlayer;
            }
        }
        return null;
    }
    
    private boolean getMaterialsSlot() {
        boolean feet = false;
        boolean pick = false;
        if (this.break_mode.in("Feet")) {
            feet = true;
        }
        if (this.break_mode.in("Pick")) {
            pick = true;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoAnvil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (pick && stack.getItem() instanceof ItemPickaxe) {
                    this.slot_mat[3] = i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        this.slot_mat[0] = i;
                    }
                    else if (block instanceof BlockAnvil) {
                        this.slot_mat[1] = i;
                    }
                    else if (feet && (block instanceof BlockPressurePlate || block instanceof BlockButton)) {
                        this.slot_mat[2] = i;
                    }
                }
            }
        }
        int i = 0;
        for (final int val : this.slot_mat) {
            if (val != -1) {
                ++i;
            }
        }
        return i - ((feet || pick) ? 1 : 0) == 2;
    }
    
    private boolean is_in_hole() {
        this.sur_block = new Double[][] { { this.aimTarget.posX + 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX - 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ + 1.0 }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ - 1.0 } };
        this.enemyCoords = new double[] { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ };
        return !(this.get_block(this.sur_block[0][0], this.sur_block[0][1], this.sur_block[0][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[1][0], this.sur_block[1][1], this.sur_block[1][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[2][0], this.sur_block[2][1], this.sur_block[2][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[3][0], this.sur_block[3][1], this.sur_block[3][2]) instanceof BlockAir);
    }
    
    private boolean createStructure() {
        if (this.break_mode.in("Feet")) {
            AutoAnvil.to_place.add(new Vec3d(0.0, 0.0, 0.0));
        }
        AutoAnvil.to_place.add(new Vec3d(1.0, 1.0, 0.0));
        AutoAnvil.to_place.add(new Vec3d(-1.0, 1.0, 0.0));
        AutoAnvil.to_place.add(new Vec3d(0.0, 1.0, 1.0));
        AutoAnvil.to_place.add(new Vec3d(0.0, 1.0, -1.0));
        AutoAnvil.to_place.add(new Vec3d(1.0, 2.0, 0.0));
        AutoAnvil.to_place.add(new Vec3d(-1.0, 2.0, 0.0));
        AutoAnvil.to_place.add(new Vec3d(0.0, 2.0, 1.0));
        AutoAnvil.to_place.add(new Vec3d(0.0, 2.0, -1.0));
        int hDistanceMod = this.h_distance.get_value(1);
        for (double distEnemy = AutoAnvil.mc.player.getDistance((Entity)this.aimTarget); distEnemy > this.decrease.get_value(1); distEnemy -= this.decrease.get_value(1)) {
            --hDistanceMod;
        }
        final int add = (int)(AutoAnvil.mc.player.posY - this.aimTarget.posY);
        if (add > 1) {}
        hDistanceMod += (int)(AutoAnvil.mc.player.posY - this.aimTarget.posY);
        double min_found = Double.MAX_VALUE;
        double[] var10000 = { -1.0, -1.0, -1.0 };
        int cor = -1;
        int i = 0;
        for (final Double[] cord_b : this.sur_block) {
            var10000 = new double[] { cord_b[0], cord_b[1], cord_b[2] };
            final double distance_now;
            if ((distance_now = AutoAnvil.mc.player.getDistanceSq(new BlockPos((double)cord_b[0], (double)cord_b[1], (double)cord_b[2]))) < min_found) {
                min_found = distance_now;
                cor = i;
            }
            ++i;
        }
        boolean possible = false;
        int incr;
        for (incr = 1; this.get_block(this.enemyCoords[0], this.enemyCoords[1] + incr, this.enemyCoords[2]) instanceof BlockAir && incr < hDistanceMod; ++incr) {
            if (!this.anti_crystal.get_value(true)) {
                AutoAnvil.to_place.add(new Vec3d((double)this.model[cor][0], (double)(this.model[cor][1] + incr), (double)this.model[cor][2]));
            }
            else {
                for (int ij = 0; ij < 4; ++ij) {
                    AutoAnvil.to_place.add(new Vec3d((double)this.model[ij][0], (double)(this.model[ij][1] + incr), (double)this.model[ij][2]));
                }
            }
        }
        if (!(this.get_block(this.enemyCoords[0], this.enemyCoords[1] + incr, this.enemyCoords[2]) instanceof BlockAir)) {
            --incr;
        }
        if (incr >= this.min_h.get_value(1)) {
            possible = true;
        }
        AutoAnvil.to_place.add(new Vec3d(0.0, (double)(this.model[cor][1] + incr - 1), 0.0));
        return possible;
    }
    
    private Block get_block(final double x, final double y, final double z) {
        return AutoAnvil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    static {
        AutoAnvil.to_place = new ArrayList<Vec3d>();
    }
}
