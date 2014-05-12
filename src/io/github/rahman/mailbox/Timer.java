package io.github.rahman.mailbox;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Timer implements Runnable
{
	private MailBoxPlugin plugin;

	public Timer(MailBoxPlugin plugin)
	{
		this.plugin = plugin;
	}

	public void checkCooldown()
	{
		try
		{
			ArrayList<Record> h = this.plugin.cooldown;

			for (int i = 0; i < h.size(); i++)
			{
				Record r = (Record)h.get(i);
				if (r.getTime() <= 1)
				{
					h.remove(i);
					i--;
				}
				else
				{
					r.setTime(r.getTime() - 1);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Problem with cooldown thread!");
		}
		try
		{
			TimeUnit.MILLISECONDS.sleep(1000L);
		}
		catch (InterruptedException localInterruptedException)
		{
			localInterruptedException.printStackTrace();
		}
	}

	public void run()
	{
		System.out.println("[MailBox] New thread running.");
		while (this.plugin.isRunning())
		{
			checkCooldown();
		}
	}
}