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