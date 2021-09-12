package allah.owns.me.allahware.hacks.combat;

import allah.owns.me.allahware.hacks.Hack;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.util.FriendUtil;
import allah.owns.me.allahware.util.BlockInteractHelper;
import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.util.MessageUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.block.BlockRedstoneTorch;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import net.minecraft.block.Block;
import org.apache.logging.log4j.LogManager;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import java.util.Iterator;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class AutoPiston extends Hack
{
    private boolean noMaterials;
    private boolean hasMoved;
    private boolean isSneaking;
    private boolean yUnder;
    private boolean isHole;
    private boolean enoughSpace;
    private boolean redstoneBlockMode;
    private boolean fastModeActive;
    private boolean broken;
    private boolean brokenCrystalBug;
    private boolean brokenRedstoneTorch;
    private boolean deadPl;
    private boolean rotationPlayerMoved;
    private int oldSlot;
    private int stage;
    private int delayTimeTicks;
    private int stuck;
    private int hitTryTick;
    private int nCrystal;
    private long startTime;
    private long endTime;
    private int[] slot_mat;
    private int[] delayTable;
    private int[] meCoordsInt;
    private int[] enemyCoordsInt;
    private double[] enemyCoordsDouble;
    private structureTemp toPlace;
    int[][] disp_surblock;
    Double[][] sur_block;
    private EntityPlayer aimTarget;
    Setting breakType;
    Setting placeMode;
    Setting target;
    Setting range;
    Setting crystalDeltaBreak;
    Setting blocksPerTick;
    Setting supBlocksDelay;
    Setting startDelay;
    Setting pistonDelay;
    Setting crystalDelay;
    Setting midHitDelay;
    Setting hitDelay;
    Setting stuckDetector;
    Setting maxYincr;
    Setting blockPlayer;
    Setting rotate;
    Setting confirmBreak;
    Setting confirmPlace;
    Setting allowCheapMode;
    Setting betterPlacement;
    Setting bypassObsidian;
    Setting antiWeakness;
    Setting chatMsg;
    private double CrystalDeltaBreak;
    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    
    public AutoPiston() {
        super(Category.COMBAT);
        this.noMaterials = false;
        this.hasMoved = false;
        this.isSneaking = false;
        this.yUnder = false;
        this.isHole = true;
        this.enoughSpace = true;
        this.redstoneBlockMode = false;
        this.fastModeActive = false;
        this.oldSlot = -1;
        this.stuck = 0;
        this.disp_surblock = new int[][] { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 0, 1 }, { 0, 0, -1 } };
        this.sur_block = new Double[4][3];
        this.breakType = this.create("Break Types", "AutoPistonBreakTypes", "Swing", this.combobox("Swing", "Packet"));
        this.placeMode = this.create("Place Mode", "AutoPistonPlaceMode", "Torch", this.combobox("Block", "Torch", "Both"));
        this.target = this.create("Target Mode", "AutoPistonTargetMode", "Nearest", this.combobox("Nearest", "Looking"));
        this.range = this.create("Range", "AutoPistonRange", 4.91, 0.0, 6.0);
        this.crystalDeltaBreak = this.create("Center Break", "AutoPistonCenterBreak", 1, 0, 5);
        this.blocksPerTick = this.create("Blocks Per Tick", "AutoPistonBPS", 4, 0, 20);
        this.supBlocksDelay = this.create("Surround Delay", "AutoPistonSurroundDelay", 4, 0, 20);
        this.startDelay = this.create("Start Delay", "AutoPistonStartDelay", 4, 0, 20);
        this.pistonDelay = this.create("Piston Delay", "AutoPistonPistonDelay", 2, 0, 20);
        this.crystalDelay = this.create("Crystal Delay", "AutoPistonCrystalDelay", 2, 0, 20);
        this.midHitDelay = this.create("Mid HitDelay", "AutoPistonMidHitDelay", 5, 0, 20);
        this.hitDelay = this.create("Hit Delay", "AutoPistonHitDelay", 2, 0, 20);
        this.stuckDetector = this.create("Stuck Check", "AutoPistonStuckDetector", 35, 0, 200);
        this.maxYincr = this.create("Max Y", "AutoPistonMaxY", 3, 0, 5);
        this.blockPlayer = this.create("Block Player", "AutoPistonBlockPlayer", true);
        this.rotate = this.create("Rotate", "AutoPistonRotate", false);
        this.confirmBreak = this.create("Confirm Break", "AutoPistonConfirmBreak", true);
        this.confirmPlace = this.create("Confirm Place", "AutoPistonConfirmPlace", true);
        this.allowCheapMode = this.create("Cheap Mode", "AutoPistonCheapMode", false);
        this.betterPlacement = this.create("Better Place", "AutoPistonBetterPlace", true);
        this.bypassObsidian = this.create("Bypass", "AutoPistonBypass", false);
        this.antiWeakness = this.create("Anti Weakness", "AutoPistonAntiWeakness", false);
        this.chatMsg = this.create("Chat Messages", "AutoPistongChatMSG", true);
		
        this.name = "Piston Crystal";
        this.tag = "AutoPiston";
        this.description = "Automatically pushes crystals into holes";
        this.CrystalDeltaBreak = this.crystalDeltaBreak.get_value(1) * 0.1;
    }
    
    @Override
    protected void enable() {
        this.initValues();
        if (!this.getAimTarget()) {
            this.playerChecks();
        }
    }
    
    private boolean getAimTarget() {
        if (this.target.in("Nearest")) {
            this.aimTarget = findClosestTarget(this.range.get_value(1), this.aimTarget);
        }
        else {
            this.aimTarget = findLookingPlayer(this.range.get_value(1));
        }
        if (this.aimTarget == null || !this.target.in("Looking")) {
            if (!this.target.in("Looking") && this.aimTarget == null) {
                this.set_disable();
            }
            if (this.aimTarget == null) {
                return true;
            }
        }
        return false;
    }
    
    private void playerChecks() {
        if (this.getMaterialsSlot()) {
            if (this.is_in_hole()) {
                this.enemyCoordsDouble = new double[] { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ };
                this.enemyCoordsInt = new int[] { (int)this.enemyCoordsDouble[0], (int)this.enemyCoordsDouble[1], (int)this.enemyCoordsDouble[2] };
                this.meCoordsInt = new int[] { (int)AutoPiston.mc.player.posX, (int)AutoPiston.mc.player.posY, (int)AutoPiston.mc.player.posZ };
                this.antiAutoDestruction();
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
    
    private void antiAutoDestruction() {
        if (this.redstoneBlockMode || this.rotate.get_value(true)) {
            this.betterPlacement.set_value(false);
        }
    }
    
    private void initValues() {
        this.aimTarget = null;
        this.delayTable = new int[] { this.startDelay.get_value(1), this.supBlocksDelay.get_value(1), this.pistonDelay.get_value(1), this.crystalDelay.get_value(1), this.hitDelay.get_value(1) };
        this.toPlace = new structureTemp(0.0, 0, null);
        this.isHole = true;
        final boolean hasMoved = false;
        this.fastModeActive = hasMoved;
        this.redstoneBlockMode = hasMoved;
        this.yUnder = hasMoved;
        this.brokenRedstoneTorch = hasMoved;
        this.brokenCrystalBug = hasMoved;
        this.broken = hasMoved;
        this.deadPl = hasMoved;
        this.rotationPlayerMoved = hasMoved;
        this.hasMoved = hasMoved;
        this.slot_mat = new int[] { -1, -1, -1, -1, -1, -1 };
        final int stage = 0;
        this.stuck = stage;
        this.delayTimeTicks = stage;
        this.stage = stage;
        if (AutoPiston.mc.player == null) {
            this.set_disable();
        }
        else {
            if (this.chatMsg.get_value(true)) {
                MessageUtil.send_client_error_message("AutoPiston off");
            }
            this.oldSlot = AutoPiston.mc.player.inventory.currentItem;
        }
    }
    
    @Override
    protected void disable() {
        if (AutoPiston.mc.player != null) {
            if (this.chatMsg.get_value(true)) {
                String output = "";
                String materialsNeeded = "";
                if (this.aimTarget == null) {
                    output = "No target found";
                }
                else if (this.yUnder) {
                    output = String.format("you cannot be 2+ blocks under the enemy or %d above", this.maxYincr.get_value(1));
                }
                else if (this.noMaterials) {
                    output = "No Materials Detected";
                    materialsNeeded = this.getMissingMaterials();
                }
                else if (!this.isHole) {
                    output = "The enemy is not in a hole";
                }
                else if (!this.enoughSpace) {
                    output = "Not enough space";
                }
                else if (this.hasMoved) {
                    output = "Out of range";
                }
                else if (this.deadPl) {
                    output = "Enemy is dead";
                }
                else if (this.rotationPlayerMoved) {
                    output = "You cannot move from your hole if you have rotation on";
                }
                if (!materialsNeeded.equals("")) {
                    MessageUtil.send_client_error_message("Materials missing:" + materialsNeeded);
                }
                MessageUtil.send_client_error_message(output + "");
            }
            if (this.isSneaking) {
                AutoPiston.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoPiston.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
            if (this.oldSlot != AutoPiston.mc.player.inventory.currentItem && this.oldSlot != -1) {
                AutoPiston.mc.player.inventory.currentItem = this.oldSlot;
                this.oldSlot = -1;
            }
            this.noMaterials = false;
        }
    }
    
    @Override
    public void update() {
        if (AutoPiston.mc.player == null) {
            this.set_disable();
        }
        else if (this.delayTimeTicks < this.delayTable[this.stage]) {
            ++this.delayTimeTicks;
        }
        else {
            this.delayTimeTicks = 0;
            if (this.enemyCoordsDouble != null && this.aimTarget != null) {
                if (this.aimTarget.isDead) {
                    this.deadPl = true;
                }
                if (this.rotate.get_value(true) && (int)AutoPiston.mc.player.posX != this.meCoordsInt[0] && (int)AutoPiston.mc.player.posZ != this.meCoordsInt[2]) {
                    this.rotationPlayerMoved = true;
                }
                if ((int)this.aimTarget.posX != (int)this.enemyCoordsDouble[0] || (int)this.aimTarget.posZ != (int)this.enemyCoordsDouble[2]) {
                    this.hasMoved = true;
                }
                if (!this.checkVariable() && this.placeSupport()) {
                    switch (this.stage) {
                        case 1: {
                            if (!this.fastModeActive && !this.breakRedstone()) {
                                break;
                            }
                            if (this.fastModeActive && !this.checkCrystalPlace()) {
                                this.stage = 2;
                                break;
                            }
                            this.placeBlockThings(this.stage);
                            break;
                        }
                        case 2: {
                            if (this.fastModeActive || !this.confirmPlace.get_value(true) || this.checkPistonPlace()) {
                                this.placeBlockThings(this.stage);
                                break;
                            }
                            break;
                        }
                        case 3: {
                            if (!this.fastModeActive && this.confirmPlace.get_value(true) && !this.checkCrystalPlace()) {
                                break;
                            }
                            this.placeBlockThings(this.stage);
                            this.hitTryTick = 0;
                            if (this.fastModeActive && !this.checkPistonPlace()) {
                                this.stage = 1;
                                break;
                            }
                            break;
                        }
                        case 4: {
                            this.destroyCrystalAlgo();
                            break;
                        }
                    }
                }
            }
            else if (this.aimTarget == null) {
                this.aimTarget = findLookingPlayer(this.range.get_value(1));
                if (this.aimTarget != null) {
                    this.playerChecks();
                }
            }
            else {
                this.checkVariable();
            }
        }
    }
    
    public void destroyCrystalAlgo() {
        Entity crystal = null;
        for (final Entity t : AutoPiston.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && (((int)t.posX == this.enemyCoordsInt[0] && ((int)(t.posZ - this.CrystalDeltaBreak) == this.enemyCoordsInt[2] || (int)(t.posZ + this.CrystalDeltaBreak) == this.enemyCoordsInt[2])) || ((int)t.posZ == this.enemyCoordsInt[2] && ((int)(t.posX - this.CrystalDeltaBreak) == this.enemyCoordsInt[0] || (int)(t.posX + this.CrystalDeltaBreak) == this.enemyCoordsInt[0])))) {
                crystal = t;
            }
        }
        if (this.confirmBreak.get_value(true) && this.broken && crystal == null) {
            final int n = 0;
            this.stuck = n;
            this.stage = n;
            this.broken = false;
        }
        if (crystal != null) {
            this.breakCrystalPiston(crystal);
            if (this.confirmBreak.get_value(true)) {
                this.broken = true;
            }
            else {
                final int n2 = 0;
                this.stuck = n2;
                this.stage = n2;
            }
        }
        else if (++this.stuck >= this.stuckDetector.get_value(1)) {
            boolean found = false;
            for (final Entity t2 : AutoPiston.mc.world.loadedEntityList) {
                if (t2 instanceof EntityEnderCrystal && (int)t2.posX == (int)this.toPlace.to_place.get(this.toPlace.supportBlock + 1).x && (int)t2.posZ == (int)this.toPlace.to_place.get(this.toPlace.supportBlock + 1).z) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                final BlockPos offsetPosPist = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 2));
                final BlockPos pos = new BlockPos(this.aimTarget.getPositionVector()).add(offsetPosPist.getX(), offsetPosPist.getY(), offsetPosPist.getZ());
                if (this.confirmBreak.get_value(true) && this.brokenRedstoneTorch && this.get_block(pos.getX(), pos.getY(), pos.getZ()) instanceof BlockAir) {
                    this.stage = 1;
                    this.brokenRedstoneTorch = false;
                }
                else {
                    final EnumFacing side = BlockInteractHelper.getPlaceableSide(pos);
                    if (side != null) {
                        this.breakRedstone();
                        if (this.confirmBreak.get_value(true)) {
                            this.brokenRedstoneTorch = true;
                        }
                        else {
                            this.stage = 1;
                        }
                        MessageUtil.send_client_message("Stuck detected: crystal not placed");
                    }
                }
            }
            else {
                boolean ext = false;
                for (final Entity t3 : AutoPiston.mc.world.loadedEntityList) {
                    if (t3 instanceof EntityEnderCrystal && (int)t3.posX == (int)this.toPlace.to_place.get(this.toPlace.supportBlock + 1).x && (int)t3.posZ == (int)this.toPlace.to_place.get(this.toPlace.supportBlock + 1).z) {
                        ext = true;
                        break;
                    }
                }
                if (this.confirmBreak.get_value(true) && this.brokenCrystalBug && !ext) {
                    final int n3 = 0;
                    this.stuck = n3;
                    this.stage = n3;
                    this.brokenCrystalBug = false;
                }
                if (ext) {
                    this.breakCrystalPiston(crystal);
                    if (this.confirmBreak.get_value(true)) {
                        this.brokenCrystalBug = true;
                    }
                    else {
                        final int n4 = 0;
                        this.stuck = n4;
                        this.stage = n4;
                    }
                    MessageUtil.send_client_message("Stuck detected: crystal is stuck in the moving piston");
                }
            }
        }
    }
    
    private String getMissingMaterials() {
        final StringBuilder output = new StringBuilder();
        if (this.slot_mat[0] == -1) {
            output.append(" Obsidian");
        }
        if (this.slot_mat[1] == -1) {
            output.append(" Piston");
        }
        if (this.slot_mat[2] == -1) {
            output.append(" Crystals");
        }
        if (this.slot_mat[3] == -1) {
            output.append(" Redstone");
        }
        if (this.antiWeakness.get_value(true) && this.slot_mat[4] == -1) {
            output.append(" Sword");
        }
        if (this.redstoneBlockMode && this.slot_mat[5] == -1) {
            output.append(" Pick");
        }
        return output.toString();
    }
    
    private void printTimeCrystals() {
        this.endTime = System.currentTimeMillis();
        MessageUtil.send_client_message("3 crystal, time took: " + (this.endTime - this.startTime));
        this.nCrystal = 0;
        this.startTime = System.currentTimeMillis();
    }
    
    private void breakCrystalPiston(final Entity crystal) {
        if (this.hitTryTick++ >= this.midHitDelay.get_value(1)) {
            this.hitTryTick = 0;
            if (this.antiWeakness.get_value(true)) {
                AutoPiston.mc.player.inventory.currentItem = this.slot_mat[4];
            }
            if (this.rotate.get_value(true)) {
                lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)AutoPiston.mc.player);
            }
            if (this.breakType.in("Swing")) {
                breakCrystal(crystal);
            }
            else if (this.breakType.in("Packet")) {
                try {
                    AutoPiston.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                    AutoPiston.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                catch (NullPointerException ex) {}
            }
            if (this.rotate.get_value(true)) {
                resetRotation();
            }
        }
    }
    
    private boolean breakRedstone() {
        final BlockPos offsetPosPist = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + 2));
        final BlockPos pos = new BlockPos(this.aimTarget.getPositionVector()).add(offsetPosPist.getX(), offsetPosPist.getY(), offsetPosPist.getZ());
        if (!(this.get_block(pos.getX(), pos.getY(), pos.getZ()) instanceof BlockAir)) {
            this.breakBlock(pos);
            return false;
        }
        return true;
    }
    
    private void breakBlock(final BlockPos pos) {
        if (this.redstoneBlockMode) {
            AutoPiston.mc.player.inventory.currentItem = this.slot_mat[5];
        }
        final EnumFacing side = BlockInteractHelper.getPlaceableSide(pos);
        if (side != null) {
            if (this.rotate.get_value(true)) {
                final BlockPos neighbour = pos.offset(side);
                final EnumFacing opposite = side.getOpposite();
                final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 1.0, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
                BlockInteractHelper.faceVectorPacketInstant(hitVec);
            }
            AutoPiston.mc.player.swingArm(EnumHand.MAIN_HAND);
            AutoPiston.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, side));
            AutoPiston.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, side));
        }
    }
    
    private boolean checkPistonPlace() {
        final BlockPos targetPosPist = this.compactBlockPos(1);
        if (!(this.get_block(targetPosPist.getX(), targetPosPist.getY(), targetPosPist.getZ()) instanceof BlockPistonBase)) {
            --this.stage;
            return false;
        }
        return true;
    }
    
    private boolean checkCrystalPlace() {
        for (final Entity t : AutoPiston.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && (int)t.posX == (int)(this.aimTarget.posX + this.toPlace.to_place.get(this.toPlace.supportBlock + 1).x) && (int)t.posZ == (int)(this.aimTarget.posZ + this.toPlace.to_place.get(this.toPlace.supportBlock + 1).z)) {
                return true;
            }
        }
        --this.stage;
        return false;
    }
    
    private boolean placeSupport() {
        int checksDone = 0;
        int blockDone = 0;
        if (this.toPlace.supportBlock > 0) {
            do {
                final BlockPos targetPos = this.getTargetPos(checksDone);
                if (this.placeBlock(targetPos, 0, 0.0, 0.0, 1.0) && ++blockDone == this.blocksPerTick.get_value(1)) {
                    return false;
                }
            } while (++checksDone != this.toPlace.supportBlock);
        }
        this.stage = ((this.stage == 0) ? 1 : this.stage);
        return true;
    }
    
    private boolean placeBlock(final BlockPos pos, final int step, final double offsetX, final double offsetZ, final double offsetY) {
        final Block block = AutoPiston.mc.world.getBlockState(pos).getBlock();
        final EnumFacing side = BlockInteractHelper.getPlaceableSide(pos);
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
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5 + offsetX, offsetY, 0.5 + offsetZ).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = AutoPiston.mc.world.getBlockState(neighbour).getBlock();
        try {
            if (this.slot_mat[step] != 11 && AutoPiston.mc.player.inventory.getStackInSlot(this.slot_mat[step]) == ItemStack.EMPTY) {
                this.noMaterials = true;
                return false;
            }
            if (AutoPiston.mc.player.inventory.currentItem != this.slot_mat[step]) {
                AutoPiston.mc.player.inventory.currentItem = ((this.slot_mat[step] == 11) ? AutoPiston.mc.player.inventory.currentItem : this.slot_mat[step]);
            }
        }
        catch (Exception var22) {
            MessageUtil.send_client_message("Fatal Error during the creation of the structure. Please, report this bug in the gamesense disc server");
            final Logger LOGGER = LogManager.getLogger("ListedHack");
            LOGGER.error("[Elevator] error during the creation of the structure.");
            if (var22.getMessage() != null) {
                LOGGER.error("[Elevator] error message: " + var22.getClass().getName() + " " + var22.getMessage());
            }
            else {
                LOGGER.error("[Elevator] cannot find the cause");
            }
            final boolean i5 = false;
            if (var22.getStackTrace().length != 0) {
                LOGGER.error("[Elevator] StackTrace Start");
                for (final StackTraceElement errorMess : var22.getStackTrace()) {
                    LOGGER.error("[Elevator] " + errorMess.toString());
                }
                LOGGER.error("[Elevator] StackTrace End");
            }
            MessageUtil.send_client_message(Integer.toString(step));
            this.set_disable();
        }
        if ((!this.isSneaking && BlockInteractHelper.blackList.contains(neighbourBlock)) || BlockInteractHelper.shulkerList.contains(neighbourBlock)) {
            AutoPiston.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoPiston.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.get_value(true) || step == 1) {
            Vec3d positionHit = hitVec;
            if (!this.rotate.get_value(true) && step == 1) {
                positionHit = new Vec3d(AutoPiston.mc.player.posX + offsetX, AutoPiston.mc.player.posY + ((offsetY == -1.0) ? offsetY : 0.0), AutoPiston.mc.player.posZ + offsetZ);
            }
            BlockInteractHelper.faceVectorPacketInstant(positionHit);
        }
        EnumHand handSwing = EnumHand.MAIN_HAND;
        if (this.slot_mat[step] == 11) {
            handSwing = EnumHand.OFF_HAND;
        }
        AutoPiston.mc.playerController.processRightClickBlock(AutoPiston.mc.player, AutoPiston.mc.world, neighbour, opposite, hitVec, handSwing);
        AutoPiston.mc.player.swingArm(handSwing);
        return true;
    }
    
    public void placeBlockThings(final int step) {
        final BlockPos targetPos = this.compactBlockPos(step);
        this.placeBlock(targetPos, step, this.toPlace.offsetX, this.toPlace.offsetZ, this.toPlace.offsetY);
        ++this.stage;
    }
    
    public BlockPos compactBlockPos(final int step) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + step - 1));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    private BlockPos getTargetPos(final int idx) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(idx));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    private boolean checkVariable() {
        if (!this.noMaterials && this.isHole && this.enoughSpace && !this.hasMoved && !this.deadPl && !this.rotationPlayerMoved) {
            return false;
        }
        this.set_disable();
        return true;
    }
    
    private boolean createStructure() {
        final structureTemp addedStructure = new structureTemp(Double.MAX_VALUE, 0, null);
        try {
            if (this.meCoordsInt[1] - this.enemyCoordsInt[1] > -1 && this.meCoordsInt[1] - this.enemyCoordsInt[1] <= this.maxYincr.get_value(1)) {
                for (int startH = 1; startH >= 0; --startH) {
                    if (addedStructure.to_place == null) {
                        int incr = 0;
                        final ArrayList highSup = new ArrayList();
                        while (this.meCoordsInt[1] > this.enemyCoordsInt[1] + incr) {
                            ++incr;
                            for (final int[] cordSupport : this.disp_surblock) {
                                highSup.add(new Vec3d((double)cordSupport[0], (double)incr, (double)cordSupport[2]));
                            }
                        }
                        incr += startH;
                        int i = -1;
                        for (final Double[] cord_b : this.sur_block) {
                            ++i;
                            final double[] crystalCordsAbs = { cord_b[0], cord_b[1] + incr, cord_b[2] };
                            final int[] crystalCordsRel = { this.disp_surblock[i][0], this.disp_surblock[i][1] + incr, this.disp_surblock[i][2] };
                            Label_2754: {
                                final double distanceNowCrystal;
                                if ((distanceNowCrystal = AutoPiston.mc.player.getDistance(crystalCordsAbs[0], crystalCordsAbs[1], crystalCordsAbs[2])) < addedStructure.distance && this.get_block(crystalCordsAbs[0], crystalCordsAbs[1], crystalCordsAbs[2]) instanceof BlockAir && this.get_block(crystalCordsAbs[0], crystalCordsAbs[1] + 1.0, crystalCordsAbs[2]) instanceof BlockAir && !someoneInCoords(crystalCordsAbs[0], crystalCordsAbs[2])) {
                                    double[] pistonCordAbs = new double[3];
                                    int[] pistonCordRel = new int[3];
                                    if (!this.rotate.get_value(true) && this.betterPlacement.get_value(true)) {
                                        double distancePist = Double.MAX_VALUE;
                                        for (final int[] disp : this.disp_surblock) {
                                            final BlockPos blockPiston = new BlockPos(crystalCordsAbs[0] + disp[0], crystalCordsAbs[1], crystalCordsAbs[2] + disp[2]);
                                            final double minFound;
                                            if ((minFound = AutoPiston.mc.player.getDistanceSqToCenter(blockPiston)) <= distancePist && (this.get_block(blockPiston.getX(), blockPiston.getY(), blockPiston.getZ()) instanceof BlockPistonBase || this.get_block(blockPiston.getX(), blockPiston.getY(), blockPiston.getZ()) instanceof BlockAir) && !someoneInCoords(crystalCordsAbs[0] + disp[0], crystalCordsAbs[2] + disp[2]) && this.get_block(blockPiston.getX() - crystalCordsRel[0], blockPiston.getY(), blockPiston.getZ() - crystalCordsRel[2]) instanceof BlockAir) {
                                                distancePist = minFound;
                                                pistonCordAbs = new double[] { crystalCordsAbs[0] + disp[0], crystalCordsAbs[1], crystalCordsAbs[2] + disp[2] };
                                                pistonCordRel = new int[] { crystalCordsRel[0] + disp[0], crystalCordsRel[1], crystalCordsRel[2] + disp[2] };
                                            }
                                        }
                                        if (distancePist == Double.MAX_VALUE) {
                                            break Label_2754;
                                        }
                                    }
                                    else {
                                        pistonCordAbs = new double[] { crystalCordsAbs[0] + this.disp_surblock[i][0], crystalCordsAbs[1], crystalCordsAbs[2] + this.disp_surblock[i][2] };
                                        final Block tempBlock;
                                        if ((tempBlock = this.get_block(pistonCordAbs[0], pistonCordAbs[1], pistonCordAbs[2])) instanceof BlockPistonBase == tempBlock instanceof BlockAir) {
                                            break Label_2754;
                                        }
                                        if (someoneInCoords(pistonCordAbs[0], pistonCordAbs[2])) {
                                            break Label_2754;
                                        }
                                        pistonCordRel = new int[] { crystalCordsRel[0] * 2, crystalCordsRel[1], crystalCordsRel[2] * 2 };
                                    }
                                    if (this.rotate.get_value(true)) {
                                        final int[] pistonCordInt = { (int)pistonCordAbs[0], (int)pistonCordAbs[1], (int)pistonCordAbs[2] };
                                        boolean behindBol = false;
                                        for (final int checkBehind : new int[] { 0, 2 }) {
                                            if (this.meCoordsInt[checkBehind] == pistonCordInt[checkBehind]) {
                                                final int idx = (checkBehind == 2) ? 0 : 2;
                                                if (pistonCordInt[idx] >= this.enemyCoordsInt[idx] == this.meCoordsInt[idx] >= this.enemyCoordsInt[idx]) {
                                                    behindBol = true;
                                                }
                                            }
                                        }
                                        if (!behindBol && Math.abs(this.meCoordsInt[0] - this.enemyCoordsInt[0]) == 2 && Math.abs(this.meCoordsInt[2] - this.enemyCoordsInt[2]) == 2 && ((this.meCoordsInt[0] == pistonCordInt[0] && Math.abs(this.meCoordsInt[2] - pistonCordInt[2]) >= 2) || (this.meCoordsInt[2] == pistonCordInt[2] && Math.abs(this.meCoordsInt[0] - pistonCordInt[0]) >= 2))) {
                                            behindBol = true;
                                        }
                                        if ((!behindBol && Math.abs(this.meCoordsInt[0] - this.enemyCoordsInt[0]) > 2 && this.meCoordsInt[2] != this.enemyCoordsInt[2]) || (Math.abs(this.meCoordsInt[2] - this.enemyCoordsInt[2]) > 2 && this.meCoordsInt[0] != this.enemyCoordsInt[0])) {
                                            behindBol = true;
                                        }
                                        if (behindBol) {
                                            break Label_2754;
                                        }
                                    }
                                    double[] redstoneCoordsAbs = new double[3];
                                    int[] redstoneCoordsRel = new int[3];
                                    double minFound = Double.MAX_VALUE;
                                    double minNow = -1.0;
                                    boolean foundOne = true;
                                    for (final int[] pos : this.disp_surblock) {
                                        final double[] torchCoords = { pistonCordAbs[0] + pos[0], pistonCordAbs[1], pistonCordAbs[2] + pos[2] };
                                        if ((minNow = AutoPiston.mc.player.getDistance(torchCoords[0], torchCoords[1], torchCoords[2])) < minFound && (!this.redstoneBlockMode || pos[0] == crystalCordsRel[0]) && !someoneInCoords(torchCoords[0], torchCoords[2]) && (this.get_block(torchCoords[0], torchCoords[1], torchCoords[2]) instanceof BlockRedstoneTorch || this.get_block(torchCoords[0], torchCoords[1], torchCoords[2]) instanceof BlockAir) && ((int)torchCoords[0] != (int)crystalCordsAbs[0] || (int)torchCoords[2] != (int)crystalCordsAbs[2])) {
                                            boolean torchFront = false;
                                            for (final int part : new int[] { 0, 2 }) {
                                                final int contPart = (part == 0) ? 2 : 0;
                                                if ((int)torchCoords[contPart] == (int)pistonCordAbs[contPart] && (int)torchCoords[part] == this.enemyCoordsInt[part]) {
                                                    torchFront = true;
                                                }
                                            }
                                            if (!torchFront) {
                                                redstoneCoordsAbs = new double[] { torchCoords[0], torchCoords[1], torchCoords[2] };
                                                redstoneCoordsRel = new int[] { pistonCordRel[0] + pos[0], pistonCordRel[1], pistonCordRel[2] + pos[2] };
                                                foundOne = false;
                                                minFound = minNow;
                                            }
                                        }
                                    }
                                    if (!foundOne) {
                                        if (this.redstoneBlockMode && this.allowCheapMode.get_value(true) && this.get_block(redstoneCoordsAbs[0], redstoneCoordsAbs[1] - 1.0, redstoneCoordsAbs[2]) instanceof BlockAir) {
                                            pistonCordAbs = new double[] { redstoneCoordsAbs[0], redstoneCoordsAbs[1], redstoneCoordsAbs[2] };
                                            pistonCordRel = new int[] { redstoneCoordsRel[0], redstoneCoordsRel[1], redstoneCoordsRel[2] };
                                            redstoneCoordsAbs = new double[] { redstoneCoordsAbs[0], redstoneCoordsAbs[1] - 1.0, redstoneCoordsRel[2] };
                                            redstoneCoordsRel = new int[] { redstoneCoordsRel[0], redstoneCoordsRel[1] - 1, redstoneCoordsRel[2] };
                                            this.fastModeActive = true;
                                        }
                                        final List<Vec3d> toPlaceTemp = new ArrayList<Vec3d>();
                                        int supportBlock = 0;
                                        if (this.get_block(crystalCordsAbs[0], crystalCordsAbs[1] - 1.0, crystalCordsAbs[2]) instanceof BlockAir) {
                                            toPlaceTemp.add(new Vec3d((double)crystalCordsRel[0], (double)(crystalCordsRel[1] - 1), (double)crystalCordsRel[2]));
                                            ++supportBlock;
                                        }
                                        if (!this.fastModeActive && this.get_block(pistonCordAbs[0], pistonCordAbs[1] - 1.0, pistonCordAbs[2]) instanceof BlockAir) {
                                            toPlaceTemp.add(new Vec3d((double)pistonCordRel[0], (double)(pistonCordRel[1] - 1), (double)pistonCordRel[2]));
                                            ++supportBlock;
                                        }
                                        if (!this.fastModeActive) {
                                            if (!this.redstoneBlockMode && this.get_block(redstoneCoordsAbs[0], redstoneCoordsAbs[1] - 1.0, redstoneCoordsAbs[2]) instanceof BlockAir) {
                                                toPlaceTemp.add(new Vec3d((double)redstoneCoordsRel[0], (double)(redstoneCoordsRel[1] - 1), (double)redstoneCoordsRel[2]));
                                                ++supportBlock;
                                            }
                                        }
                                        else if (this.get_block(redstoneCoordsAbs[0] - crystalCordsRel[0], redstoneCoordsAbs[1] - 1.0, redstoneCoordsAbs[2] - crystalCordsRel[2]) instanceof BlockAir) {
                                            toPlaceTemp.add(new Vec3d((double)(redstoneCoordsRel[0] - crystalCordsRel[0]), (double)redstoneCoordsRel[1], (double)(redstoneCoordsRel[2] - crystalCordsRel[2])));
                                            ++supportBlock;
                                        }
                                        toPlaceTemp.add(new Vec3d((double)pistonCordRel[0], (double)pistonCordRel[1], (double)pistonCordRel[2]));
                                        toPlaceTemp.add(new Vec3d((double)crystalCordsRel[0], (double)crystalCordsRel[1], (double)crystalCordsRel[2]));
                                        toPlaceTemp.add(new Vec3d((double)redstoneCoordsRel[0], (double)redstoneCoordsRel[1], (double)redstoneCoordsRel[2]));
                                        if (incr > 1) {
                                            for (int i2 = 0; i2 < highSup.size(); ++i2) {
                                                toPlaceTemp.add(0, (Vec3d) highSup.get(i2) );
                                                ++supportBlock;
                                            }
                                        }
                                        float offsetX;
                                        float offsetZ;
                                        if (this.disp_surblock[i][0] != 0) {
                                            offsetX = (this.rotate.get_value(true) ? (this.disp_surblock[i][0] / 2.0f) : ((float)this.disp_surblock[i][0]));
                                            if (this.rotate.get_value(true)) {
                                                if (AutoPiston.mc.player.getDistanceSq(pistonCordAbs[0], pistonCordAbs[1], pistonCordAbs[2] + 0.5) > AutoPiston.mc.player.getDistanceSq(pistonCordAbs[0], pistonCordAbs[1], pistonCordAbs[2] - 0.5)) {
                                                    offsetZ = -0.5f;
                                                }
                                                else {
                                                    offsetZ = 0.5f;
                                                }
                                            }
                                            else {
                                                offsetZ = (float)this.disp_surblock[i][2];
                                            }
                                        }
                                        else {
                                            offsetZ = (this.rotate.get_value(true) ? (this.disp_surblock[i][2] / 2.0f) : ((float)this.disp_surblock[i][2]));
                                            if (this.rotate.get_value(true)) {
                                                if (AutoPiston.mc.player.getDistanceSq(pistonCordAbs[0] + 0.5, pistonCordAbs[1], pistonCordAbs[2]) > AutoPiston.mc.player.getDistanceSq(pistonCordAbs[0] - 0.5, pistonCordAbs[1], pistonCordAbs[2])) {
                                                    offsetX = -0.5f;
                                                }
                                                else {
                                                    offsetX = 0.5f;
                                                }
                                            }
                                            else {
                                                offsetX = (float)this.disp_surblock[i][0];
                                            }
                                        }
                                        final float offsetY = (this.meCoordsInt[1] - this.enemyCoordsInt[1] == -1) ? 0.0f : 1.0f;
                                        addedStructure.replaceValues(distanceNowCrystal, supportBlock, toPlaceTemp, -1, offsetX, offsetZ, offsetY);
                                        if (this.blockPlayer.get_value(true)) {
                                            final Vec3d valuesStart = addedStructure.to_place.get(addedStructure.supportBlock + 1);
                                            final int[] valueBegin = { (int)(-valuesStart.x), (int)valuesStart.y, (int)(-valuesStart.z) };
                                            if (this.bypassObsidian.get_value(true) && (int)AutoPiston.mc.player.posY != this.enemyCoordsInt[1]) {
                                                addedStructure.to_place.add(0, new Vec3d(0.0, (double)incr, 0.0));
                                                addedStructure.to_place.add(0, new Vec3d((double)valueBegin[0], (double)incr, (double)valueBegin[2]));
                                                final structureTemp structureTemp = addedStructure;
                                                structureTemp.supportBlock += 2;
                                            }
                                            else {
                                                addedStructure.to_place.add(0, new Vec3d(0.0, (double)(incr + 1), 0.0));
                                                addedStructure.to_place.add(0, new Vec3d((double)valueBegin[0], (double)(incr + 1), (double)valueBegin[2]));
                                                addedStructure.to_place.add(0, new Vec3d((double)valueBegin[0], (double)incr, (double)valueBegin[2]));
                                                final structureTemp structureTemp2 = addedStructure;
                                                structureTemp2.supportBlock += 3;
                                            }
                                        }
                                        this.toPlace = addedStructure;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                this.yUnder = true;
            }
        }
        catch (Exception var51) {
            MessageUtil.send_client_message("Fatal Error during the creation of the structure. Please, report this bug in the GameSense discord server");
            final Logger LOGGER = LogManager.getLogger("ListedHack");
            LOGGER.error("[AutoPiston] error during the creation of the structure.");
            if (var51.getMessage() != null) {
                LOGGER.error("[AutoPiston] error message: " + var51.getClass().getName() + " " + var51.getMessage());
            }
            else {
                LOGGER.error("[AutoPiston] cannot find the cause");
            }
            int i3 = 0;
            if (var51.getStackTrace().length != 0) {
                LOGGER.error("[AutoPiston] StackTrace Start");
                for (final StackTraceElement errorMess : var51.getStackTrace()) {
                    LOGGER.error("[AutoPiston] " + errorMess.toString());
                }
                LOGGER.error("[AutoPiston] StackTrace End");
            }
            if (this.aimTarget != null) {
                LOGGER.error("[AutoPiston] closest target is not null");
            }
            else {
                LOGGER.error("[AutoPiston] closest target is null somehow");
            }
            for (final Double[] cord_b2 : this.sur_block) {
                if (cord_b2 != null) {
                    LOGGER.error("[AutoPiston] " + i3 + " is not null");
                }
                else {
                    LOGGER.error("[AutoPiston] " + i3 + " is null");
                }
                ++i3;
            }
        }
        return addedStructure.to_place != null;
    }
    
    public static boolean someoneInCoords(final double x, final double z) {
        final int xCheck = (int)x;
        final int zCheck = (int)z;
        final List<EntityPlayer> playerList = (List<EntityPlayer>)AutoPiston.mc.world.playerEntities;
        for (final EntityPlayer player : playerList) {
            if ((int)player.posX == xCheck && (int)player.posZ == zCheck) {
                return true;
            }
        }
        return false;
    }
    
    private boolean getMaterialsSlot() {
        if (AutoPiston.mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            this.slot_mat[2] = 11;
        }
        if (this.placeMode.in("Block")) {
            this.redstoneBlockMode = true;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoPiston.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (this.slot_mat[2] == -1 && stack.getItem() instanceof ItemEndCrystal) {
                    this.slot_mat[2] = i;
                }
                else if (this.antiWeakness.get_value(true) && stack.getItem() instanceof ItemSword) {
                    this.slot_mat[4] = i;
                }
                else if (stack.getItem() instanceof ItemPickaxe) {
                    this.slot_mat[5] = i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        this.slot_mat[0] = i;
                    }
                    else if (block instanceof BlockPistonBase) {
                        this.slot_mat[1] = i;
                    }
                    else if (!this.placeMode.in("Block") && block instanceof BlockRedstoneTorch) {
                        this.slot_mat[3] = i;
                        this.redstoneBlockMode = false;
                    }
                    else if (!this.placeMode.in("Torch") && block.translationKey.equals("blockRedstone")) {
                        this.slot_mat[3] = i;
                        this.redstoneBlockMode = true;
                    }
                }
            }
        }
        if (!this.redstoneBlockMode) {
            this.slot_mat[5] = -1;
        }
        int i = 0;
        for (final int val : this.slot_mat) {
            if (val != -1) {
                ++i;
            }
        }
        return i >= 4 + (this.antiWeakness.get_value(true) ? 1 : 0) + (this.redstoneBlockMode ? 1 : 0);
    }
    
    private boolean is_in_hole() {
        this.sur_block = new Double[][] { { this.aimTarget.posX + 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX - 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ + 1.0 }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ - 1.0 } };
        return !(this.get_block(this.sur_block[0][0], this.sur_block[0][1], this.sur_block[0][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[1][0], this.sur_block[1][1], this.sur_block[1][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[2][0], this.sur_block[2][1], this.sur_block[2][2]) instanceof BlockAir) && !(this.get_block(this.sur_block[3][0], this.sur_block[3][1], this.sur_block[3][2]) instanceof BlockAir);
    }
    
    private Block get_block(final double x, final double y, final double z) {
        return AutoPiston.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static EntityPlayer findLookingPlayer(final double rangeMax) {
        final ArrayList<EntityPlayer> listPlayer = new ArrayList<EntityPlayer>();
        for (final EntityPlayer target : AutoPiston.mc.world.playerEntities) {
            if (!basicChecksEntity((Entity)target) && AutoPiston.mc.player.getDistance((Entity)target) <= rangeMax) {
                listPlayer.add(target);
            }
        }
        EntityPlayer target = null;
        final Vec3d positionEyes = AutoPiston.mc.player.getPositionEyes(AutoPiston.mc.getRenderPartialTicks());
        final Vec3d rotationEyes = AutoPiston.mc.player.getLook(AutoPiston.mc.getRenderPartialTicks());
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
    
    public static boolean basicChecksEntity(final Entity pl) {
        return pl == AutoPiston.mc.player || FriendUtil.isFriend(pl.getName()) || pl.isDead;
    }
    
    public static EntityPlayer findClosestTarget(final double rangeMax, final EntityPlayer aimTarget) {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)AutoPiston.mc.world.playerEntities;
        EntityPlayer closestTarget_test = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (!basicChecksEntity((Entity)entityPlayer)) {
                if (aimTarget == null && AutoPiston.mc.player.getDistance((Entity)entityPlayer) <= rangeMax) {
                    closestTarget_test = entityPlayer;
                }
                else {
                    if (aimTarget == null || AutoPiston.mc.player.getDistance((Entity)entityPlayer) > rangeMax || AutoPiston.mc.player.getDistance((Entity)entityPlayer) >= AutoPiston.mc.player.getDistance((Entity)aimTarget)) {
                        continue;
                    }
                    closestTarget_test = entityPlayer;
                }
            }
        }
        return closestTarget_test;
    }
    
    public static void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static void setYawAndPitch(final float yaw1, final float pitch1) {
        AutoPiston.yaw = yaw1;
        AutoPiston.pitch = pitch1;
        AutoPiston.isSpoofingAngles = true;
    }
    
    public static void breakCrystal(final Entity crystal) {
        try {
            AutoPiston.mc.playerController.attackEntity((EntityPlayer)AutoPiston.mc.player, crystal);
            AutoPiston.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        catch (NullPointerException ex) {}
    }
    
    public static void resetRotation() {
        if (AutoPiston.isSpoofingAngles) {
            AutoPiston.yaw = AutoPiston.mc.player.rotationYaw;
            AutoPiston.pitch = AutoPiston.mc.player.rotationPitch;
            AutoPiston.isSpoofingAngles = false;
        }
    }
    
    private static class structureTemp
    {
        public double distance;
        public int supportBlock;
        public List<Vec3d> to_place;
        public int direction;
        public float offsetX;
        public float offsetY;
        public float offsetZ;
        
        public structureTemp(final double distance, final int supportBlock, final List<Vec3d> to_place) {
            this.distance = distance;
            this.supportBlock = supportBlock;
            this.to_place = to_place;
            this.direction = -1;
        }
        
        public void replaceValues(final double distance, final int supportBlock, final List<Vec3d> to_place, final int direction, final float offsetX, final float offsetZ, final float offsetY) {
            this.distance = distance;
            this.supportBlock = supportBlock;
            this.to_place = to_place;
            this.direction = direction;
            this.offsetX = offsetX;
            this.offsetZ = offsetZ;
            this.offsetY = offsetY;
        }
    }
}
