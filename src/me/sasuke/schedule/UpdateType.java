package me.sasuke.schedule;

// ** Creditos ao @Willjafor1 pelo enum

public enum UpdateType
{
	HOUR(3600000),
	HALF_HOUR(1800000),
	MIN(60000),
	SLOWER(16000),
	SLOW(4000),
	SEC(1000),
	FAST(500),
	FASTER(250),
	FASTEST(125),
	TICK(49),
     REALTIME(1);

	private long _time;
	private long _last;
	private long _timeSpent;
	private long _timeCount;

	UpdateType(long time)
	{
		_time = time;
		_last = System.currentTimeMillis();
	}

	public boolean Elapsed()
	{
		if (elapsed(_last, _time))
		{
			_last = System.currentTimeMillis();
			return true;
		}

		return false;
	}

	public void StartTime()
	{
		_timeCount = System.currentTimeMillis();
	}

	public void StopTime()
	{
		_timeSpent += System.currentTimeMillis() - _timeCount;
	}

	public void PrintAndResetTime()
	{
		System.out.println(this.name() + " in a second: " + _timeSpent);
		_timeSpent = 0;
	}


	public static boolean elapsed(long from, long required)
	{
		return System.currentTimeMillis() - from > required;
	}
}