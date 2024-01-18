package com.sh.pxitemeditor.message;

import com.sh.pxitemeditor.PXItemEditor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    private Map<MessageKey, String> messageMap = new HashMap<>();

    public void initialize() {
        messageMap.clear();
        FileConfiguration config = PXItemEditor.getInstance().getConfig();

        ConfigurationSection messageSection = config.getConfigurationSection("message");

        for (MessageKey key : MessageKey.values()) {
            String message = messageSection.getString(key.getKey());
            messageMap.put(key, (message == null) ? "Invalid Message." : message);
        }
    }

    private String getPrefix() {
        return messageMap.get(MessageKey.PREFIX);
    }

    public String getMessage(MessageKey key) {
        return ChatColor.translateAlternateColorCodes('&', getPrefix() + messageMap.get(key));
    }


}
