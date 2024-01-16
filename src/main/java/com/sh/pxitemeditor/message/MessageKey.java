package com.sh.pxitemeditor.message;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageKey {
    /* --------------- NORMAL ---------------*/
    PREFIX("normal.prefix"),
    RELOAD_CONFIG("normal.reload_config"),

    /* --------------- ERROR ---------------*/
    PLAYER_ONLY("error.player_only"),
    NO_PERMISSION("error.no_permission"),
    WRONG_COMMAND("error.wrong_command"),

    /* --------------- MAIN ---------------*/
    NAME_EDIT("main.name_edit"),
    LORE_EDIT("main.lore_edit"),
    CUSTOMMODELDATA_EDIT("main.custommodeldata_edit"),
    ENCHANT_ADD("main.enchant_add"),
    ENCHANT_DELETE("main.enchant_delete"),
    UNBREAKABLE_EDIT("main.unbreakable_edit"),
    BREAKABLE_EDIT("main.breakable_edit");

    private final String key;

}
