package com.sh.pxitemeditor.command;

import com.sh.pxitemeditor.PXItemEditor;
import com.sh.pxitemeditor.message.MessageKey;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemEditorCmd implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player player)) {
            sender.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.PLAYER_ONLY));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.WRONG_COMMAND));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "리로드", "reload" -> {
                if (!PXItemEditor.getInstance().hasPermission(player, "reload")) return false;

                PXItemEditor.getInstance().reloadConfig();

                player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.RELOAD_CONFIG));
            }
            // 이름, 로어, 커스텀모델데이터, 내구도 (부서지지 않음), 인챈트
            case "이름", "name" -> {
                if (!PXItemEditor.getInstance().hasPermission(player, "name")) return false;

                String name = args[1];

                ItemStack itemStack = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                itemStack.setItemMeta(itemMeta);

                player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.NAME_EDIT));
            }

            case "로어", "lore" -> {
                if (!PXItemEditor.getInstance().hasPermission(player, "lore")) return false;

                switch (args[1].toLowerCase()) {
                    case "추가", "add" -> {

                        String lore = args[2];

                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        List<String> lores = itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>();

                        lores.add(ChatColor.translateAlternateColorCodes('&', lore));
                        itemMeta.setLore(lores);
                        itemStack.setItemMeta(itemMeta);

                        player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.LORE_EDIT));
                    }

                    case "설정", "set" -> {

                        int line = Integer.parseInt(args[2]);

                        String content = args[3];

                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        List<String> lores = itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>();

                        lores.set(line - 1, ChatColor.translateAlternateColorCodes('&', content));
                        itemMeta.setLore(lores);
                        itemStack.setItemMeta(itemMeta);

                        player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.LORE_EDIT));
                    }
                    // /아이템편집 로어 삭제 줄수
                    case "삭제", "delete" -> {

                        int line = Integer.parseInt(args[2]);

                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        List<String> lores = itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>();

                        lores.remove(line - 1);
                        itemMeta.setLore(lores);
                        itemStack.setItemMeta(itemMeta);

                        player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.LORE_EDIT));
                    }
                }
            }

            case "커스텀모델데이터", "custommodeldata" -> {
                if (!PXItemEditor.getInstance().hasPermission(player, "custommodeldata")) return false;

                int customData = Integer.parseInt(args[1]);

                ItemStack itemStack = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(customData);
                itemStack.setItemMeta(itemMeta);

                player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.CUSTOMMODELDATA_EDIT).replace("{custommodeldata}", args[1]));
            }
            // /아이템편집 인챈트 <추가/삭제> <인챈트ID> <값>
            case "인챈트", "enchant" -> {
                if (!PXItemEditor.getInstance().hasPermission(player, "enchant")) return false;
                switch (args[1].toLowerCase()) {
                    case "추가", "add" -> {

                        String enchantName = args[2];

                        int level;

                        // 레벨 예외 처리
                        try {
                            level = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            player.sendMessage("유효하지 않은 레벨입니다.");
                            return false;
                        }

                        // 존재하지 않는 인챈트 처리
                        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantName.toLowerCase()));
                        if (enchantment == null) {
                            player.sendMessage("존재하지 않는 인챈트입니다.");
                            return false;
                        }

                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        // 기존 인챈트 유지를 위한 처리
                        //Map<Enchantment, Integer> existingEnchants = itemMeta.getEnchants();
                        //existingEnchants.put(enchantment, level);

                        itemMeta.addEnchant(enchantment, level, true);
                        itemStack.setItemMeta(itemMeta);

                        player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.ENCHANT_ADD).replace("{enchantment}", args[2]).replace("{level}", args[3]));
                    }

                    case "삭제", "delete" -> {

                        String enchantName = args[2];

                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantName.toLowerCase()));
                        if (enchantment != null && itemMeta.hasEnchant(enchantment)) {
                            itemMeta.removeEnchant(enchantment);
                            player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.ENCHANT_DELETE).replace("{enchantment}", args[2]));
                        } else {
                            player.sendMessage("존재하지 않는 인챈트입니다.");
                        }
                        itemStack.setItemMeta(itemMeta);
                        return true;
                    }
                }
            }

            case "내구도무한", "unbreakable" -> {
                if (!PXItemEditor.getInstance().hasPermission(player, "unbreakable")) return false;

                switch (args[1].toLowerCase()) {
                    case "활성화", "true" -> {

                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setUnbreakable(true);
                        itemStack.setItemMeta(itemMeta);

                        player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.UNBREAKABLE_EDIT));
                    }

                    case "비활성화", "false" -> {

                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setUnbreakable(false);
                        itemStack.setItemMeta(itemMeta);

                        player.sendMessage(PXItemEditor.getInstance().getMessageManager().getMessage(MessageKey.BREAKABLE_EDIT));
                    }
                }

            }
        }
        return false;
    }
}

