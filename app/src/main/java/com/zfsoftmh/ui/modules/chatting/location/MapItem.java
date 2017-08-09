package com.zfsoftmh.ui.modules.chatting.location;

import com.amap.api.services.core.PoiItem;

/**
 * Created by ljq
 * on 2017/6/5.
 */

class MapItem {

    private PoiItem mPoiItem;
    private boolean isSelected;

    public MapItem(PoiItem item) {
        this.mPoiItem = item;
    }

    public PoiItem getmPoiItem() {
        return mPoiItem;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
