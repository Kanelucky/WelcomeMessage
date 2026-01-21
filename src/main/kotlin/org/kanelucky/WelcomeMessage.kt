package org.kanelucky

import org.allaymc.api.plugin.Plugin
import org.allaymc.api.server.Server
import org.allaymc.api.utils.TextFormat
import org.allaymc.api.utils.config.Config
import org.allaymc.api.utils.config.ConfigSection
import org.kanelucky.listener.onJoin
import java.io.File

class WelcomeMessage : Plugin() {
    private lateinit var config: Config
    override fun onEnable() {
        val configFile = File(pluginContainer.dataFolder.toFile(), "config.yml")
        val defaults = ConfigSection()
        defaults.set("title", "Welcome to the server!")
        defaults.set("subtitle", "Play your way")
        config = Config(configFile, Config.YAML, defaults)
        Server.getInstance().eventBus.registerListener(onJoin(config))
        pluginLogger.info("${TextFormat.GREEN}Enabled successfully!")
    }
    override fun onDisable() {
        pluginLogger.info("${TextFormat.RED}Disabled successfully!")
    }
}