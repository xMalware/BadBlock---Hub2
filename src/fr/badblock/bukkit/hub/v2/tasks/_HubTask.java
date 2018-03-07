package fr.badblock.bukkit.hub.v2.tasks;

import fr.badblock.gameapi.utils.threading.TaskManager;

public abstract class _HubTask implements Runnable
{

	public _HubTask(boolean sync, int delay, int repeat)
	{
		if (sync)
		{
			TaskManager.scheduleSyncRepeatingTask(getTaskName(), this, delay, repeat);
			return;
		}
		TaskManager.scheduleAsyncRepeatingTask(getTaskName(), this, delay, repeat);
	}

	private String getTaskName()
	{
		return "HubTask-" + getClass().getSimpleName();
	}

}
