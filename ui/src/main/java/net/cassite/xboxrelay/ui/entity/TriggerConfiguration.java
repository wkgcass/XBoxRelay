package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.ui.Action;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public record TriggerConfiguration(Supplier<Action> minGetter,
                                   Supplier<Action> maxGetter,
                                   Consumer<Action> minSetter,
                                   Consumer<Action> maxSetter,
                                   IntSupplier minPosGetter,
                                   IntSupplier maxPosGetter,
                                   IntConsumer minPosSetter,
                                   IntConsumer maxPosSetter) {
}
