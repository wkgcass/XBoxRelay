package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.ui.KeyOrMouse;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record BindingConfiguration(
    Supplier<KeyOrMouse> getter,
    Consumer<KeyOrMouse> setter
) {
}
