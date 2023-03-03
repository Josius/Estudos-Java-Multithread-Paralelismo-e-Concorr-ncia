public class Aula01ThreadERunnable {
    
    public static void main(String[] args) {
        
        Aula01MeuRunnable meuRunnable = new Aula01MeuRunnable();
        
        // Descobrindo a thread atual
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName());
        
        // Criando um objeto que representa uma nova thread
        Thread threadUm = new Thread(meuRunnable);
        
        // Implementando Runnable com uma função lambda
        Thread threadDois = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        
        // Várias Threads
        Thread threadTres = new Thread(meuRunnable);

        threadUm.run();
        threadUm.start();
        threadDois.start();
        threadTres.start();

    }
}
