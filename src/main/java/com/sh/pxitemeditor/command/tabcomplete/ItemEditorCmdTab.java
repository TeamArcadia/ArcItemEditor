package com.sh.pxitemeditor.command.tabcomplete;

import com.sh.pxitemeditor.PXItemEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ItemEditorCmdTab implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;

        if (args.length == 1) {
            if (label.equalsIgnoreCase("ie")) {
                if (PXItemEditor.getInstance().hasPermission(player, "reload")) completions.add("reload");
                if (PXItemEditor.getInstance().hasPermission(player, "name")) completions.add("name");
                if (PXItemEditor.getInstance().hasPermission(player, "lore")) completions.add("lore");
                if (PXItemEditor.getInstance().hasPermission(player, "custommodeldata"))
                    completions.add("custommodeldata");
                if (PXItemEditor.getInstance().hasPermission(player, "enchant")) completions.add("enchant");
                if (PXItemEditor.getInstance().hasPermission(player, "unbreakable"))
                    completions.add("unbreakable");

            } else if (label.equalsIgnoreCase("아이템편집")) {
                if (PXItemEditor.getInstance().hasPermission(player, "reload")) completions.add("리로드");
                if (PXItemEditor.getInstance().hasPermission(player, "name")) completions.add("이름");
                if (PXItemEditor.getInstance().hasPermission(player, "lore")) completions.add("로어");
                if (PXItemEditor.getInstance().hasPermission(player, "custommodeldata"))
                    completions.add("커스텀모델데이터");
                if (PXItemEditor.getInstance().hasPermission(player, "enchant")) completions.add("인챈트");
                if (PXItemEditor.getInstance().hasPermission(player, "unbreakable"))
                    completions.add("내구도무한");
            }
        } else if (args.length == 2) {
            if ("lore".equalsIgnoreCase(args[0])) {
                completions.addAll(Arrays.asList("add", "set", "delete"));
            } else if ("로어".equalsIgnoreCase(args[0])) {
                completions.addAll(Arrays.asList("추가", "설정", "삭제"));
            }
            if (("custommodeldata".equalsIgnoreCase(args[0]))) {
                completions.add("<modelnumber>");
            } else if (("커스텀모델데이터".equalsIgnoreCase(args[0]))) {
                completions.add("<모델 번호>");
            }
            if (("enchant".equalsIgnoreCase(args[0]))) {
                completions.addAll(Arrays.asList("add", "delete"));
            } else if (("인챈트".equalsIgnoreCase(args[0]))) {
                completions.addAll(Arrays.asList("추가", "삭제"));
            }
            if ("unbreakable".equalsIgnoreCase(args[0])) {
                completions.addAll(Arrays.asList("true", "false"));
                completions.add("true");
                completions.add("false");
            } else if ("내구도무한".equalsIgnoreCase(args[0])) {
                completions.addAll(Arrays.asList("활성화", "비활성화"));
            }

        } else if (args.length == 3) {
            if (("lore".equalsIgnoreCase(args[0]) && "set".equalsIgnoreCase(args[1])) || ("lore".equalsIgnoreCase(args[0]) && "delete".equalsIgnoreCase(args[1]))) {
                completions.add("<line number>");
            } else if (("로어".equalsIgnoreCase(args[0]) && "설정".equalsIgnoreCase(args[1])) || ("로어".equalsIgnoreCase(args[0]) && "삭제".equalsIgnoreCase(args[1]))) {
                completions.add("<줄 번호>");
            }
            if (("lore".equalsIgnoreCase(args[0]) && "add".equalsIgnoreCase(args[1]))) {
                completions.add("<content>");
            } else if (("로어".equalsIgnoreCase(args[0]) && "추가".equalsIgnoreCase(args[1]))) {
                completions.add("<내용>");
            }
            if (("enchant".equalsIgnoreCase(args[0]) && "add".equalsIgnoreCase(args[1])) || ("인챈트".equalsIgnoreCase(args[0]) && "추가".equalsIgnoreCase(args[1]))) {
                Set<Enchantment> enchantments = Set.of(Enchantment.values());
                List<String> enchantmentNames = enchantments.stream()
                        .map(enchantment -> enchantment.getKey().toString().replace("minecraft:", ""))
                        .toList();
                completions.addAll(enchantmentNames);
            } else if (("enchant".equalsIgnoreCase(args[0]) && "delete".equalsIgnoreCase(args[1])) || ("인챈트".equalsIgnoreCase(args[0]) && "삭제".equalsIgnoreCase(args[1]))) {
                Set<Enchantment> enchantments = Set.of(Enchantment.values());
                List<String> enchantmentNames = enchantments.stream()
                        .map(enchantment -> enchantment.getKey().toString().replace("minecraft:", ""))
                        .toList();
                completions.addAll(enchantmentNames);
            }

        } else if (args.length == 4) {
           if (("lore".equalsIgnoreCase(args[0]) && "set".equalsIgnoreCase(args[1]))) {
               completions.add("<content>");
           } else if (("로어".equalsIgnoreCase(args[0]) && "설정".equalsIgnoreCase(args[1]))) {
               completions.add("<내용>");
           }
           if (("enchant".equalsIgnoreCase(args[0]) && "add".equalsIgnoreCase(args[1]))) {
               completions.add("<level>");
           } else if (("인챈트".equalsIgnoreCase(args[0]) && "추가".equalsIgnoreCase(args[1]))) {
               completions.add("<레벨>");
           }
        }

        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}
