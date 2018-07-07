import java.util.ArrayList;

/**
 * Created by ali on 7/3/2018 AD.
 */
public class Recorder
{
    ArrayList <Record> records = new ArrayList<>();
    Memory memory;
    Processor processor;
    public void setUp(Processor processor)
    {
        this.processor = processor;
    }

    public void takeRecord()
    {
        records.add(
                new Record(
                        processor.MAR.getRecord(),
                        processor.MDR.getRecord(),
                        processor.PC.getRecord(),
                        processor.MBR.getRecord(),
                        processor.SP.getRecord(),
                        processor.LV.getRecord(),
                        processor.CPP.getRecord(),
                        processor.TOS.getRecord(),
                        processor.OPC.getRecord(),
                        processor.H.getRecord(),
                        processor.IR.getRecord(),
                        memory.getRecord(),
                        processor.alu.getRecord(),
                        processor.negFlag,
                        processor.zeroFlag
                )
        );
    }

    public void deleteLast(int num)
    {
        for (int i = 0; i < num; i++)
        {
            records.remove(records.size() - 1);
        }
    }
}

class Record
{
    RegRecord MAR;
    RegRecord MDR;
    RegRecord PC;
    RegRecord MBR;
    RegRecord SP;
    RegRecord LV;
    RegRecord CPP;
    RegRecord TOS;
    RegRecord OPC;
    RegRecord H;
    RegRecord IR;
    MemRecord memory;
    String ALU_signal;
    boolean Nflag;
    boolean Zflag;


    public Record(
            RegRecord MAR,
            RegRecord MDR,
            RegRecord PC,
            RegRecord MBR,
            RegRecord SP,
            RegRecord LV,
            RegRecord CPP,
            RegRecord TOS,
            RegRecord OPC,
            RegRecord h,
            RegRecord IR,
            MemRecord memory,
            String ALU_signal,
            boolean nflag,
            boolean zflag
    )
    {
        this.MAR = MAR;
        this.MDR = MDR;
        this.PC = PC;
        this.MBR = MBR;
        this.SP = SP;
        this.LV = LV;
        this.CPP = CPP;
        this.TOS = TOS;
        this.OPC = OPC;
        H = h;
        this.memory = memory;
        this.ALU_signal = ALU_signal;
        Nflag = nflag;
        Zflag = zflag;
        this.IR = IR;
    }

    public void print()
    {
        System.out.println("MAR = " + MAR.value);
        System.out.println("MDR = " + MDR.value);
        System.out.println("PC = " + PC.value);
        System.out.println("MBR = " + MBR.value);
        System.out.println("SP = " + SP.value);
        System.out.println("LV = " + LV.value);
        System.out.println("CPP = " + PC.value);
        System.out.println("TOS = " + TOS.value);
        System.out.println("OPC = " + OPC.value);
        System.out.println("H = " + H.value);
        System.out.println("ALU = " + ALU_signal);
        System.out.println("IR = " + IR.value);
        System.out.println("NFLAG = " + Nflag);
        System.out.println("Zflag = " + Zflag);
        System.out.println("Ready = " + memory.ready);
        System.out.println("Start = " + memory.start);
        System.out.println("rwn = " + memory.rwn);

        System.out.println(".......................");

    }
}


class RegRecord
{
    boolean load;
    boolean clear;
    int value;

    public RegRecord(int value, boolean clear, boolean load)
    {
        this.value = value;
        this.load = load;
        this.clear = clear;
    }
}

class MemRecord
{
    boolean rwn;
    boolean start;
    boolean ready;

    public MemRecord(boolean rwn, boolean start, boolean ready)
    {
        this.rwn = rwn;
        this.start = start;
        this.ready = ready;
    }
}
