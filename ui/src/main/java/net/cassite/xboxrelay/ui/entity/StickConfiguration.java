package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.ui.Action;

import java.util.function.*;

public record StickConfiguration(Supplier<Action> xMinGetter,
                                 Supplier<Action> xMaxGetter,
                                 Supplier<Action> xBMinGetter,
                                 Supplier<Action> xBMaxGetter,
                                 Supplier<Action> yMinGetter,
                                 Supplier<Action> yMaxGetter,
                                 Supplier<Action> yBMinGetter,
                                 Supplier<Action> yBMaxGetter,
                                 Consumer<Action> xMinSetter,
                                 Consumer<Action> xMaxSetter,
                                 Consumer<Action> xBMinSetter,
                                 Consumer<Action> xBMaxSetter,
                                 Consumer<Action> yMinSetter,
                                 Consumer<Action> yMaxSetter,
                                 Consumer<Action> yBMinSetter,
                                 Consumer<Action> yBMaxSetter,
                                 IntSupplier xMinPosGetter,
                                 IntSupplier xMaxPosGetter,
                                 IntSupplier xBMinPosGetter,
                                 IntSupplier xBMaxPosGetter,
                                 IntSupplier yMinPosGetter,
                                 IntSupplier yMaxPosGetter,
                                 IntSupplier yBMinPosGetter,
                                 IntSupplier yBMaxPosGetter,
                                 IntConsumer xMinPosSetter,
                                 IntConsumer xMaxPosSetter,
                                 IntConsumer xBMinPosSetter,
                                 IntConsumer xBMaxPosSetter,
                                 IntConsumer yMinPosSetter,
                                 IntConsumer yMaxPosSetter,
                                 IntConsumer yBMinPosSetter,
                                 IntConsumer yBMaxPosSetter,
                                 BindingConfiguration binding) {
}
