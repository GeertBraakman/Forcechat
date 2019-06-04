package io.github.com.geertbraakman;

import io.github.com.geertbraakman.api.command.APICommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ForceCommand extends APICommand {

    private boolean usePlaceholders;
    private String successMessage;
    private String notOnlineMessage;
    private String invalidArgumentsMessage;

    public ForceCommand(Plugin plugin, String command, boolean usePlaceholders) {
        super(plugin, command);
        this.usePlaceholders = usePlaceholders;
        successMessage = "&8[&2&lForceChat&8] &aForced &e%player% &ato chat: &7%message%&a!";
        notOnlineMessage = "&8[&2&lForceChat&8] &cThe player &7%player% &cis not online!";
        invalidArgumentsMessage = "&8[&2&lForceChat&8] &cInvalid arguments! Use /forcechat <player> <message>";
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length < 2){
            sendMessage(sender, invalidArgumentsMessage, "", "");
            return true;
        }

        String playername = args[0];
        args = removeFirstArgument(args);
        Player player = Bukkit.getPlayer(playername);
        if(player == null) {
            sendMessage(sender, notOnlineMessage, playername, "");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for(int i = 0; i < args.length; i++){
            message.append(args[i]);
            if(i < args.length -1){
                message.append(" ");
            }
        }

        player.chat(setPlaceholders(message.toString(), playername));

        sendMessage(sender, successMessage, playername, message.toString());
        return true;
    }

    public void setSuccessMessage(String successMessage){
        this.successMessage = successMessage;
    }

    public void setNotOnlineMessage(String notOnlineMessage) {
        this.notOnlineMessage = notOnlineMessage;
    }

    public void setInvalidArgumentsMessage(String invalidArgumentsMessage) {
        this.invalidArgumentsMessage = invalidArgumentsMessage;
    }

    private void sendMessage(CommandSender sender, String message, String playername, String msg){
        message = message.replace("%player%", playername).replace("%message%", msg);
        message = setPlaceholders(message, playername);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private String setPlaceholders(String message, String playername) {
        if(usePlaceholders){
            Player player = Bukkit.getPlayer(playername);
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

}
