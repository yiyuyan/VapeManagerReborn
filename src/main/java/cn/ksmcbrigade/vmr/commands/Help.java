package cn.ksmcbrigade.vmr.commands;

import cn.ksmcbrigade.vmr.command.Command;
import cn.ksmcbrigade.vmr.uitls.CommandUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Help extends Command {
    public Help() {
        super("help");
    }

    @Override
    public void onCommand(Minecraft MC, Player player, String[] args) {
        if(player!=null){
            CommandUtils.getAll().forEach(c -> player.sendSystemMessage(Component.nullToEmpty("command: "+c.name+"     args: "+c.length)));
        }
    }
}
