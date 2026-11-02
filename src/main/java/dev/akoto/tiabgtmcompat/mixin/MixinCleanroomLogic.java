package dev.akoto.tiabgtmcompat.mixin;

import com.gregtechceu.gtceu.api.machine.trait.recipe.RecipeLogic;
import com.gregtechceu.gtceu.common.machine.trait.CleanroomLogic;

import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CleanroomLogic.class, remap = false)
public class MixinCleanroomLogic {

    @Unique
    private long tiab_lastCleanEnergyTick = -1L;

    @Unique
    private boolean tiab_cleanroomLastSucceeded = false;

    @Inject(method = "serverTick", at = @At("HEAD"), cancellable = true)
    private void tiab_onCleanroomTick(CallbackInfo ci) {
        CleanroomLogic self = (CleanroomLogic) (Object) this;
        Level level = self.getMachine().getLevel();
        if (level == null || level.isClientSide()) return;

        if (level.getGameTime() == tiab_lastCleanEnergyTick) {
            if (tiab_cleanroomLastSucceeded && self.getDuration() > 0) {
                self.setProgress(self.getProgress() + 1);
            }
            ci.cancel();
        }
    }

    @Inject(method = "serverTick", at = @At("RETURN"))
    private void tiab_afterCleanroomTick(CallbackInfo ci) {
        CleanroomLogic self = (CleanroomLogic) (Object) this;
        Level level = self.getMachine().getLevel();
        if (level == null || level.isClientSide()) return;

        tiab_lastCleanEnergyTick = level.getGameTime();
        tiab_cleanroomLastSucceeded = (self.getStatus() == RecipeLogic.Status.WORKING);
    }
}
