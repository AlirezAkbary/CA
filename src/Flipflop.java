/**
 * Created by alireza on 7/8/18.
 */
public class Flipflop {
    boolean set;
    boolean load;
    public Flipflop() {
        this.set = false;
        this.load = false;
    }
    public FlipflopRecord getRecord(){
        return new FlipflopRecord(this.set, this.load);
    }
}
