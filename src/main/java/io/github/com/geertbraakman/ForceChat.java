package io.github.com.geertbraakman;

import io.github.com.geertbraakman.api.command.APICommand;
import io.github.com.geertbraakman.api.command.CommandHandler;
import io.github.com.geertbraakman.api.config.APIConfig;
import io.github.com.geertbraakman.api.config.ConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class ForceChat extends JavaPlugin {

    private APIConfig config;
    private ForceCommand command;
    private ConfigHandler configHandler;

    @Override
    public void onEnable(){
        configHandler = ConfigHandler.getInstance(this);
        config = new APIConfig(this, "config");
        configHandler.registerConfig(config);
        command = new ForceCommand(this, "forcechat", (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null));
        reloadCommand(command);
        APICommand cmd = getReloadCommand();
        cmd.setPermission("forcechat.reload");
        command.addSubCommand(cmd);
        new CommandHandler(this).registerCommand(this, command);
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
            successMessage = config.getFileConfiguration().getString("command.messages.success");
            notOnlineMessage = config.getFileConfiguration().getString("command.messages.notOnline");
            invalidArgumentsMessage = config.getFileConfiguration().getString("command.messages.invalidArguments");
        }

        command.setAliases(commands);
        if(!permission.equals("none")){
            command.setPermissionMessage(permission);
        }
        command.setPermission(permission);
        if(noPermMessage != null && !noPermMessage.equals("default")){
            command.setPermissionMessage(noPermMessage);
        }
        if(successMessage != null && !successMessage.equals("default")){
            command.setSuccessMessage(successMessage);
        }
        if(notOnlineMessage != null && !notOnlineMessage.equals("default")){
            command.setNotOnlineMessage(notOnlineMessage);
        }
        if(invalidArgumentsMessage != null && !invalidArgumentsMessage.equals("default")){
            command.setInvalidArgumentsMessage(invalidArgumentsMessage);
        }
        command.setDescription("This command will force someone to chat what you want!");
        command.setUsage("forcechat <player> <message>");
    }

    private APICommand getReloadCommand(){
        return new APICommand(this, "reload"){

            public boolean onCommand(CommandSender commandSender, Command command2, String s, String[] strings) {
                if(!configHandler.reload()){
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
