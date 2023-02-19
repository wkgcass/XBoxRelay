package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.ui.KeyOrMouse;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public record TriggerConfiguration(Supplier<KeyOrMouse> minGetter,
                                   Supplier<KeyOrMouse> maxGetter,
                                   Consumer<KeyOrMouse> minSetter,
                                   Consumer<KeyOrMouse> maxSetter,
                                   IntSupplier minPosGetter,
                                   IntSupplier maxPosGetter,
                                   IntConsumer minPosSetter,
                                   IntConsumer maxPosSetter) {
}
