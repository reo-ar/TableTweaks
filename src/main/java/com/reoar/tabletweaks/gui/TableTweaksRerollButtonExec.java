package com.reoar.tabletweaks.gui;

import com.reoar.tabletweaks.TableTweaks;
import com.reoar.tabletweaks.packets.PacketReroll;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TableTweaksRerollButtonExec extends TableTweaksRerollButton {

    public TableTweaksRerollButtonExec(int x, int y) {
        super(x, y, 0, 0, btn -> TableTweaks.NET_INST.sendToServer(new PacketReroll()));
    }
}
