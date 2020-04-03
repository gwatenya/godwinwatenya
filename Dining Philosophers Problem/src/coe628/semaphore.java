/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe628;

/**
 *
 * @author waten
 */
public class semaphore {
    private int value;
    
    public semaphore(int value){
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
