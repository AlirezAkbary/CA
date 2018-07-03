/**
 * Created by alireza on 7/3/18.
 */
public class Memory {
    boolean ready;
    boolean rwn;
    boolean start;
    int[] array = new int[1024];
    boolean reset;
    public Memory(){
        this.ready = false;
        this.rwn = false;
        this.start = false;
        this.reset = false;
    }
}
