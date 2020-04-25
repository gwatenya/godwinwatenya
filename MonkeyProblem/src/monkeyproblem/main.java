/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monkeyproblem;

/**
 *
 * @author waten
 */

public class main {
    static final int N = 15; /*Number of monkeys */
    private enum Destination {EAST, WEST};
    static int[] dir_count = {0, 0}; // counter for each direction
    static Semaphore max_on_rope = new Semaphore(5); // maximum 5
    static Semaphore mutex0 = new Semaphore(1); // east mutual exclusion
    static Semaphore mutex1 = new Semaphore(1); // west mutual exclusion
    static Semaphore rope = new Semaphore(1); // ensures monkeys on rope are heading same direction
    static Semaphore order = new Semaphore(1); // to prevent starvation
    static Monkey[] monkeys = new Monkey[N];
    
    public static void main(String[] args) {
        for (int i=0; i<N; i++){
            monkeys[i] = new Monkey(i);
        }
        for (Thread t: monkeys){
            t.start();
        }
    }     
    public static class Monkey extends Thread{
        private final int id;
        private final Destination d; // destination: East = 0, West = 1
        
        Monkey(int id){
            this.id = id;
            if (id%2 == 0)
                this.d = Destination.EAST;
            else
                this.d = Destination.WEST;
        }
        
        /* If mutex0 or mutex1 are locked on the 1st monkey for a specific
        *  direction, and rope is locked, no monkeys for that direction 
        *  can continue to cross until the rope is unlocked and mutex0 and 
        *  mutex1 is unlocked */
        private void WaitToCross(Destination D){
            order.down();
            System.out.println("Monkey " + this.id + " waiting to cross " + this.d);
            switch(D){
                case EAST:
                    mutex0.down();
                    dir_count[0]++;
                    if (dir_count[0] == 1){ // 1st monkey for east direction
                        rope.down(); // mutex-lock the rope for east direction
                    }
                    mutex0.up();
                    break;
                case WEST:
                    mutex1.down();
                    dir_count[1]++;
                    if (dir_count[1] == 1){ // 1st monkey for west direction
                        rope.down(); // mutex-lock the rope for west direction
                    }
                    mutex1.up();
                    break;
            }
            max_on_rope.down(); // decrements number of monkeys on rope
            System.out.println("\tMonkey " + this.id + " is crossing " + this.d);
            System.out.println("\tMonkeys on rope: " + (5-max_on_rope.getValue()));
            order.up();
        }
        
        private void DoneCrossing(Destination D){
            switch(D){
                case EAST:
                    mutex0.down();
                    dir_count[0]--;
                    System.out.println("\t\tMonkey " + this.id + " is done crossing " + this.d);
                    System.out.println("\t\tMonkeys on rope: " + (5-(max_on_rope.getValue()+1)));
                    if (dir_count[0] == 0)  // last monkey for easr
                        rope.up(); // unlocks the rope
                    mutex0.up();
                    break;
                case WEST:
                    mutex1.down();
                    dir_count[1]--;
                    System.out.println("\t\tMonkey " + this.id + " is done crossing " + this.d);
                    System.out.println("\t\tMonkeys on rope: " + (5-(max_on_rope.getValue()+1)));
                    if (dir_count[1] == 0) // last monkey
                        rope.up(); //unlocks the rope
                    mutex1.up();
                    break;
            }
            max_on_rope.up();
            
        }
        
        private void thinkOrCross() {
            try {
                Thread.sleep((long) Math.round(Math.random() * 1000));
            } catch (InterruptedException e) {}
        }
        
        @Override
        public void run(){
            while(true){
                thinkOrCross();
                WaitToCross(this.d);
                thinkOrCross();
                DoneCrossing(this.d);
                break;
            }
        }
    }
}
