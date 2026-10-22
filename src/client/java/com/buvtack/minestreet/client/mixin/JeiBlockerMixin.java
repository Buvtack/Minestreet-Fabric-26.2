package com.buvtack.minestreet.client.mixin;

import com.buvtack.minestreet.client.ModHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "mezz.jei.gui.overlay.IngredientListOverlayController", remap = false)
public class JeiBlockerMixin {

    @Inject(
            method = "hasValidScreen",
            at = @At("HEAD"),
            cancellable = true
    )
    private void setScreenInvalid(CallbackInfoReturnable<Boolean> cir) {
        if (ModHelper.isTradingStation())
            cir.setReturnValue(false);
    }

    @Inject(
            method = "isListDisplayed",
            at = @At("HEAD"),
            cancellable = true
    )
    private void undisplayList(CallbackInfoReturnable<Boolean> cir) {
        if (ModHelper.isTradingStation())
            cir.setReturnValue(false);
    }
}
