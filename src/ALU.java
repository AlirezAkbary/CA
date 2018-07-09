/**
 * Created by alireza on 7/3/18.
 */
public class ALU{
    String function;
    boolean zeroFlag;
    boolean negFlag;

    public ALU(){

        this.function = "000000";
        this.negFlag = false;
        this.zeroFlag = false;
    }
    public void setFunction(String function)
    {
        this.function = function;
    }

    public ALURecord getRecord()
    {
        return new ALURecord(this.negFlag, this.zeroFlag,this.function);
    }

    public int operation(int A, int B, String type){
        int result;
        switch (type){
            case "B":
                result = B;
                if (result == 0){
                    this.zeroFlag = true;
                    this.negFlag = false;
                }
                else if (result < 0){
                    this.zeroFlag = false;
                    this.negFlag = true;
                }
                else {
                    this.negFlag = false;
                    this.zeroFlag = false;
                }
                this.setFunction("010100");
                return result;
            case "A+B":
                result = A + B;
                if (result == 0){
                    this.zeroFlag = true;
                    this.negFlag = false;
                }
                else if (result < 0){
                    this.zeroFlag = false;
                    this.negFlag = true;
                }
                else {
                    this.negFlag = false;
                    this.zeroFlag = false;
                }
                this.setFunction("111100");
                return result;
            case "B+1":
                result = B + 1;
                if (result == 0){
                    this.zeroFlag = true;
                    this.negFlag = false;
                }
                else if (result < 0){
                    this.zeroFlag = false;
                    this.negFlag = true;
                }
                else {
                    this.negFlag = false;
                    this.zeroFlag = false;
                }
                this.setFunction("110101");
                return result;
            case "B-A":
                result = B - A;
                if (result == 0){
                    this.zeroFlag = true;
                    this.negFlag = false;
                }
                else if (result < 0){
                    this.zeroFlag = false;
                    this.negFlag = true;
                }
                else {
                    this.negFlag = false;
                    this.zeroFlag = false;
                }
                this.setFunction("111111");
                return result;
            case "B-1":
                result = B -1;
                if (result == 0){
                    this.zeroFlag = true;
                    this.negFlag = false;
                }
                else if (result < 0){
                    this.zeroFlag = false;
                    this.negFlag = true;
                }
                else {
                    this.negFlag = false;
                    this.zeroFlag = false;
                }
                this.setFunction("110110");
                return result;
            case "OR":
                result = A | B;
                if (result == 0){
                    this.zeroFlag = true;
                    this.negFlag = false;
                }
                else if (result < 0){
                    this.zeroFlag = false;
                    this.negFlag = true;
                }
                else {
                    this.negFlag = false;
                    this.zeroFlag = false;
                }
                this.setFunction("011100");
                return result;
            default:
                System.out.println("doesn't come here");
                this.negFlag = false;
                this.zeroFlag = false;
                return 0;
        }
    }
}
