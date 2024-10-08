# Minecraft Server Whitelist Mod

![Minecraft Server Whitelist Mod](https://jacobwasbeast.net/assets/img/project-05.webp)

A Minecraft mod made for servers & clients to only allow progression when a certain amount of players are on the server.
When installed on the server only vanilla/other clients can't move drop, hit, or mine anything until the amount of players are met. Also server time DOES NOT progress when the players are not met, using this you could make minecraft days only progress when someone is on. This can be done by settings the neededPlayers to 1.
When installed on the client, the clients will only see the end background until the amount of players are met from the server config. The clients can't type, move or do anything but escape and leave.

## Overview

The Minecraft Server Whitelist Mod provides server administrators with the ability to control access to their Minecraft servers, ensuring a controlled and paced environment.

## Documentation

Right now there is only a config file located in ./config/serverwhitelist.toml
and the only option is neededPlayers
This sets how many players are needed on the server so progression can continue.
This can also be set with the command (serverwhitelist set [players])

## Preview
![preview](https://i.imgur.com/ywooj78.png)
