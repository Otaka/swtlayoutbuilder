package com.swtlayoutbuilder.borderlayout;

import static com.swtlayoutbuilder.borderlayout.BorderLayout.CENTER;
import static com.swtlayoutbuilder.borderlayout.BorderLayout.EAST;
import static com.swtlayoutbuilder.borderlayout.BorderLayout.NORTH;
import static com.swtlayoutbuilder.borderlayout.BorderLayout.SOUTH;
import static com.swtlayoutbuilder.borderlayout.BorderLayout.WEST;

public class BorderData {
    public final int position;

    public BorderData(int position) {
        if(!(position==NORTH || position==SOUTH || position==WEST || position==EAST || position==CENTER)){
            throw new IllegalArgumentException("BorderData position only can be one of BorderLayout.[NORTH, SOUTH, CENTER, WEST, EAST]");
        }
        this.position = position;
    }
}
