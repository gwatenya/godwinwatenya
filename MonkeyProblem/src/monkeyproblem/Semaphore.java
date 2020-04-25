package monkeyproblem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author waten
 */
public class Semaphore {
    private int value;
    
    public Semaphore(int value){
        this.value = value;
    }
    
    public synchronized void up(){ /* V */
        this.value++;
        if (this.value > 0){
            this.notify();
        }
    }
    
    public synchronized void down(){ /* P */
        if (this.value >0){
            this.value--;
        }
        else{
            try{
                this.wait();
                this.value--;
            }
            catch (Exception ex){}
        }
    }
    
    public int getValue(){
        return this.value;
    }
}
