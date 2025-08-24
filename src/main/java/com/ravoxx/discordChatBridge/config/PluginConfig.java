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

package com.ravoxx.discordChatBridge.config;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfig {
    private final String token;
    private final long channelId;
    private final String discordToMcFormat;
    private final String mcToDiscordFormat;

    public PluginConfig(FileConfiguration cfg) {
        this.token = cfg.getString("discord.token", "");
        this.channelId = cfg.getLong("discord.channelId", 0L);
        this.discordToMcFormat = cfg.getString("format.discordToMc", "(discord) <user>: <message>");
        this.mcToDiscordFormat = cfg.getString("format.mcToDiscord", "<user>: <message>");
    }

    public String token() { return token; }
    public long channelId() { return channelId; }
    public String discordToMcFormat() { return discordToMcFormat; }
    public String mcToDiscordFormat() { return mcToDiscordFormat; }

    public boolean isReady() { return token != null && !token.isEmpty() && channelId != 0L; }
}
