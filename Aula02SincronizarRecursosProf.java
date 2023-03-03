public class Aula02SincronizarRecursosProf {

    static int variavelGlobal = 0;

    public static void main(String[] args) {

        MeuRunnableConcorrente meuRunnableConcorrente = new MeuRunnableConcorrente();
        Thread threadZero = new Thread(meuRunnableConcorrente);
        Thread threadUm = new Thread(meuRunnableConcorrente);
        Thread threadDois = new Thread(meuRunnableConcorrente);
        Thread threadTres = new Thread(meuRunnableConcorrente);
        Thread threadQuatro = new Thread(meuRunnableConcorrente);

        threadZero.start();
        threadUm.start();
        threadDois.start();
        threadTres.start();
        threadQuatro.start();

    }

    public static class MeuRunnableConcorrente implements Runnable {

        @Override
        public void run() {
            int j;
            synchronized(this){
                variavelGlobal++;
                j = variavelGlobal * 2;
            }
            
            double jElevadoA10 = Math.pow(j, 100);
            double sqrt = Math.sqrt(jElevadoA10);
            System.out.println(sqrt);
        }
    }
}
