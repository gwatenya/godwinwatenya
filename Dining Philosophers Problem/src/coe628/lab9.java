package coe628;


/**
 *
 * @author waten
 */
public class lab9 {

    /**
     * @param args the command line arguments
     */
    static final int N = 5; /*Number of philosophers */
    private enum phil_state {THINKING, HUNGRY, EATING};
    
    static semaphore mutex = new semaphore(1);
    static semaphore[] s = new semaphore[N]; /* one per philosopher, all 0 */
    static Philosopher[] phils = new Philosopher[N];
    static int completed = 0;
    
    public static void main(String[] args) {
        //Initializing Philosopher Threads
        
        /*           P1   f2       
                f1           P2  
             P0          f3       
                f0          P3   
                   P4   f4         */
        
        for (int i=0; i<N; i++){
            int left = i == 0 ? N - 1 : i - 1;
            int right = ((i)+1) % N;
            phils[i] = new Philosopher(i);
            s[i] = new semaphore(0);
        }
        
        //Starting the threads
        for (Thread t: phils){
            t.start();
        }
    }
    
    public static class Philosopher extends Thread{
        private final int id;
        private phil_state state;
        private final semaphore self;
        
        Philosopher(int id){
            this.id = id;
            self = new semaphore(0);
            state = phil_state.THINKING;
        }
        
        private Philosopher left(){
            return phils[id == 0 ? N - 1 : id - 1];
        }
        
        private Philosopher right(){
            return phils[(id + 1) % N];
        }
        
        private void test(Philosopher i){
            if (i.state == phil_state.HUNGRY &&
                    i.left().state != phil_state.EATING &&
                    i.right().state != phil_state.EATING){
                i.state = phil_state.EATING;
                s[i.id].up();
            }
        }
        
        private void get_forks(Philosopher i){
            mutex.down();
            i.state = phil_state.HUNGRY;
            System.out.println("Philosopher " + i.id + " is waiting for Fork " +
                    i.id + " and " + i.right().id);
            test(i);
            mutex.up();
            s[i.id].down();
            System.out.println("Fork " + i.id + " and " + i.right().id +
                    " taken by Philosopher " + i.id);
        }
        
        private void put_forks(Philosopher i){
            mutex.down();
            i.state = phil_state.THINKING;
            System.out.println("Philosopher " + i.id + " completed his dinner.");
            System.out.println("Philosopher " + i.id + " releases fork " + i.id + 
                    " and " + i.right().id);
            test(i.left());
            test(i.right());
            mutex.up();
        }
        
        private void thinkOrEat() {
            try {
                Thread.sleep((long) Math.round(Math.random() * 5000));
            } catch (InterruptedException e) {}
        }
        
        @Override
        public void run(){
            while(true){
                thinkOrEat();
                get_forks(this);
                thinkOrEat();
                put_forks(this);
                completed++;
                System.out.println("Till now num of philophers completed dinner are " + completed);
                break;
            }
        }
    }
    
}
