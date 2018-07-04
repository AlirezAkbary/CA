/**
 * Created by alireza on 7/3/18.
 */
public class ALU{
    String Function;
    int signal_control;//it should be shown in binary
    boolean N;
    boolean Z;

    public ALU(){
        this.Function = "";
        this.signal_control = 0;
        this.N = false;
        this.Z = false;
    }
    public int[] operation(int input1, int input2, int control){

        return new int[]{};
    }

    public String getRecord()
    {
        return String.valueOf(signal_control);
    }

}
