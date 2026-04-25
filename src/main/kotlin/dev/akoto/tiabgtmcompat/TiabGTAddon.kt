package dev.akoto.tiabgtmcompat

import com.gregtechceu.gtceu.api.addon.GTAddon
import com.gregtechceu.gtceu.api.addon.IGTAddon
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

@GTAddon
class TiabGTAddon : IGTAddon {
    override fun getRegistrate(): GTRegistrate = TiabGtmCompatMod.REGISTRATE

    override fun initializeAddon() {}

    override fun addonModId(): String = TiabGtmCompatMod.MOD_ID

    override fun registerTagPrefixes() {}

    override fun addRecipes(provider: Consumer<FinishedRecipe>) {}

    override fun registerElements() {}
}
