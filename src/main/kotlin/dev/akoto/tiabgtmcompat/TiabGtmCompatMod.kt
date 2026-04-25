package dev.akoto.tiabgtmcompat

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent
import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import com.gregtechceu.gtceu.api.sound.SoundEntry
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(TiabGtmCompatMod.MOD_ID)
object TiabGtmCompatMod {
    const val MOD_ID = "tiab_gtm_compat"

    val REGISTRATE: GTRegistrate = GTRegistrate.create(MOD_ID)

    init {
        MOD_BUS.addListener(::addMaterialRegistries)
        MOD_BUS.addListener(::addMaterials)
        MOD_BUS.addListener(::modifyMaterials)
        MOD_BUS.addGenericListener(GTRecipeType::class.java, ::registerRecipeTypes)
        MOD_BUS.addGenericListener(MachineDefinition::class.java, ::registerMachines)
        MOD_BUS.addGenericListener(SoundEntry::class.java, ::registerSounds)

        MOD_BUS.register(REGISTRATE)
    }

    private fun addMaterialRegistries(event: MaterialRegistryEvent) {
        GTCEuAPI.materialManager.createRegistry(MOD_ID)
    }

    private fun addMaterials(event: MaterialEvent) {}

    private fun modifyMaterials(event: PostMaterialEvent) {}

    private fun registerRecipeTypes(event: GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType>) {}

    private fun registerMachines(event: GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition>) {}

    private fun registerSounds(event: GTCEuAPI.RegisterEvent<ResourceLocation, SoundEntry>) {}

    fun id(path: String): ResourceLocation = ResourceLocation(MOD_ID, path)
}
