import java.util.ArrayList;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    public static void main(String[] args) {

        JobService jobService = new JobService();

        Runnable command1 = () -> {
            try {
                SECONDS.sleep(3);
                System.out.println("Command 1 executed");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable command2 = () -> {
            try {
                SECONDS.sleep(3);
                System.out.println("Command 2 executed");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable command3 = () -> {
            try {
                SECONDS.sleep(3);
                System.out.println("Command 3 executed");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable command4 = () -> {
            try {
                SECONDS.sleep(3);
                System.out.println("Command 4 executed");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        ArrayList<Runnable> jobArray = new ArrayList<>(5);
        jobArray.add(command1);
        jobArray.add(command2);
        jobArray.add(command3);
        jobArray.add(command4);

        jobService.startJob(jobArray.get(0));
        jobService.startJob(jobArray.get(1));
        jobService.startJob(jobArray.get(2));
        jobService.startJob(jobArray.get(3));

        jobService.cancelJobByRunnable(jobArray.get(1), true);

        jobService.scheduleDelayedJob(jobArray.get(0), 5, SECONDS);
        jobService.scheduleDelayedJob(jobArray.get(1), 5, SECONDS);
        jobService.scheduleDelayedJob(jobArray.get(2), 5, SECONDS);

        jobService.cancelScheduledJobByRunnable(jobArray.get(0), true);
    }
}