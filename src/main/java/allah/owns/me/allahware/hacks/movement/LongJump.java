//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.guiscreen.settings.Setting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.Mod;
import allah.owns.me.allahware.hacks.Hack;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class LongJump extends Hack
{
    Setting speed;
    Setting packet;
    Setting toggle;
    private static boolean jumped;
    private static boolean boostable;
    
    public LongJump() {
        super(Category.MOVEMENT);
        this.speed = this.create("Speed", "LGSpeed", 30.0, 1.0, 100.0);
        this.packet = this.create("Packet", "LGPacket", false);
        this.toggle = this.create("Toggle", "LGToggle", false);
        this.name = "Long Jump";
        this.tag = "LongJump";
        this.description = "jumps longer";
    }
    
    @Override
    public void update() {
        if (LongJump.mc.player != null && LongJump.mc.world != null) {
            if (LongJump.jumped) {
                if (LongJump.mc.player.onGround || LongJump.mc.player.capabilities.isFlying) {
                    LongJump.jumped = false;
                    LongJump.mc.player.motionX = 0.0;
                    LongJump.mc.player.motionZ = 0.0;
                    if (this.packet.get_value(true)) {
                        LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX, LongJump.mc.player.posY, LongJump.mc.player.posZ, LongJump.mc.player.onGround));
                        LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX + LongJump.mc.player.motionX, 0.0, LongJump.mc.player.posZ + LongJump.mc.player.motionZ, LongJump.mc.player.onGround));
                    }
                    if (this.toggle.get_value(true)) {
                        this.toggle();
                    }
                    return;
                }
                if (LongJump.mc.player.movementInput.moveForward == 0.0f && LongJump.mc.player.movementInput.moveStrafe == 0.0f) {
                    return;
                }
                final double yaw = this.getDirection();
                LongJump.mc.player.motionX = -Math.sin(yaw) * ((float)Math.sqrt(LongJump.mc.player.motionX * LongJump.mc.player.motionX + LongJump.mc.player.motionZ * LongJump.mc.player.motionZ) * (LongJump.boostable ? (this.speed.get_value(0) / 10.0f) : 1.0f));
                LongJump.mc.player.motionZ = Math.cos(yaw) * ((float)Math.sqrt(LongJump.mc.player.motionX * LongJump.mc.player.motionX + LongJump.mc.player.motionZ * LongJump.mc.player.motionZ) * (LongJump.boostable ? (this.speed.get_value(0) / 10.0f) : 1.0f));
                LongJump.boostable = false;
                if (this.toggle.get_value(true)) {
                    this.toggle();
                }
            }
            if (LongJump.mc.player.movementInput.moveForward == 0.0f && LongJump.mc.player.movementInput.moveStrafe == 0.0f && LongJump.jumped) {
                LongJump.mc.player.motionX = 0.0;
                LongJump.mc.player.motionZ = 0.0;
            }
        }
    }
    
    @SubscribeEvent
    public static void onJump(final LivingEvent.LivingJumpEvent event) {
        if (LongJump.mc.player != null && LongJump.mc.world != null && event.getEntity() == LongJump.mc.player && (LongJump.mc.player.movementInput.moveForward != 0.0f || LongJump.mc.player.movementInput.moveStrafe != 0.0f)) {
            LongJump.jumped = true;
            LongJump.boostable = true;
        }
    }
    
    private double getDirection() {
        float rotationYaw = LongJump.mc.player.rotationYaw;
        if (LongJump.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (LongJump.mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (LongJump.mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (LongJump.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (LongJump.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    static {
        LongJump.jumped = false;
        LongJump.boostable = false;
    }
}
