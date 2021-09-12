package allah.owns.me.allahware.hacks.chat;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import com.mojang.realmsclient.gui.ChatFormatting;
import allah.owns.me.allahware.util.MessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Module

public class BurrowAnnouncer extends Hack {

    //Module Info
    public BurrowAnnouncer() {
        super(Category.CHAT);

        this.name        = "Burrow Announcer"; //Commands and Clickgui
        this.tag         = "BurrowAnnouncer"; //Config and Arraylist
        this.description = "alerts when someone burrows"; //Useless but normally i add this
    }

    List<Entity> knownPlayers = new ArrayList<>();
    List<Entity> burrowedPlayers = new ArrayList<>();

    @Override
    public void update() {
            for (Entity entity : mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).collect(Collectors.toList())) {
                if (!(entity instanceof EntityPlayer)){
                    continue;
                }

                if (!burrowedPlayers.contains(entity) && isBurrowed(entity)) {
                    burrowedPlayers.add(entity);
                    MessageUtil.send_client_message(ChatFormatting.DARK_PURPLE + "" + ChatFormatting.BOLD + "Burrow Announcer " + ChatFormatting.DARK_AQUA + " > " + ChatFormatting.RESET + entity.getName() + " burrowed!");
                }
                else if (burrowedPlayers.contains(entity) && !isBurrowed(entity)) {
                    burrowedPlayers.remove(entity);
                    MessageUtil.send_client_message(ChatFormatting.DARK_PURPLE + "" + ChatFormatting.BOLD + "Burrow Announcer " + ChatFormatting.DARK_AQUA + " > " + ChatFormatting.RESET + entity.getName() + " unburrowed!");
            }}} 
    
    private boolean isBurrowed(Entity entity) {
        BlockPos entityPos = new BlockPos((entity.posX), entity.posY, (entity.posZ));

        if (mc.world.getBlockState(entityPos).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(entityPos).getBlock() == Blocks.ENDER_CHEST || mc.world.getBlockState(entityPos).getBlock() == Blocks.SKULL) {
            return true;
        }

        return false;
    }
}