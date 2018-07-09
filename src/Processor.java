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
    Register IR;
    ALU alu;
    Memory memory;
    boolean zeroFlag;
    boolean negFlag;
    Recorder recorder;
    public Processor(Recorder recorder){
        this.MAR = new Register(0);
        this.MDR = new Register(0);
        this.PC = new Register(0);
        this.MBR = new Register(0);
        this.SP = new Register(64);
        this.LV = new Register(128);
        this.CPP = new Register(511);
        this.TOS = new Register(0);
        this.OPC = new Register(0);
        this.H = new Register(0);
        this.alu = new ALU();
        this.memory = new Memory(recorder);
        this.IR = new Register(0);
        this.recorder = recorder;
        this.recorder.memory = memory;
        this.recorder.setUp(this);
    }

    public void start()
    {
        recorder.takeRecord();
        this.FETCH();
    }

    public void FETCH()
    {
        memory.start = true;
        memory.rwn = true;
        MBR.data = memory.read(PC.data) % 256;
        MBR.load = true;
        recorder.takeRecord();
        MBR.load = false;
        IR.load = true;
        IR.data = MBR.data;
        recorder.takeRecord();
        PC.data += 1;
        switch(IR.data)
        {
            case 16:
                //bipush
                this.BIPUSH();
                break;
            case 167:
                //GOTO offset
                break;
            case 96:
                //iadd
                break;
            case 153:
                //ifeq
                break;
            case 155:
                //iflt
                break;
            case 159:
                //if_icmeq
                break;
            case 132:
                //iinc
                break;
            case 21:
                //iload
                break;
            case 54:
                //istore
                break;
            case 100:
                //isub
                break;
            case 0:
                //nop
                break;
            case 10:
                //end processing OMG
                return;
        }


        //return if instruction is HLT and dont take record and enjoy your life
        //no more microwave food
        //
        //fetch instruction and take records
        //goto instruction method
        this.FETCH();
    }
    public void BIPUSH()
    {
        memory.start = true;
        memory.rwn = true;
        MBR.data = memory.read(PC.data) % 256;
        MBR.load = true;
        recorder.takeRecord();
        MBR.load = false;
        MDR.load = true;
        TOS.load = true;
        recorder.takeRecord();
        MDR.load = false;
        TOS.load = false;
        SP.load = true;
        MDR.data = (MBR.data << 24) >> 24;
        TOS.data = (MBR.data << 24) >> 24;
        alu.setFunction("010100");
        recorder.takeRecord();
        MAR.data = SP.data << 2;
        PC.data = PC.data + 1;
        alu.setFunction("010100");
        recorder.takeRecord();
        memory.start = true;
        memory.rwn = false;
        memory.write(MDR.data, MAR.data);
        recorder.takeRecord();
        return;

    }
    public void GOTO()
    {

    }
    public void IADD()
    {

    }
    public void IFEQ()
    {

    }
    public void IFLT()
    {

    }
    public void IF_ICMPEQ()
    {

    }
    public void IINC()
    {

    }
    public void ILOAD()
    {

    }
    public void ISTORE()
    {

    }
    public void ISUB()
    {

    }
    public void NOP()
    {

    }

}

