package cn.ksmcbrigade.vmr.commands;

import cn.ksmcbrigade.vmr.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Pos extends Command {
    public Pos() {
        super("pos");
    }

    @Override
    public void onCommand(Minecraft MC, Player player, String[] args) {
        if(player!=null){
            Vec3 pos = player.position();
            player.sendSystemMessage(Component.nullToEmpty("XYZ: "+Math.round(pos.x*1000.0D)/1000.0D+", "+Math.round(pos.y*1000.0D)/1000.0D+", "+Math.round(pos.z*1000.0D)/1000.0D));
        }
    }
}
