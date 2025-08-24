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

package com.ravoxx.discordChatBridge.bridge;

import java.util.EnumSet;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.ravoxx.discordChatBridge.config.PluginConfig;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BridgeService extends ListenerAdapter {
    private final Plugin plugin;
    private final PluginConfig cfg;
    private JDA jda;

    public BridgeService(Plugin plugin, PluginConfig cfg) {
        this.plugin = plugin;
        this.cfg = cfg;
    }

    public boolean start() {
        if (!cfg.isReady()) return false;
        try {
        EnumSet<GatewayIntent> intents = EnumSet.of(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_MEMBERS
        );
        jda = JDABuilder.createDefault(cfg.token(), intents).addEventListeners(this).build();
            return true;
        } catch (Exception e) {
        String msg = e.getMessage();
        if (msg == null || msg.isEmpty()) msg = e.getClass().getSimpleName();
        plugin.getLogger().log(Level.WARNING, "Discord Bot could not start: {0}", msg);
            return false;
        }
    }

    public void shutdown() {
        if (jda != null) jda.shutdownNow();
    }

    public void sendToDiscord(String user, String message) {
        if (jda == null) return;
        TextChannel ch = jda.getTextChannelById(cfg.channelId());
        if (ch == null) return;
        String out = cfg.mcToDiscordFormat().replace("<user>", user).replace("<message>", message);
        ch.sendMessage(out).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getChannel().getIdLong() != cfg.channelId()) return;
        String user = event.getAuthor().getName();
        String content = event.getMessage().getContentDisplay();
        String line = cfg.discordToMcFormat().replace("<user>", user).replace("<message>", content);
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(line)));
    }
}
