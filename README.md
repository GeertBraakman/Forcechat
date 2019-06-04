# ForceChat

This plugin is an easy to use sudo plugin, that will force a player to chat what you want them to chat. This can be used to troll players, or if you want to let a player do a command that contains a Placeholder from PAPI. It supports placeholders in all messages.

## Commands
| Command | Description | Permission | 
| ------- | ----------- | ---------- |
| `/forcechat <player> <message>` | Forces the player to chat | `forcechat.use` |
| `/forcechat reload` | Reloads the plugin | `forcechat.reload`

## Config
```YAML
command:

# The permission for the command, set to 'none' for no permission.
  permission: 'forcechat.use'

# The aliases for the command (the main command is always 'forcechat')
  aliases:
   - 'fc'

# Here you can set the messages for the plugin, use 'default' for the default message.
#
# Placeholders:
#  - %player% = the player that gets forced to chat.
#  - %message% = the message the player is forced to chat.
#  - All placeholders supported by PlaceholderAPI (https://www.spigotmc.org/resources/placeholderapi.6245/)
  messages:
    noPerm: default
    success: default
    notOnline: default
    invalidArguments: default
 
```

If you need any help contact me on discord:
**x313#0313**
