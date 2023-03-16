import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Aula05ExecutoresSingleThreadParteUm {

    public static void main(String[] args) throws InterruptedException {

        // SingleThread
        ExecutorService executor = null;
        try {
            
            executor = Executors.newSingleThreadExecutor();
            
            executor.execute(new Tarefa());
            executor.execute(new Tarefa());
            executor.execute(new Tarefa());
            Future<?> future = executor.submit(new Tarefa());
            
            System.out.println(future.isDone());
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println(future.isDone());
            
        } catch (Exception e) {

            throw e;
        }finally{

            if (executor != null){ 
                // executor.shutdown();
                executor.shutdownNow();
            }
        }
    }

    public static class Tarefa implements Runnable {

        @Override
        public void run() {

            String name = Thread.currentThread().getName();
            System.out.println(name + ": executorsssss!!");
        }

    }
}