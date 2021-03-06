import java.util.Random;

/**
 * Created by alireza on 7/3/18.
 */
public class Memory {
    boolean ready;
    boolean rwn;
    boolean start;
    byte[] array = new byte[1024];
    CacheCell[] cache = new CacheCell[128];
    int hitNum;
    int missNum;
    {
        for (int i = 0; i < cache.length; i++)
            cache[i] = new CacheCell();
    }
    boolean reset;
    Recorder recorder;
    int haltclk = 0;
    boolean hasCache = false;
    public Memory(Recorder recorder){
        this.ready = false;
        this.rwn = false;
        this.start = false;
        this.reset = false;
        this.recorder = recorder;
    }

    {
        //int x = 0;
        //for (int i = 0;i<20;i++)
        //  x+=i;
        array[0] = 16;//bipush 0
        array[1] = 0;
        array[2] = 54;//istore x(2)
        array[3] = 2;
        array[4] = 16;//bipush 0
        array[5] = 0;
        array[6] = 54;//istore i(3)
        array[7] = 3;
        array[8] = 16;//bipush 19, label
        array[9] = 19;
        array[10] = 21;//iload i(3)
        array[11] = 3;
        array[12] = 100;//isub
        array[13] = (byte) 155;//iflt
        array[14] = 0;
        array[15] = 17;//later should complete
        array[16] = 21;//iload x
        array[17] = 2;
        array[18] = 21;
        array[19] = 3;//iload i
        array[20] = 96;//IADD
        array[21] = 54;//ISTORE x(2)
        array[22] = 2;
        array[23] = (byte)132;//iinc i(3) 1
        array[24] = 3;
        array[25] = 1;
        array[26] = (byte)167;
        array[27] = -1;
        array[28] = -18;
        array[30] = 10;

    }

    public int no_cache_read(int address)
    {
        int delay = (new Random()).nextInt(4) + 1;
        start = false;
        recorder.takeRecord();
        ready = false;
        for (int i = 0; i < delay; i++)
        {
            recorder.takeRecord();
            haltclk++;
        }
        ready = true;
        int alaki = ((array[address] & 0x000000FF) + ((array[address + 1] << 8) & 0x0000FF00) + ((array[address + 2] << 16) & 0x00FF0000)  + ((array[address + 3] << 24) & 0xFF000000))%256;

        System.out.println(alaki+ "Read");
        System.out.println((array[address] & 0x000000FF)+" "+((array[address + 1] << 8) & 0x0000FF00)+" "+((array[address + 2] << 16) & 0x00FF0000)+" "+ ((array[address + 3] << 24) & 0xFF000000)+"read");
        return (array[address] & 0x000000FF) + ((array[address + 1] << 8) & 0x0000FF00) + ((array[address + 2] << 16) & 0x00FF0000)  + ((array[address + 3] << 24) & 0xFF000000);
    }

    public int cache_read(int address)
    {
        if (!(isInCache(address) && isInCache(address + 1) &&
                isInCache(address + 2) && isInCache(address + 3)))
        {
            bringToCache(address);
            missNum++;
        }
        else
            hitNum++;

        return (cache[address % 128].value & 0x000000FF) +
                ((cache[(address + 1) % 128].value << 8) & 0x0000FF00) +
                ((cache[(address + 2) % 128].value << 16) & 0x00FF0000)
            + ((cache[(address + 3) % 128].value << 24) & 0xFF000000);

    }

    private void bringToCache(int address)
    {
        int delay = (new Random()).nextInt(4) + 1;
        for (int i = 0; i < delay; i++)
        {
            recorder.takeRecord();
            haltclk++;
        }
        for (int i = 0; i < 8; i++)
        {
            cache[(address + i) % 128].value = array[address + i];
            cache[(address + i) % 128].valid = true;
            cache[(address + i) % 128].tag = address / 128;
        }
    }

    public int read(int address)
    {
        if (!hasCache)
            return no_cache_read(address);
        return cache_read(address);
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
        System.out.println(address+ " " + array[address] +" "+ array[address+1] + " " + array[address+2] + " " + array[address+3] + "Write");
        if (hasCache)
        {
            missNum++;
            bringToCache(address);
        }

    }


    public boolean isInCache(int address)
    {
        return (cache[address % 128].valid && cache[address % 128].tag == (int)(address / 128));
    }



    public MemRecord getRecord()
    {
        return new MemRecord(rwn, start, ready);
    }
}

class CacheCell
{
    int tag = 0;
    byte value = 0;
    boolean valid = false;


}
