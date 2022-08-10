package me.xemor.randomwars.PluginEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {
   private static final HandlerList HANDLERS = new HandlerList();
   private final Player player;

   public GameEndEvent(Player player) {
      this.player = player;
   }

   public HandlerList getHandlers() {
      return HANDLERS;
   }
}
