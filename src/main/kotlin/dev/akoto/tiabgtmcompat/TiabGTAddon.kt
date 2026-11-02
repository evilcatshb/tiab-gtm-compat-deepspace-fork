package dev.akoto.tiabgtmcompat

import com.gregtechceu.gtceu.api.addon.GTAddon
import com.gregtechceu.gtceu.api.addon.IGTAddon
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate

@GTAddon
class TiabGTAddon : IGTAddon {
    override fun getRegistrate(): GTRegistrate = TiabGtmCompatMod.REGISTRATE

    override fun initializeAddon() {}

    override fun addonModId(): String = TiabGtmCompatMod.MOD_ID
}
