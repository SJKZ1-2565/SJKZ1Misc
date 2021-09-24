package com.sjkz1.minetils;


import com.google.common.collect.Lists;
import com.sjkz1.minetils.command.EntityDetector;
import com.sjkz1.minetils.command.OpenFolderCommand;
import com.sjkz1.minetils.config.ConFigIN;
import com.sjkz1.minetils.utils.ClientInit;
import com.sjkz1.minetils.utils.KeyBindInit;
import com.sjkz1.minetils.utils.SoundInits;
import com.sjkz1.minetils.utils.SpecialMember;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class Minetils implements ModInitializer
{
	public static KeyMapping danceKey;
	public static KeyMapping showPost;

	public static String MOD_ID = "minetils";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);


	public static final ConFigIN CONFIG = new ConFigIN();

	public static final List<String> SPECIAL_MEMBER = Lists.newCopyOnWriteArrayList();
	static {
		for(SpecialMember values : SpecialMember.VALUES) {
			SPECIAL_MEMBER.add(values.getName());
		}
	}

	@Override
	public void onInitialize()
	{
		KeyBindInit.init();
		ClientTickEvents.END_CLIENT_TICK.register(ClientInit::tick);
		SoundInits.init();
		new OpenFolderCommand(ClientCommandManager.DISPATCHER);
		new EntityDetector(ClientCommandManager.DISPATCHER);
	}
}