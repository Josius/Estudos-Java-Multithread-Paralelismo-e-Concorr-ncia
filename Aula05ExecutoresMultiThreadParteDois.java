import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Aula05ExecutoresMultiThreadParteDois {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = null;

        try {
// Fixed e Cached
            // executor = Executors.newFixedThreadPool(4);
            executor = Executors.newCachedThreadPool();
            // Future<String> future_1 = executor.submit(new Tarefa());
            // System.out.println(future_1.get()); // forçando a terminar a 1ª tarefa antes de executar as outras
            // Future<String> future_2 = executor.submit(new Tarefa());
            // Future<String> future_3 = executor.submit(new Tarefa());
            // Future<String> future_4 = executor.submit(new Tarefa());
            // Future<String> future_5 = executor.submit(new Tarefa());
            // System.out.println(future_2.get());
            // System.out.println(future_3.get());
            // System.out.println(future_4.get());
            // System.out.println(future_5.get());
            // InvokeAll e InvokeAny
            List<Tarefa> lista = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                lista.add(new Tarefa());
            }
            
// InvokeAll
            // List<Future<String>> list = executor.invokeAll(lista);
            
            // for (Future<String> future : list) {
            //     System.out.println(future.get());
            // }
// InvokeAny
            String invokeAny = executor.invokeAny(lista);
            System.out.println(invokeAny);
            executor.shutdown();
        } catch (Exception e) {
            throw e;
        } finally {
            if(executor != null){
                executor.shutdownNow();
            }
        }
    }
    
    public static class Tarefa implements Callable<String> {
        
        @Override
        public String call() throws Exception {

            Thread.sleep(1000);
            String name = Thread.currentThread().getName();
            int nextInt = new Random().nextInt(12);

            return name + ": executorsssss!! " + nextInt;
        }

    }
}
