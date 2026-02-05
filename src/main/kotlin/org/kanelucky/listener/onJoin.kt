package org.kanelucky.listener

import org.allaymc.api.eventbus.EventHandler
import org.allaymc.api.eventbus.event.server.PlayerJoinEvent
import org.allaymc.api.utils.TextFormat
import org.allaymc.api.utils.config.Config

class onJoin(private val config: Config) {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val typeChat: Boolean = config.getBoolean("types.chat")
        val typeTitle: Boolean = config.getBoolean("types.title")
        val typeActionbar: Boolean = config.getBoolean("types.actionbar")
        if (typeTitle) {
            val title = config.getString("title.title", "")
            val subtitle = config.getString("title.subtitle", "")
            player.sendTitle(title)
            player.sendSubtitle(subtitle)
        }
        if (typeChat) {
            val message = config.getString("chat.message", "")
            player.sendMessage(message)
        }
        if (typeActionbar) {
            val message = config.getString("actionbar.message", "")
            player.sendActionBar(message)
        }
    }
}