/*
 * Hytils Reborn - Hypixel focused Quality of Life mod.
 * Copyright (C) 2022  W-OVERFLOW
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cc.woverflow.hytils.handlers.chat.modules.triggers;

import cc.woverflow.hytils.HytilsReborn;
import cc.woverflow.hytils.config.HytilsConfig;
import gg.essential.api.utils.Multithreading;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class AutoWB {
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (HytilsConfig.AutoWB != 0) {
            String msg = event.message.getFormattedText().trim();
            Matcher matcher = HytilsReborn.INSTANCE.getLanguageHandler().getCurrent().chatRestylerStatusPatternRegex.matcher(msg);
            if (matcher.matches()) {
                // TODO: eventually replace with a better option that allows user to pick one of the 10 options
                String message = HytilsConfig.AutoWBsendMessage1.replace("%player%", matcher.group("player"));

                boolean isGuild = false;
                boolean isFriend = false;
                String chatType = null;

                switch (HytilsConfig.AutoWB) {
                    case 1:
                        isGuild = true;
                        break;
                    case 2:
                        isFriend = true;
                        break;
                    case 3:
                        isGuild = true;
                        isFriend = true;
                        break;
                }

                if ((matcher.group("type").equals("§2Guild") || matcher.group("type").equals("§2§2G")) && matcher.group("status").equals("joined") && isGuild) {
                    chatType = "/gc ";
                }
                if ((matcher.group("type").equals("§aFriend") || matcher.group("type").equals("§a§aF")) && matcher.group("status").equals("joined") && isFriend) {
                    chatType = "/msg " + matcher.group("player" + " ");
                }

                String finalChatType = chatType;

                if (HytilsConfig.randomAutoWB) {
                    int r = (int) (Math.random() * 10);
                    while (true) {
                        String sendMessage = new String[]{
                            HytilsConfig.AutoWBsendMessage1,
                            HytilsConfig.AutoWBsendMessage2,
                            HytilsConfig.AutoWBsendMessage3,
                            HytilsConfig.AutoWBsendMessage4,
                            HytilsConfig.AutoWBsendMessage5,
                            HytilsConfig.AutoWBsendMessage6,
                            HytilsConfig.AutoWBsendMessage7,
                            HytilsConfig.AutoWBsendMessage8,
                            HytilsConfig.AutoWBsendMessage9,
                            HytilsConfig.AutoWBsendMessage10
                        }[r].replace("%player%", matcher.group("player"));
                        if (!sendMessage.equals("")) {
                            Multithreading.schedule(() -> {
                                Minecraft.getMinecraft().thePlayer.sendChatMessage(
                                    finalChatType + sendMessage
                                );
                            }, HytilsConfig.AutoWBsendSeconds, TimeUnit.SECONDS);
                            break;
                        }

                    }
                } else {
                    Multithreading.schedule(() -> {
                        Minecraft.getMinecraft().thePlayer.sendChatMessage(
                            finalChatType + message
                        );
                    }, HytilsConfig.AutoWBsendSeconds, TimeUnit.SECONDS);
                }
            }
        }
    }
}
