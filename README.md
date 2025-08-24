## DiscordChatBridge

Simple Discord ↔ Minecraft chat bridge.

Quick steps

1. Put the jar in your server `plugins` folder.
2. Start the server once to create `plugins/DiscordChatBridge/config.yml`.
3. Edit `config.yml`: set `discord.token` and `discord.channelId`.
4. In the Discord Developer Portal enable Message Content intent for the bot.
5. Restart the server.

Build (for devs)

Run this in PowerShell to build the plugin jar:

```powershell
./gradlew shadowJar
```

Jar is at `build/libs/DiscordChatBridge-1.0-SNAPSHOT.jar`.

Quick troubleshooting

- Bot won't log in: check the token.
- No messages from Discord: enable Message Content intent and make sure the bot can see the channel.

That's it.
