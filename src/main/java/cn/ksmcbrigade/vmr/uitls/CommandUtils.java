package cn.ksmcbrigade.vmr.uitls;

import cn.ksmcbrigade.vmr.command.Command;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandUtils {
    public static ArrayList<Command> commands = new ArrayList<>();

    public static void add(Command command){
        commands.add(command);
    }

    public static void del(Command command){
        commands.remove(command);
    }

    public static void del(int command){
        commands.remove(command);
    }

    public static String getName(String command){
        String noPoint = command.substring(1);
        if(noPoint.contains(" ")){
            return noPoint.split(" ")[0];
        }
        else{
            return noPoint;
        }
    }

    public static String[] getArgs(String command){
        String noPoint = command.substring(1);
        if(noPoint.contains(" ")){
            String name = noPoint.split(" ")[0];
            ArrayList<String> LastArgs = new ArrayList<>(List.of(noPoint.split(" ")));
            ArrayList<String> args = new ArrayList<>();
            LastArgs.forEach(a -> {
                if(!a.equalsIgnoreCase(name)){
                    args.add(a);
                }
            });
            return args.toArray(new String[0]);
        }
        else{
            return new String[0];
        }
    }

    @Nullable
    public static Command get(String name){
        for(Command command:commands){
            if(command.name.equalsIgnoreCase(name)){
                return command;
            }
        }
        return null;
    }

    public static ArrayList<Command> getAll(){
        return commands;
    }
}
