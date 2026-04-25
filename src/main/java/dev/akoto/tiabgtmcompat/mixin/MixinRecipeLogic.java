package dev.akoto.tiabgtmcompat.mixin;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;

import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RecipeLogic.class, remap = false)
public class MixinRecipeLogic {

    @Unique
    private long tiab_lastEnergyTick = -1L;

    @Unique
    private boolean tiab_lastTickSucceeded = false;

    @Inject(method = "handleRecipeWorking", at = @At("HEAD"), cancellable = true)
    private void tiab_onHandleRecipeWorking(CallbackInfo ci) {
        RecipeLogic self = (RecipeLogic) (Object) this;
        MetaMachine meta = self.getMachine();
        Level level = meta.getLevel();
        if (level == null || level.isClientSide()) return;

        if (level.getGameTime() == tiab_lastEnergyTick) {
            if (tiab_lastTickSucceeded) {
                self.setProgress(self.getProgress() + 1);
            }
            ci.cancel();
        }
    }

    @Inject(method = "handleRecipeWorking", at = @At("RETURN"))
    private void tiab_afterHandleRecipeWorking(CallbackInfo ci) {
        RecipeLogic self = (RecipeLogic) (Object) this;
        MetaMachine meta = self.getMachine();
        Level level = meta.getLevel();
        if (level == null || level.isClientSide()) return;

        tiab_lastEnergyTick = level.getGameTime();
        tiab_lastTickSucceeded = (self.getStatus() == RecipeLogic.Status.WORKING);
    }
}
