import java.lang.Thread.State;

public class Aula04YieldEVolatileParteDois {
    
    // private static int numero = 0;
    // private static boolean preparado = false;
    private static volatile int numero = 0;
    private static volatile boolean preparado = false;

    private static class MeuRunnableAula04 implements Runnable{

        @Override
        public void run() {

            while(!preparado){
                Thread.yield();
            }

            if(numero != 42){
                // System.out.println(numero);
                throw new IllegalStateException("Xiiiii!!!");
            }
        }
    }
    public static void main(String[] args) {
        
        while (true) {
            
            Thread t0 = new Thread(new MeuRunnableAula04());
            t0.start();;
            Thread t1 = new Thread(new MeuRunnableAula04());
            t1.start();;
            Thread t2 = new Thread(new MeuRunnableAula04());
            t2.start();;
            
            numero = 42;
            preparado = true;

            while(
                t0.getState() != State.TERMINATED
                || t1.getState() != State.TERMINATED
                || t2.getState() != State.TERMINATED
            ){
                // espera
            }
            numero = 0;
            preparado = false;
        }
    }
}
