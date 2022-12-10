import java.util.Map;
import java.util.concurrent.*;

public class JobService {

    private static final int MAX_NUMBER_OF_JOBS = 10;
    private Map<Runnable, Thread> stateMap = new ConcurrentHashMap<>();
    private Map<Runnable, Future<?>> jobsMap = new ConcurrentHashMap<>();
    private Map<Runnable, ScheduledFuture<?>> scheduledJobsMap = new ConcurrentHashMap<>();

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(MAX_NUMBER_OF_JOBS, runnable -> {
        Thread thread = new Thread(runnable);
        stateMap.put(runnable, thread);
        return new Thread(runnable);
    });

    public void startJob(Runnable jobRunnable) {
        Future<?> future = executorService.submit(jobRunnable);
        jobsMap.put(jobRunnable, future);
    }

    public void scheduleDelayedJob(Runnable jobRunnable, long delay, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = executorService.schedule(jobRunnable, delay, unit);
        scheduledJobsMap.put(jobRunnable, scheduledFuture);
    }

    public Map<Runnable, Thread> getThreadsStateMap() {
        return stateMap;
    }

    public Thread getThreadByRunnable(Runnable runnable) {
        return stateMap.get(runnable);
    }

    public boolean cancelJobByRunnable(Runnable runnable, boolean mayInterruptIfRunning) {
        return jobsMap.get(runnable).cancel(mayInterruptIfRunning);
    }


    public boolean cancelScheduledJobByRunnable(Runnable runnable, boolean mayInterruptIfRunning) {
        return scheduledJobsMap.get(runnable).cancel(mayInterruptIfRunning);
    }
}
