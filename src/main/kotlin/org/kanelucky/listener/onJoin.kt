package org.kanelucky.listener

import org.allaymc.api.eventbus.EventHandler
import org.allaymc.api.eventbus.event.server.PlayerJoinEvent
import org.allaymc.api.utils.TextFormat
import org.allaymc.api.utils.config.Config

class onJoin(private val config: Config) {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val title = config.getString("title", "")
        val subtitle = config.getString("subtitle", "")
        player.sendTitle(title)
        player.sendSubtitle(subtitle)
    }
}