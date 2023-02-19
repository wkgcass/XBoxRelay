package net.cassite.xboxrelay.ui;

import io.vproxy.vfx.util.Singleton;

public interface I18n {
    static I18n get() {
        return Singleton.registerIfAbsent(I18n.class, () -> new I18n() {
        });
    }

    default String title() {
        return "XBox Relay UI";
    }

    default String agentAddressLabel() {
        return "Agent Address";
    }

    default String disconnectButton() {
        return "Disconnect";
    }

    default String connectButton() {
        return "Connect";
    }

    default String disconnectedAlert() {
        return "Disconnected with the agent";
    }

    default String planLabel() {
        return "Plan";
    }

    default String savePlanButton() {
        return "Save";
    }

    default String deletePlanButton() {
        return "Delete";
    }

    default String savingConfigurationFailed() {
        return "Saving configuration failed";
    }

    default String applyConfButton() {
        return "Ok";
    }

    default String enableKeyPress() {
        return "Key Press";
    }

    default String enableMouseMove() {
        return "Mouse Move";
    }

    default String enableMouseWheel() {
        return "Mouse Wheel";
    }

    default String invalidKey() {
        return "The chosen key is not valid";
    }

    default String invalidMoveX() {
        return "Mouse Move X is not valid";
    }

    default String invalidMoveY() {
        return "Mouse Move Y is not valid";
    }

    default String invalidMouseWheel() {
        return "Mouse Wheel Amount is not valid";
    }

    default String cannotOverwritePrebuiltPlan() {
        return "Cannot overwrite pre-built plan";
    }

    default String cannotDeletePlan() {
        return "Cannot delete this plan";
    }

    default String failedToStart() {
        return "Failed to start, please make sure the address is reachable and the agent is running";
    }
}
