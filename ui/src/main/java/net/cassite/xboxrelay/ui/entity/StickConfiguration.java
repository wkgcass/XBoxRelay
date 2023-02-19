package net.cassite.xboxrelay.ui.entity;

import net.cassite.xboxrelay.ui.KeyOrMouse;

import java.util.function.*;

public record StickConfiguration(Supplier<KeyOrMouse> xMinGetter,
                                 Supplier<KeyOrMouse> xMaxGetter,
                                 Supplier<KeyOrMouse> xBMinGetter,
                                 Supplier<KeyOrMouse> xBMaxGetter,
                                 Supplier<KeyOrMouse> yMinGetter,
                                 Supplier<KeyOrMouse> yMaxGetter,
                                 Supplier<KeyOrMouse> yBMinGetter,
                                 Supplier<KeyOrMouse> yBMaxGetter,
                                 Consumer<KeyOrMouse> xMinSetter,
                                 Consumer<KeyOrMouse> xMaxSetter,
                                 Consumer<KeyOrMouse> xBMinSetter,
                                 Consumer<KeyOrMouse> xBMaxSetter,
                                 Consumer<KeyOrMouse> yMinSetter,
                                 Consumer<KeyOrMouse> yMaxSetter,
                                 Consumer<KeyOrMouse> yBMinSetter,
                                 Consumer<KeyOrMouse> yBMaxSetter,
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
