/*
 * This file is part of DiscordChatBridge - https://github.com/RavoxX/DiscordChatBridge.git
 * Copyright (C) 2025 RavoxX and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ravoxx.discordChatBridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ravoxx.discordChatBridge.bridge.BridgeService;
import com.ravoxx.discordChatBridge.config.PluginConfig;
import com.ravoxx.discordChatBridge.listener.ChatListener;

public final class DiscordChatBridge extends JavaPlugin {
    private BridgeService bridgeService;
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        pluginConfig = new PluginConfig(getConfig());
        bridgeService = new BridgeService(this, pluginConfig);
        if (bridgeService.start()) {
            Bukkit.getPluginManager().registerEvents(new ChatListener(bridgeService), this);
            getLogger().info("Bridge activated");
        } else {
            getLogger().warning("Bridge NOT active check your bot token and channel id");
        }
    }

    @Override
    public void onDisable() {
        if (bridgeService != null) bridgeService.shutdown();
    }
}
