package cn.ksmcbrigade.vmr.command;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class Command {

    public final String name;
    public int length;

    public Command(String name,int argsLength){
        this.name = name;
        this.length = argsLength;
    }

    public Command(String name){
        this(name,0);
    }

    public void onCommand(Minecraft MC, Player player,String[] args) throws Exception{}
}
