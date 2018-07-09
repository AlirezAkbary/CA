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
                        processor.Zero.getRecord(),
                        processor.Negative.getRecord(),
                        memory.getRecord(),
                        processor.alu.getRecord()
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
    ALURecord ALU;
    FlipflopRecord Zero;
    FlipflopRecord Negative;


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
            FlipflopRecord Zero,
            FlipflopRecord Negative,
            MemRecord memory,
            ALURecord AlU
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
        this.IR = IR;
        this.memory = memory;
        this.ALU = AlU;
        this.Zero = Zero;
        this.Negative = Negative;
    }

    public void print()
    {
        System.out.println("MAR = " + MAR.value);
        System.out.println("MDR = " + MDR.value);
        System.out.println("PC = " + PC.value);
        System.out.println("MBR = " + MBR.value);
        System.out.println("SP = " + SP.value);
        System.out.println("LV = " + LV.value);
        System.out.println("CPP = " + CPP.value);
        System.out.println("TOS = " + TOS.value);
        System.out.println("OPC = " + OPC.value);
        System.out.println("H = " + H.value);
        System.out.println("ALU = " + ALU.Function);
        System.out.println("IR = " + IR.value);
        System.out.println("NFLAG = " + ALU.nFlag);
        System.out.println("Zflag = " + ALU.zFlag);
        System.out.println("Ready = " + memory.ready);
        System.out.println("Start = " + memory.start);
        System.out.println("rwn = " + memory.rwn);
        System.out.println("MAR.load = " + MAR.load);
        System.out.println("MDR.load = " + MDR.load);
        System.out.println("PC.load = " + PC.load);
        System.out.println("MBR.load = " + MBR.load);
        System.out.println("SP.load = " + SP.load);
        System.out.println("LV.load = " + LV.load);
        System.out.println("CPP.load = " + CPP.load);
        System.out.println("TOS.load = " + TOS.load);
        System.out.println("OPC.load = " + OPC.load);
        System.out.println("H.load = " + H.load);
        System.out.println("IR.load = " + IR.load);
        System.out.println("Flipflop N load = " + Negative.load);
        System.out.println("Flipflop Z load = " + Zero.load);
        System.out.println("Flipflop N = " + Negative.set);
        System.out.println("Flipflop Z = " + Zero.set);


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
class ALURecord{
    boolean nFlag;
    boolean zFlag;
    String Function;

    public ALURecord(boolean nFlag, boolean zFlag, String function) {
        this.nFlag = nFlag;
        this.zFlag = zFlag;
        this.Function = function;
    }
}
class FlipflopRecord{
    boolean set;
    boolean load;

    public FlipflopRecord(boolean set, boolean load) {
        this.set = set;
        this.load = load;
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
