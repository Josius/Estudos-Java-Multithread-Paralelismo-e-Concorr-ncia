import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Aula05ExecutoresScheduled {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
// Scheduled com Callable
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // System.out.println("1º " + System.currentTimeMillis());
        // ScheduledFuture<String> future = executor.schedule(new Tarefa(), 2, TimeUnit.SECONDS);
        
        // System.out.println(future.get());
        
        // executor.shutdown();

// Scheduled com Runnable
        // System.out.println("1º " + System.currentTimeMillis());
        // executor.schedule(new TarefaComRunnable(), 2, TimeUnit.SECONDS);
        
        // executor.shutdown();
        
// Scheduled com FixedRate
        // executor.scheduleAtFixedRate(new TarefaComRunnable(), 0, 1, TimeUnit.SECONDS);
        
// Scheduled com FixedDelay
        executor.scheduleWithFixedDelay(new TarefaComRunnable(), 0, 1, TimeUnit.SECONDS);

        // executor.shutdown();
    }
    
    public static class Tarefa implements Callable<String> {
        
        @Override
        public String call() throws Exception {
            
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(12);
            
            System.out.println("2º " + System.currentTimeMillis());
            return name + ": executorsssss!! " + nextInt;
        }
    }
    
    public static class TarefaComRunnable implements Runnable {
        
        @Override
        public void run() {
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(LocalTime.now());
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(12);
            
            System.out.println("2º " + System.currentTimeMillis());
            System.out.println(name + ": executorsssss!! " + nextInt);
        }
    }
}
