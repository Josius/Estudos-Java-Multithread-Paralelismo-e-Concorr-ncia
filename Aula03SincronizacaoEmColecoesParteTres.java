import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Aula03SincronizacaoEmColecoesParteTres {

    // static AtomicInteger variavelGlobal = new AtomicInteger(-1);
    // static AtomicBoolean variavelGlobal = new AtomicBoolean(false);
    static AtomicReference<Object> variavelGlobal = new AtomicReference(new Object());
    
    public static void main(String[] args) {

        // Classes Atômicas
        MeuRunnableSincronizado meuRunnableSincronizado = new MeuRunnableSincronizado();
        Thread threadZeroS = new Thread(meuRunnableSincronizado);
        Thread threadUmS = new Thread(meuRunnableSincronizado);
        Thread threadDoisS = new Thread(meuRunnableSincronizado);
        Thread threadTresS = new Thread(meuRunnableSincronizado);
        Thread threadQuatroS = new Thread(meuRunnableSincronizado);

        threadZeroS.start();
        threadUmS.start();
        threadDoisS.start();
        threadTresS.start();
        threadQuatroS.start();
    }

    public static class MeuRunnableSincronizado implements Runnable {

        @Override
        public void run() {

            String nameThread = Thread.currentThread().getName();
            // System.out.println(nameThread + "-Sincronizado: " + variavelGlobal.incrementAndGet());
            // System.out.println(nameThread + "-Sincronizado: " + variavelGlobal.compareAndExchange(false, true));
            System.out.println(nameThread + "-Sincronizado: " + variavelGlobal.getAndSet(new Object()));
           
        }
    }
}
