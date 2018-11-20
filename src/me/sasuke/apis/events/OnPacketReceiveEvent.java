package me.sasuke.apis.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.sasuke.reflection.Reflection;

public class OnPacketReceiveEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
	private Player player;
	private Object packet;
	public OnPacketReceiveEvent(Player player, Object packet) {
		this.player=player;
		this.packet=packet;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public Player getPlayer() {return player;}
	public Object getPacket() {return packet;}
	public Object getField(String field) {
		try {
			return Reflection.getFieldValue(getPacket(), field);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
