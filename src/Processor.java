/**
 * Created by alireza on 7/2/18.
 */

public class Processor{
    Register MAR;
    Register MDR;
    Register PC;
    Register MBR;
    Register SP;
    Register LV;
    Register CPP;
    Register TOS;
    Register OPC;
    Register H;
    ALU alu;
    public Processor(){
        this.MAR = new Register(0);
        this.MDR = new Register(0);
        this.PC = new Register(0);
        this.MBR = new Register(0);
        this.SP = new Register(64);
        this.LV = new Register(128);
        this.CPP = new Register(192);
        this.TOS = new Register(0);
        this.OPC = new Register(0);
        this.H = new Register(0);
        this.alu = new ALU();
    }
    public void FETCH(){

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

