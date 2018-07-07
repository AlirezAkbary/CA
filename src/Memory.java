import java.util.Random;

/**
 * Created by alireza on 7/3/18.
 */
public class Memory {
    boolean ready;
    boolean rwn;
    boolean start;
    byte[] array = new byte[1024];
    boolean reset;
    Recorder recorder;
    public Memory(Recorder recorder){
        this.ready = false;
        this.rwn = false;
        this.start = false;
        this.reset = false;
        this.recorder = recorder;
    }

    {
        array[0] = 16;
        array[1] = -5;
        array[2] = 10;
    }

    public int read(int address)
    {
        int delay = (new Random()).nextInt(4) + 1;
        start = false;
        recorder.takeRecord();
        ready = false;
        for (int i = 0; i < delay; i++)
        {
            recorder.takeRecord();
        }
        ready = true;
        return array[address] + (array[address + 1] << 8) + (array[address + 2] << 16)  + (array[address + 3] << 24);
    }


    public void write(int value, int address)
    {
        int delay = (new Random()).nextInt(4) + 1;
        start = false;
        recorder.takeRecord();
        ready = false;
        for (int i = 0; i < delay; i++)
        {
            recorder.takeRecord();
        }
        array[address] = (byte) (value % 256);
        array[address + 1] = (byte) ((value >> 8) % 256);
        array[address + 2] = (byte) ((value >> 16) % 256);
        array[address + 3] = (byte) ((value >> 24) % 256);
        ready = true;
    }


    public MemRecord getRecord()
    {
        return new MemRecord(rwn, start, ready);
    }
}
