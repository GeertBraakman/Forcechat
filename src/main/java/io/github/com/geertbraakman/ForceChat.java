package io.github.com.geertbraakman;



import io.github.geertbraakman.api.APIPlugin;
import io.github.geertbraakman.api.command.APICommand;
import io.github.geertbraakman.api.config.APIConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


import java.util.ArrayList;
import java.util.List;


public class ForceChat extends APIPlugin {

    private APIConfig config;
    private ForceCommand command;


    @Override
    public void onEnable(){

        config = new APIConfig(this, "config");
        getConfigHandler().registerConfig(config);
        command = new ForceCommand(this, "forcechat");
        reloadCommand(command);
        APICommand cmd = getReloadCommand();
        cmd.setPermission("forcechat.reload");
        command.addSubCommand(cmd);
        getCommandHandler().registerCommand(this, command);
    }

    private void reloadCommand(ForceCommand command){
        List<String> commands = new ArrayList<>();
        commands.add("fc");

        String permission = "forcechat.use";
        String noPermMessage = null;
        String successMessage = null;
        String notOnlineMessage = null;
        String invalidArgumentsMessage = null;


        if(config.getFileConfiguration() != null) {

            List<String> cmds = config.getFileConfiguration().getStringList("command.aliases");
            if(cmds.size() > 0){
                commands = cmds;
            }

            String perm = config.getFileConfiguration().getString("command.permission");
            if(perm != null && !perm.equals("")) {
                permission = perm;
            }

            noPermMessage = config.getFileConfiguration().getString("command.messages.noPerm");
        }

        command.setAliases(commands);
        if(!permission.equals("none")){
            command.setPermissionMessage(permission);
        }
        command.setPermission(permission);
        if(noPermMessage != null && !noPermMessage.equals("default")){
            command.setPermissionMessage(noPermMessage);
        }
        command.setDescription("This command will force someone to chat what you want!");
        command.setUsage("forcechat <player> <message>");
    }

    private APICommand getReloadCommand(){
        return new APICommand(this, "reload"){

            public boolean onCommand(CommandSender commandSender, Command command2, String s, String[] strings) {
                if(!getConfigHandler().reload()){
                    commandSender.sendMessage(ChatColor.RED + "There went something wrong while reloading the config!");
                    return true;
                }
                reloadCommand(command);
                commandSender.sendMessage(ChatColor.GREEN + "The config has been reloaded!");
                return true;
            }
        };
    }

}
