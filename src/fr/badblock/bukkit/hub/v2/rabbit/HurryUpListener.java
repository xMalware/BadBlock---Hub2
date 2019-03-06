package fr.badblock.bukkit.hub.v2.rabbit;

import org.bukkit.Sound;

import fr.badblock.gameapi.technologies.RabbitAPIListener;
import fr.badblock.gameapi.technologies.RabbitListenerType;
import fr.badblock.gameapi.utils.BukkitUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class HurryUpListener extends RabbitAPIListener
{

	private long		lastInfo;
	
	public HurryUpListener()
	{
		super("hurryup", RabbitListenerType.SUBSCRIBER, false);
	}

	@Override
	public void onPacketReceiving(String body)
	{
		if (body == null)
		{
			return;
		}

		if (lastInfo > System.currentTimeMillis())
		{
			return;
		}

		
		lastInfo = System.currentTimeMillis() + 60_000L;
		
		String[] splitter = body.split(";");

		String prefix = splitter[1].split("_")[0];
		BukkitUtils.getAllPlayers().forEach(player -> {
			// "§eUne partie de Rush n'attend plus que toi !! §b§n[Clique pour rejoindre]"
			TextComponent textComponent = new TextComponent(player.getTranslatedMessage("hub.hurryup.message_" + prefix)[0]);
			TextComponent[] hover = new TextComponent[] { new TextComponent(player.getTranslatedMessage("hub.hurryup.hover")[0]) };
			textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
			textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + splitter[1]));
			player.spigot().sendMessage(textComponent);
			player.playSound(Sound.CAT_MEOW);
		});
	}

}