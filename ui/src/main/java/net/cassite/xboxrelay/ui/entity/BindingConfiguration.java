package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.ui.Action;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record BindingConfiguration(
    Supplier<Action> getter,
    Consumer<Action> setter
) {
}
