/**
 * Created by alireza on 7/3/18.
 */
public class Register
{
    int data;
    boolean reset;
    boolean load;
    public Register(int data){
        this.data = data;
        this.reset = false;
        this.load = false;
    }

    public RegRecord getRecord()
    {
        return new RegRecord(data, reset, load);
    }
}
