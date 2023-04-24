package net.cassite.xboxrelay.ui;

import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.HPadding;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.pane.FusionPane;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneGroup;
import io.vproxy.vfx.ui.scene.VSceneHideMethod;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.table.CellAware;
import io.vproxy.vfx.ui.table.VTableCellPane;
import io.vproxy.vfx.ui.table.VTableColumn;
import io.vproxy.vfx.ui.table.VTableView;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import net.cassite.xboxrelay.ui.entity.Plan;

import java.util.Arrays;

public class ShowConfigTableScene extends VScene {
    private VSceneGroup sceneGroup;

    public ShowConfigTableScene(VSceneGroup sceneGroup, Plan plan) {
        super(VSceneRole.TEMPORARY);
        this.sceneGroup = sceneGroup;

        enableAutoContentWidthHeight();
        getNode().setBackground(new Background(new BackgroundFill(
            Theme.current().sceneBackgroundColor(),
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        var table = new VTableView<TableData>();
        var nameCol = new VTableColumn<TableData, String>(I18n.get().tableColName(), d -> d.name);
        var actionMinCol = new VTableColumn<TableData, String>(I18n.get().tableColActionMin(), d -> d.actionMin);
        var actionMaxCol = new VTableColumn<TableData, String>(I18n.get().tableColActionMax(), d -> d.actionMax);
        var minCol = new VTableColumn<TableData, Integer>(I18n.get().tableColMin(), d -> d.min);
        var maxCol = new VTableColumn<TableData, Integer>(I18n.get().tableColMax(), d -> d.max);

        //noinspection unchecked
        table.getColumns().addAll(nameCol, actionMinCol, actionMaxCol, minCol, maxCol);
        nameCol.setAlignment(Pos.CENTER_RIGHT);
        nameCol.setMaxWidth(80);
        actionMinCol.setAlignment(Pos.CENTER);
        actionMaxCol.setAlignment(Pos.CENTER);
        minCol.setMaxWidth(60);
        minCol.setTextBuilder(n -> {
            if (n == -1) return "";
            return "" + n;
        });
        maxCol.setMaxWidth(60);
        maxCol.setTextBuilder(n -> {
            if (n == -1) return "";
            return "" + n;
        });
        var bottomPane = new FusionPane();
        bottomPane.getNode().setPrefHeight(60);
        FXUtils.observeWidthHeight(getContentPane(), table.getNode(), -20, -60 - 10 - 10 - 10);
        FXUtils.observeWidth(getContentPane(), bottomPane.getNode(), -20);
        getContentPane().getChildren().addAll(new HBox(
            new HPadding(10),
            new VBox(
                new VPadding(10),
                table.getNode(),
                new VPadding(10),
                bottomPane.getNode()
            )
        ));
        var back = new FusionButton(I18n.get().back());
        back.setPrefWidth(120);
        FXUtils.observeHeight(bottomPane.getContentPane(), back);
        bottomPane.getContentPane().getChildren().add(back);
        back.setOnAction(e -> hideAndRemove());

        var b = plan.binding;
        var d = plan.deadZoneSettings;
        table.setItems(Arrays.asList(
            new TableData("LSB X+", b.lsbXMin, b.lsbXMax, d.min.lsbX, d.max.lsbX),
            new TableData("LSB X-", b.lsbXBMin, b.lsbXBMax, d.min.lsbXB, d.max.lsbXB),
            new TableData("LSB Y+", b.lsbYMin, b.lsbYMax, d.min.lsbY, d.max.lsbY),
            new TableData("LSB Y-", b.lsbYBMin, b.lsbYBMax, d.min.lsbYB, d.max.lsbYB),
            new TableData("LSB", b.tl),
            new TableData("RSB X+", b.rsbXMin, b.rsbXMax, d.min.rsbX, d.max.rsbX),
            new TableData("RSB X-", b.rsbXBMin, b.rsbXBMax, d.min.rsbXB, d.max.rsbXB),
            new TableData("RSB Y+", b.rsbYMin, b.rsbYMax, d.min.rsbY, d.max.rsbY),
            new TableData("RSB Y-", b.rsbYBMin, b.rsbYBMax, d.min.rsbYB, d.max.rsbYB),
            new TableData("RSB", b.tr),
            new TableData("DU", b.du),
            new TableData("DD", b.dd),
            new TableData("DL", b.dl),
            new TableData("DR", b.dr),
            new TableData("Back", b.back),
            new TableData("Guide", b.guide),
            new TableData("Start", b.start),
            new TableData("A", b.a),
            new TableData("B", b.b),
            new TableData("X", b.x),
            new TableData("Y", b.y),
            new TableData("LB", b.lb),
            new TableData("RB", b.rb),
            new TableData("LT", b.ltMin, b.ltMax, d.min.lt, d.max.lt),
            new TableData("RT", b.rtMin, b.rtMax, d.min.rt, d.max.rt)
        ));
    }

    public void hideAndRemove() {
        sceneGroup.hide(this, VSceneHideMethod.TO_RIGHT);
        FXUtils.runDelay(ANIMATION_DURATION_MILLIS, () -> sceneGroup.removeScene(this));
    }

    private static class TableData implements CellAware<TableData> {
        final String name;
        final String actionMin;
        final String actionMax;
        final int min;
        final int max;

        private TableData(String name, Action actionMin) {
            this(name, actionMin == null ? "" : actionMin.toString());
        }

        private TableData(String name, String actionMin) {
            this(name, actionMin, "", -1, -1);
        }

        private TableData(String name,
                          Action actionMin,
                          Action actionMax,
                          int min, int max) {
            this(name,
                actionMin == null ? "" : actionMin.toString(),
                actionMax == null ? "" : actionMax.toString(),
                min, max);
        }

        private TableData(String name,
                          String actionMin,
                          String actionMax,
                          int min, int max) {
            this.name = name;
            this.actionMin = actionMin;
            this.actionMax = actionMax;
            this.min = min;
            this.max = max;
        }

        @Override
        public void setCell(VTableColumn<TableData, ?> col, VTableCellPane<TableData> pane) {
            if (actionMin.contains("\n") || actionMax.contains("\n"))
                pane.setMinHeight(45);
        }
    }
}
