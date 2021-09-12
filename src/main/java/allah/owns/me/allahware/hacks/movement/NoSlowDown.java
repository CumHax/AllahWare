//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.movement;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class NoSlowDown extends Hack
{
    public NoSlowDown() {
        super(Category.MOVEMENT);
        this.name = "NoSlowDown";
        this.tag = "NoSlowDown";
        this.description = "eat while you run ";
    }
    
    @SubscribeEvent
    public void onInput(final InputUpdateEvent event) {
        if (NoSlowDown.mc.player.isHandActive() && !NoSlowDown.mc.player.isRiding()) {
            final MovementInput movementInput4;
            final MovementInput movementInput7;
            final MovementInput movementInput3 = movementInput7 = (movementInput4 = event.getMovementInput());
            movementInput7.moveStrafe *= 5.0f;
            final MovementInput movementInput6;
            final MovementInput movementInput8;
            final MovementInput movementInput5 = movementInput8 = (movementInput6 = event.getMovementInput());
            movementInput8.moveForward *= 5.0f;
        }
    }
    
    public void enable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
}
