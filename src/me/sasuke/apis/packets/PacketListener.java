package me.sasuke.apis.packets;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import me.sasuke.apis.events.OnPacketReceiveEvent;

public class PacketListener implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e) {
		injectPlayer(e.getPlayer());
	}
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		removePlayer(e.getPlayer());
	}
	
	private void removePlayer(Player p) {
		Channel ch = ((CraftPlayer) p).getHandle().playerConnection.networkManager.channel;
		ch.eventLoop().submit(() -> {
			ch.pipeline().remove(p.getName());
			return null;
		});
		
	}
	public void injectPlayer(Player p) {
		ChannelDuplexHandler cDH = new ChannelDuplexHandler() {
			@Override
			public void channelRead(ChannelHandlerContext cHC, Object packet) throws Exception {
				Bukkit.getPluginManager().callEvent(new OnPacketReceiveEvent(p, packet));
				super.channelRead(cHC, packet);
			}
				
			@Override
			public void write(ChannelHandlerContext cHC, Object packet, ChannelPromise cP) throws Exception {
				super.write(cHC, packet, cP);
			}
		};
			
		Channel ch = ((CraftPlayer) p).getHandle().playerConnection.networkManager.channel;
		ChannelPipeline cpl = ch.pipeline();
		cpl.addBefore("packet_handler", p.getName(), cDH);
	}
	
}
