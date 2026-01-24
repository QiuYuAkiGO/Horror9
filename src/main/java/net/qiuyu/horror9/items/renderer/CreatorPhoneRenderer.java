package net.qiuyu.horror9.items.renderer;

import net.qiuyu.horror9.items.custom.CreatorPhoneItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CreatorPhoneRenderer extends GeoItemRenderer<CreatorPhoneItem> {
    public CreatorPhoneRenderer() {
        super(new CreatorPhoneModel());
    }
}
