/**
 * Created by alireza on 7/2/18.
 */
public class Processor {
    DataPath dataPath;
}
class DataPath{
    register MAR;
    register MDR;
    register PC;
    register MBR;
    register SP;
    register LV;
    register CPP;
    register TOS;
    register OPC;
    register H;
    ALU alu;
    public DataPath(){
        this.MAR = new register(0);
        this.MDR = new register(0);
        this.PC = new register(0);
        this.MBR = new register(0);
        this.SP = new register(64);
        this.LV = new register(128);
        this.CPP = new register(192);
        this.TOS = new register(0);
        this.OPC = new register(0);
        this.H = new register(0);
        this.alu = new ALU();
    }
    public void BIPUSH(){

    }
    public void GOTO(){

    }
    public void IADD(){

    }
    public void IFEQ(){

    }
    public void IFLT(){

    }
    public void IF_ICMPEQ(){

    }
    public void IINC(){

    }
    public void ILOAD(){

    }
    public void ISTORE(){

    }
    public void ISUB(){

    }
    public void NOP(){

    }

}
class register{
    int data;
    boolean reset;
    boolean load;
    public register(int data){
        this.data = data;
        this.reset = false;
        this.load = false;
    }
}

class ALU{
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

}
