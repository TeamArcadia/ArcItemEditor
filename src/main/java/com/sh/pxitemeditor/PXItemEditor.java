package com.sh.pxitemeditor;

import com.sh.pxitemeditor.command.ItemEditorCmd;
import com.sh.pxitemeditor.command.tabcomplete.ItemEditorCmdTab;
import com.sh.pxitemeditor.message.MessageKey;
import com.sh.pxitemeditor.message.MessageManager;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PXItemEditor extends JavaPlugin {

    @Getter
    private static PXItemEditor instance;

    @Getter
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        getLogger().info("플러그인이 활성화 되었습니다.");
        saveDefaultConfig();
        instance = this;

        /* --------------- Message ---------------*/
        messageManager = new MessageManager();
        messageManager.initialize();

        /* --------------- COMMAND ---------------*/
        getCommand("ie").setExecutor(new ItemEditorCmd());
        getCommand("ie").setTabCompleter(new ItemEditorCmdTab());
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        getLogger().info("플러그인이 비활성화 되었습니다.");
        // Plugin shutdown logic
    }

    public boolean hasPermission(Player player, String permission) {
        if (player.hasPermission("arc.itemeditor." + permission)) {
            return true;
        } else {
            player.sendMessage(getMessageManager().getMessage(MessageKey.NO_PERMISSION));
            return false;
        }
    }
}
