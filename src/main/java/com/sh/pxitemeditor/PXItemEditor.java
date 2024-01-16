package com.sh.pxitemeditor;

import com.sh.pxitemeditor.command.ItemEditorCmd;
import com.sh.pxitemeditor.command.tabcomplete.ItemEditorCmdTab;
import com.sh.pxitemeditor.message.MessageKey;
import com.sh.pxitemeditor.message.MessageManager;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PXItemEditor extends JavaPlugin {

    @Getter
    private MessageManager messageManager;

    @Getter
    public static PXItemEditor instance;

    @Override
    public void onEnable() {
        getLogger().info("플러그인이 활성화 되었습니다.");
        saveDefaultConfig();
        instance = this;

        /* --------------- Message ---------------*/
        messageManager = new MessageManager();
        messageManager.initialize();

        /* --------------- COMMAND ---------------*/
        getServer().getPluginCommand("ie").setExecutor(new ItemEditorCmd());
        getServer().getPluginCommand("ie").setTabCompleter(new ItemEditorCmdTab());
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        getLogger().info("플러그인이 비활성화 되었습니다.");
        // Plugin shutdown logic
    }

    public Boolean hasPermission(Player player, String permission) {
        if (player.hasPermission("px.itemeditor." + permission)) {
            return true;
        } else {
            player.sendMessage(getMessageManager().getMessage(MessageKey.NO_PERMISSION));
            return false;
        }
    }
}
