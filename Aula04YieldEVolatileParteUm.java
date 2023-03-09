public class Aula04YieldEVolatileParteUm {

    private static int numero = 0;
    private static boolean preparado = false;

    private static class MeuRunnableAula04 implements Runnable{

        @Override
        public void run() {

            while(!preparado){
                Thread.yield();
            }

            System.out.println(numero);
        }


    }
    public static void main(String[] args) {
        
        Thread t0 = new Thread(new MeuRunnableAula04());
        t0.start();;
        numero = 42;
        preparado = true;
    }
}
