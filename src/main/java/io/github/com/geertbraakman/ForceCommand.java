package io.github.com.geertbraakman;


import io.github.geertbraakman.api.APIPlugin;
import io.github.geertbraakman.api.command.APICommand;
import io.github.geertbraakman.api.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ForceCommand extends APICommand {

    public ForceCommand(APIPlugin plugin, String command) {
        super(plugin, command);
        getMessageHandler().setDefaultMessage("success", "&aForced &e%player% &ato chat: &7%message%&a!");
        getMessageHandler().setDefaultMessage("notOnline","&cThe player &7%player% &cis not online!");
        getMessageHandler().setDefaultMessage("invalidArguments", "&cInvalid arguments! Use /forcechat <player> <message>");
        getMessageHandler().setDefaultMessage("prefix", "&8[&2&lForceChat&8]");
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = null;
        if(sender instanceof Player) {
            player = (Player) sender;
        }

        HashMap<String, String> map = new HashMap<>();

        if(args.length < 2){
            sender.sendMessage(getMessageHandler().getMessage("invalidArguments", player));
            return true;
        }

        String playerName = args[0];
        map.put("%player%", playerName);
        args = removeFirstArgument(args);
        Player target = Bukkit.getPlayer(playerName);
        if(target == null) {
            sender.sendMessage(getMessageHandler().getMessage("invalidArguments", player, map));
            return true;
        }

        StringBuilder message = new StringBuilder();
        for(int i = 0; i < args.length; i++){
            message.append(args[i]);
            if(i < args.length -1){
                message.append(" ");
            }
        }
        map.put("%message%", message.toString());

        target.chat(Util.updatePlaceholders(map.get("%message%"), target, null));

        sender.sendMessage(getMessageHandler().getMessage("success", player, map));
        return true;
    }
}
