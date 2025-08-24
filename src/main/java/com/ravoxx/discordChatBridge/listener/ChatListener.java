package com.ravoxx.discordChatBridge.listener;

import com.ravoxx.discordChatBridge.bridge.BridgeService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final BridgeService bridge;
    public ChatListener(BridgeService bridge) {
        this.bridge = bridge;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        bridge.sendToDiscord(e.getPlayer().getName(), e.getMessage());
    }
}
