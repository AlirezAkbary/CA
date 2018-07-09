import java.util.Date;
import java.util.TreeMap;

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
    Flipflop Zero;
    Flipflop Negative;

    Recorder recorder;
    public Processor(Recorder recorder){
        this.MAR = new Register(0);
        this.MDR = new Register(0);
        this.PC = new Register(0);
        this.MBR = new Register(0);
        this.SP = new Register(16);
        this.LV = new Register(128);
        this.CPP = new Register(511);
        this.TOS = new Register(0);
        this.OPC = new Register(0);
        this.H = new Register(0);
        this.alu = new ALU();
        this.memory = new Memory(recorder);
        this.IR = new Register(0);
        this.Zero = new Flipflop();
        this.Negative = new Flipflop();
        this.recorder = recorder;
        this.recorder.memory = memory;
        this.recorder.setUp(this);
    }

    public void start()
    {
        recorder.takeRecord();
        this.FETCH();
    }
    public int mod(int x, int y){
        int result = x % y;
        return result < 0? result + y : result;
    }
    public void FETCH()
    {
        memory.start = true;
        memory.rwn = true;
        MBR.load = true;
        recorder.takeRecord();
        System.out.println("PC"+PC.data);
        MBR.data = memory.read(PC.data) % 256;
        MBR.data = this.mod(MBR.data, 256);
        System.out.println(MBR.data+"mbr");
        MBR.load = false;
        IR.load = true;
        recorder.takeRecord();


        IR.data = alu.operation(0, (MBR.data ) , "B");
        System.out.println(IR.data+"ir");

        IR.load = false;
        PC.load = true;
        recorder.takeRecord();
        PC.data = alu.operation(0, PC.data, "B+1");
        PC.load = false;
        switch(IR.data)
        {
            case 16:
                //bipush
                this.BIPUSH();
                break;
            case 167:
                //GOTO offset
                this.GOTO();
                break;
            case 96:
                //iadd
                this.IADD();
                break;
            case 153:
                //ifeq
                this.IFEQ();
                break;
            case 155:
                //iflt
                this.IFLT();
                break;
            case 159:
                //if_icmeq
                this.IF_ICMPEQ();
                break;
            case 132:
                //iinc
                this.IINC();
                break;
            case 21:
                //iload
                this.ILOAD();
                break;
            case 54:
                //istore
                this.ISTORE();
                break;
            case 100:
                //isub
                this.ISUB();
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
        MBR.load = true;
        recorder.takeRecord();
        MBR.data = memory.read(PC.data) % 256;
        recorder.takeRecord();
        MBR.load = false;
        MDR.load = true;
        TOS.load = true;
        recorder.takeRecord();
        MDR.load = false;
        TOS.load = false;
        SP.load = true;
        MAR.load = true;
        MBR.data = alu.operation(0, MBR.data,"B");
        MDR.data = (MBR.data << 24) >> 24;
        TOS.data = (MBR.data << 24) >> 24;
        recorder.takeRecord();
        SP.data = alu.operation(0, SP.data, "B+1");
        MAR.data = SP.data << 2;
        SP.load = false;
        PC.load = true;
        MAR.load = false;
        recorder.takeRecord();
        PC.data = alu.operation(0, PC.data, "B+1");
        PC.load = false;
        memory.start = true;
        memory.rwn = false;
        recorder.takeRecord();
        memory.write(MDR.data, MAR.data);
        recorder.takeRecord();
        return;

    }
    public void GOTO()
    {
        OPC.load = true;
        MBR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        OPC.data = alu.operation(0, PC.data, "B-1");
        OPC.load = false;
        MBR.data = (memory.read(PC.data))%256;
        MBR.load = false;
        H.load = true;
        recorder.takeRecord();
        H.data = (alu.operation(0, (MBR.data<<8)&0x0000FF00, "B") << 16)>>16;
        H.load = false;
        PC.load = true;
        recorder.takeRecord();
        PC.data = alu.operation(0, PC.data, "B+1");;
        PC.load = false;
        MBR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        MBR.data = (memory.read(PC.data))%256;
        MBR.load = false;
        H.load = true;
        recorder.takeRecord();
        H.data = alu.operation(H.data, MBR.data & 0x000000FF, "OR");
        H.load = false;
        PC.load = true;
        recorder.takeRecord();
        PC.data = alu.operation(H.data, OPC.data, "A+B");
        PC.load = false;
        recorder.takeRecord();
        return;
    }
    public void IADD()
    {

        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.load = false;
        MAR.load = false;
        System.out.println(SP.data+"sp");
        SP.data = alu.operation(0, SP.data, "B-1");
        System.out.println(SP.data+"sp2");
        MAR.data = SP.data << 2;
        memory.start = true;
        memory.rwn = true;
        MDR.load = true;
        recorder.takeRecord();
        System.out.println(MAR.data+"address");
        MDR.data = memory.read(MAR.data);
        MDR.load = false;
        H.load = true;
        recorder.takeRecord();
        H.data = alu.operation(0, TOS.data, "B");
        H.load = false;
        TOS.load = true;
        MDR.load = true;
        recorder.takeRecord();
        System.out.println(alu.operation(H.data, MDR.data, "A+B") + " "+ H.data+" "+ MDR.data);
        TOS.data = alu.operation(H.data, MDR.data, "A+B");
        MDR.data = alu.operation(H.data, MDR.data, "A+B");
        TOS.load = false;
        MDR.load = false;
        memory.start = true;
        memory.rwn = false;
        recorder.takeRecord();
        memory.write(MDR.data, MAR.data);
        recorder.takeRecord();
    }
    public void IFEQ()
    {
        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.data = alu.operation(0, SP.data, "B-1");
        MAR.data = SP.data << 2;
        SP.load = false;
        MAR.load = false;
        OPC.load = true;
        MDR.load = true;
        memory.start =  true;
        memory.rwn = true;
        recorder.takeRecord();
        OPC.data = alu.operation(0, TOS.data, "B");
        MDR.data = memory.read(MAR.data);
        OPC.load = false;
        MDR.load = false;
        TOS.load = true;
        recorder.takeRecord();
        TOS.data = alu.operation(0, MDR.data, "B");
        TOS.load = false;
        Zero.load = true;
        if (OPC.data == 0){
            Zero.set = true;
            Zero.load = false;
            recorder.takeRecord();
            this.GOTO();
        }
        else {
            Zero.set = false;
            Zero.load = false;
            PC.load = true;
            recorder.takeRecord();
            PC.data = alu.operation(0, PC.data, "B+1");
            PC.load = true;
            recorder.takeRecord();
            PC.data = alu.operation(0, PC.data, "B+1" );
            PC.load = false;
            recorder.takeRecord();
        }



    }
    public void IFLT()
    {
        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.data = alu.operation(0, SP.data, "B-1");
        MAR.data = SP.data << 2;
        SP.load = false;
        MAR.load = false;
        OPC.load = true;
        MDR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        OPC.data = alu.operation(0, TOS.data, "B");
        MDR.data = memory.read(MAR.data);
        OPC.load = false;
        MDR.load = false;
        TOS.load = true;
        recorder.takeRecord();
        TOS.data = alu.operation(0, MDR.data, "B");
        TOS.load = false;
        Negative.load = true;
        if (OPC.data < 0){
            Negative.set = true;
            Negative.load = false;
            recorder.takeRecord();
            this.GOTO();
        }
        else {
            Negative.set = false;
            Negative.load = false;
            PC.load = true;
            recorder.takeRecord();
            PC.data = alu.operation(0, PC.data, "B+1");
            PC.load = true;
            recorder.takeRecord();
            PC.data = alu.operation(0, PC.data, "B+1" );
            PC.load = false;
            recorder.takeRecord();
        }
    }
    public void IF_ICMPEQ()
    {
        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.data = alu.operation(0, SP.data, "B-1");
        MAR.data = SP.data << 2;
        SP.load = false;
        MAR.load = false;
        OPC.load = true;
        MDR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        OPC.data = alu.operation(0, TOS.data, "B");
        OPC.load = false;
        MDR.data = memory.read(MAR.data);
        MDR.load = false;
        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.data = alu.operation(0, SP.data, "B-1");
        MAR.data = SP.data << 2;
        SP.load = false;
        MAR.load = false;
        H.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        H.data = alu.operation(0, MDR.data, "B");
        H.load = false;
        MDR.data = memory.read(MAR.data);
        MDR.load = false;
        TOS.load = true;
        recorder.takeRecord();
        TOS.data = alu.operation(0, MDR.data, "B");
        TOS.load = false;
        Zero.load = true;
        recorder.takeRecord();
        if (OPC.data == H.data){
            Zero.set = true;
            Zero.load = false;
            recorder.takeRecord();
            this.GOTO();
        }
        else {
            Zero.set = false;
            Zero.load = false;
            PC.load = true;
            recorder.takeRecord();
            PC.data = alu.operation(0, PC.data, "B+1");
            PC.load = true;
            recorder.takeRecord();
            PC.data = alu.operation(0, PC.data, "B+1" );
            PC.load = false;
            recorder.takeRecord();
        }
    }
    public void IINC()
    {
        H.load = true;
        MBR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        H.data = alu.operation(0, LV.data, "B");
        H.load = false;
        MBR.data = (memory.read(PC.data))%256;
        MBR.load = false;
        MAR.load = true;
        recorder.takeRecord();
        MAR.data = alu.operation(H.data, ((MBR.data << 24) >> 24), "A+B");
        MAR.load = false;
        PC.load = true;
        recorder.takeRecord();
        PC.data = alu.operation(0, PC.data, "B+1");
        PC.load = false;
        memory.start = true;
        memory.rwn = true;
        MBR.load = true;
        recorder.takeRecord();
        MBR.data = (memory.read(PC.data))%256;
        MBR.load = false;
        MDR.load = true;
        recorder.takeRecord();
        MDR.data = memory.read(MAR.data);
        MDR.load = false;
        H.load = true;
        recorder.takeRecord();
        H.data = alu.operation(0, MDR.data, "B");
        H.load = false;
        MDR.load = true;
        recorder.takeRecord();
        MDR.data = alu.operation(H.data, ((MBR.data << 24) >> 24), "A+B");
        MDR.load = false;
        memory.start = true;
        memory.rwn = false;
        recorder.takeRecord();
        memory.write(MDR.data, MAR.data);
        PC.load = true;
        recorder.takeRecord();
        PC.data = alu.operation(0, PC.data, "B+1");
        PC.load = false;
        recorder.takeRecord();
    }
    public void ILOAD()
    {
        H.load = true;
        MBR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        H.data = alu.operation(0,LV.data, "B");
        H.load = false;
        MBR.data = (memory.read(PC.data))%256;
        MBR.load = false;
        MAR.load = true;
        recorder.takeRecord();
        MAR.data = alu.operation(H.data, ((MBR.data << 24) >> 24), "A+B");
        MAR.load = false;
        PC.load = true;
        MDR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        PC.data = alu.operation(0, PC.data, "B+1");
        PC.load = false;
        MDR.data = memory.read(MAR.data);
        MDR.load = false;
        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.data = alu.operation(0, SP.data, "B+1");
        MAR.data = SP.data << 2;
        SP.load = false;
        MAR.load = false;
        TOS.load = true;
        memory.start = true;
        memory.rwn = false;
        recorder.takeRecord();
        //System.out.println(alu.operation(0, ((MDR.data<<24)>>24), "B") + "why" + MDR.data);
        TOS.data = alu.operation(0, (MDR.data), "B");
        TOS.load = false;
        memory.write(MDR.data, MAR.data);
        recorder.takeRecord();
    }
    public void ISTORE()
    {
        H.load = true;
        MBR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        H.data = alu.operation(0, LV.data, "B");
        H.load = false;
        MBR.data = (memory.read(PC.data))%256;
        MBR.load = false;
        MAR.load = true;
        recorder.takeRecord();
        MAR.data = alu.operation(H.data, ((MBR.data << 24) >> 24), "A+B");
        MAR.load = false;
        MDR.load = true;
        recorder.takeRecord();
        MDR.data = alu.operation(0, TOS.data, "B");
        MDR.load = false;
        memory.start = true;
        memory.rwn = false;
        recorder.takeRecord();
        memory.write(MDR.data, MAR.data);
        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.data = alu.operation(0, SP.data, "B-1");
        MAR.data = SP.data << 2;
        SP.load = false;
        MAR.load = false;
        PC.load = true;
        MDR.load = true;
        memory.start = true;
        memory.rwn = true;
        recorder.takeRecord();
        PC.data = alu.operation(0, PC.data, "B+1");
        PC.load = false;
        MDR.data = memory.read(MAR.data);
        MDR.load = false;
        TOS.load = true;
        recorder.takeRecord();
        TOS.data = alu.operation(0, MDR.data, "B");
        TOS.load = false;
        recorder.takeRecord();
    }
    public void ISUB()
    {
        SP.load = true;
        MAR.load = true;
        recorder.takeRecord();
        SP.load = false;
        MAR.load = false;
        SP.data = alu.operation(0, SP.data, "B-1");
        MAR.data = SP.data << 2;
        memory.start = true;
        memory.rwn = true;
        MDR.load = true;
        recorder.takeRecord();
        MDR.data = memory.read(MAR.data);
        MDR.load = false;
        H.load = true;
        recorder.takeRecord();
        H.data = alu.operation(0, TOS.data, "B");
        H.load = false;
        TOS.load = true;
        MDR.load = true;
        recorder.takeRecord();
        TOS.data = alu.operation(H.data, MDR.data, "B-A");
        MDR.data = alu.operation(H.data, MDR.data, "B-A");
        TOS.load = false;
        MDR.load = false;
        memory.start = true;
        memory.rwn = false;
        recorder.takeRecord();
        memory.write(MDR.data, MAR.data);
        recorder.takeRecord();
    }
    public void NOP()
    {

    }

}

