public class Aula02SincronizarRecursos {

    static int variavelGlobal = -1;

    public static void main(String[] args) {

        // MeuRunnableConcorrente meuRunnableConcorrente = new MeuRunnableConcorrente();
        // Thread threadZero = new Thread(meuRunnableConcorrente);
        // Thread threadUm = new Thread(meuRunnableConcorrente);
        // Thread threadDois = new Thread(meuRunnableConcorrente);
        // Thread threadTres = new Thread(meuRunnableConcorrente);
        // Thread threadQuatro = new Thread(meuRunnableConcorrente);

        // threadZero.start();
        // threadUm.start();
        // threadDois.start();
        // threadTres.start();
        // threadQuatro.start();

        // MeuRunnableSincronizado meuRunnableSincronizado = new
        // MeuRunnableSincronizado();
        // Thread threadZeroS = new Thread(meuRunnableSincronizado);
        // Thread threadUmS = new Thread(meuRunnableSincronizado);
        // Thread threadDoisS = new Thread(meuRunnableSincronizado);
        // Thread threadTresS = new Thread(meuRunnableSincronizado);
        // Thread threadQuatroS = new Thread(meuRunnableSincronizado);

        // threadZeroS.start();
        // threadUmS.start();
        // threadDoisS.start();
        // threadTresS.start();
        // threadQuatroS.start();

        // MeuRunnableSincronizadoEmBloco meuRunnableSincronizadoEmBloco = new MeuRunnableSincronizadoEmBloco();
        // Thread threadZeroSB = new Thread(meuRunnableSincronizadoEmBloco);
        // Thread threadUmSB = new Thread(meuRunnableSincronizadoEmBloco);
        // Thread threadDoisSB = new Thread(meuRunnableSincronizadoEmBloco);
        // Thread threadTresSB = new Thread(meuRunnableSincronizadoEmBloco);
        // Thread threadQuatroSB = new Thread(meuRunnableSincronizadoEmBloco);
        
        // threadZeroSB.start();
        // threadUmSB.start();
        // threadDoisSB.start();
        // threadTresSB.start();
        // threadQuatroSB.start();
        
        MeuRunnableSincronizadoEmBlocoEstatico meuRunnableSincronizadoEmBlocoEstatico = new MeuRunnableSincronizadoEmBlocoEstatico();
        Thread threadZeroSB = new Thread(meuRunnableSincronizadoEmBlocoEstatico);
        Thread threadUmSB = new Thread(meuRunnableSincronizadoEmBlocoEstatico);
        Thread threadDoisSB = new Thread(meuRunnableSincronizadoEmBlocoEstatico);
        Thread threadTresSB = new Thread(meuRunnableSincronizadoEmBlocoEstatico);
        Thread threadQuatroSB = new Thread(meuRunnableSincronizadoEmBlocoEstatico);
        
        threadZeroSB.start();
        threadUmSB.start();
        threadDoisSB.start();
        threadTresSB.start();
        threadQuatroSB.start();
    }

    public static class MeuRunnableConcorrente implements Runnable {

        @Override
        public void run() {

            variavelGlobal++;
            String nameThread = Thread.currentThread().getName();
            System.out.println(nameThread + "-Concorrente: " + variavelGlobal);
        }
    }

    public static class MeuRunnableSincronizado implements Runnable {

        @Override
        public synchronized void run() {

            variavelGlobal++;
            String nameThread = Thread.currentThread().getName();
            System.out.println(nameThread + "-Sincronizado: " + variavelGlobal);
        }
    }

    public static class MeuRunnableSincronizadoEmBloco implements Runnable {
        private static Object lock_0 = new Object();
        private static Object lock_1 = new Object();

        @Override
        public void run() {
            synchronized (lock_0) {

                variavelGlobal++;
                String nameThread = Thread.currentThread().getName();
                System.out.println(nameThread + "-Sincronizado em bloco: " + variavelGlobal);
            }
            synchronized (lock_1) {

                variavelGlobal++;
                String nameThread = Thread.currentThread().getName();
                System.out.println(nameThread + "-Sincronizado em bloco: " + variavelGlobal);
            }
        }
    }

    public static void imprimeMeuRunnableSincronizadoEmBlocoEstatico() {
        synchronized (Aula02SincronizarRecursos.class) {
            variavelGlobal++;
            String nameThread = Thread.currentThread().getName();
            System.out.println(nameThread + "-Sincronizado em bloco estático: " + variavelGlobal);
        }
    }

    public static class MeuRunnableSincronizadoEmBlocoEstatico implements Runnable {

        @Override
        public void run() {
            imprimeMeuRunnableSincronizadoEmBlocoEstatico();
        }
    }
}
