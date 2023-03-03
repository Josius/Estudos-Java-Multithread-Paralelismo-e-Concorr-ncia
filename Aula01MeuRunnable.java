public class Aula01MeuRunnable implements Runnable{

    @Override
    public void run() {
        // TODO Auto-generated method stub
        String nameThread = Thread.currentThread().getName();
        System.out.println(nameThread);
        // throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
