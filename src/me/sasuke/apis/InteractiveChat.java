package me.sasuke.apis;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class InteractiveChat {

	private TextComponent tc;
	private ArrayList<TextComponent> cs;
	public InteractiveChat(String str) {
		tc = new TextComponent();
		cs = new ArrayList<>();
		cs.add(new TextComponent(str));
	}
	
	public InteractiveChat add(String str) {
		TextComponent t = new TextComponent(str);
		cs.add(t);
		return this;
	}
	public InteractiveChat hover(HoverEvent.Action action, String str) {
		cs.get(cs.size()-1).setHoverEvent(new HoverEvent(action, new ComponentBuilder(str).create()));
		return this;
	}
	public InteractiveChat click(ClickEvent.Action action, String str) {
		cs.get(cs.size()-1).setClickEvent(new ClickEvent(action, str));
		return this;
	}
	public void send(Player p) {
		cs.forEach(tc::addExtra);
		p.sendMessage(tc);
	}
	
}
