package dev.akoto.tiabgtmcompat

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.fml.common.Mod

@Mod(TiabGtmCompatMod.MOD_ID)
object TiabGtmCompatMod {
    const val MOD_ID = "tiab_gtm_compat"

    val REGISTRATE: GTRegistrate = GTRegistrate.create(MOD_ID)

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
}
