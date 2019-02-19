package fr.badblock.bukkit.hub.v2.tasks;

import java.util.UUID;

import org.bukkit.scheduler.BukkitTask;

import fr.badblock.gameapi.utils.threading.TaskManager;

public abstract class HubTask implements Runnable
{

	protected BukkitTask task;
	
	public HubTask(boolean sync, int delay, int repeat)
	{
		if (sync)
		{
			task = TaskManager.scheduleSyncRepeatingTask(getTaskName() + "-" + UUID.randomUUID(), this, delay, repeat);
			return;
		}
		
		task = TaskManager.scheduleAsyncRepeatingTask(getTaskName() + "-" + UUID.randomUUID(), this, delay, repeat);
	}

	private String getTaskName()
	{
		return "HubTask-" + getClass().getSimpleName();
	}

}
