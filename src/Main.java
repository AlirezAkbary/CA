/**
 * Created by ali on 7/7/2018 AD.
 */
public class Main
{
    public static void main(String[] args)
    {
        Recorder recorder = new Recorder();
        Processor processor = new Processor(recorder);
        processor.start();
        for (Record record : recorder.records)
        {
            record.print();
        }
    }
}
