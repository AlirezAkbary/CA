import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ali on 7/7/2018 AD.
 */
public class Assembler
{
    int instNum;
    public byte[] start(String input) throws Exception
    {
        HashMap<String, Integer> label = new HashMap<>();
        ArrayList<Pair> constant = new ArrayList<>();
        byte[] out = new byte[1025];
        ArrayList<String> var = new ArrayList<>();
        input = input.toLowerCase();
        String[] s = input.split("\\s+");
        int constN = -1;
        int varN = -1;
        int instN = -1;
        for (int i = 0; i < s.length; i++)
        {
            if (s[i].equals(".constant"))
                constN = i;
            else if (s[i].equals(".var"))
                varN = i;
            else if (s[i].equals(".main"))
                instN = i;
        }
        if (instN == -1)
            throw new Exception("Main method does not exist");
        if (varN != -1)
        {
            for (int i = varN + 1; !(s[i].equals(".end-var")); i++)
                var.add(s[i]);
        }

        if (constN != -1)
        {
            for (int i = constN + 1; !s[i].equals(".end-constant"); i += 2)
            {
                constant.add(new Pair(s[i], Integer.parseInt(s[i + 1])));
            }
        }

        for (int i = 0; i < constant.size(); i++)
        {
            int value = (int)constant.get(i).getValue();
            out[511 + i] = (byte) (value & 0xFF);
            value = value >> 8;
            out[511 + i + 1] = (byte) (value & 0xFF);
            value = value >> 8;
            out[511 + i + 2] = (byte) (value & 0xFF);
            value = value >> 8;
            out[511 + i + 3] = (byte) (value & 0xFF);

        }

        int memNum = 0;
        for (int i = instN + 1; !(s[i].equals(".end-main")); i++)
        {
            switch(s[i])
            {
                case "goto":
                case "ifeq":
                case "iflt":
                case "if_icmpeq":
                case "iinc":
                    memNum += 3;
                    break;
                case "bipush":
                case "iload":
                case "istore":
                    memNum += 2;
                    break;
                case "isub":
                case "iadd":
                case "nop":
                    memNum += 1;
                    break;
            }
            if (s[i].matches("\\S+:"))
                label.put(s[i].substring(0, s[i].length() - 1), memNum);

        }

//        for (String key : label.keySet())
//        {
//            System.out.println(key + " : " + label.get(key));
//        }
//
//        System.out.println("\nconstants");
//        for (int i = 0; i < constant.size(); i++)
//        {
//            System.out.println(constant.get(i).getKey() + " : " + constant.get(i).getValue());
//        }
//
//        System.out.println("\nvars");
//        for (int i = 0; i < var.size(); i++)
//        {
//            System.out.println(var.get(i));
//        }

        memNum = 0;
        int dest;
        for (int i = instN + 1; !s[i].equals(".end-main"); i++)
        {
            switch (s[i])
            {
                case "bipush":
                    out[memNum] = (byte) 0x10;
                    out[memNum + 1] = (byte) Integer.parseInt(s[i + 1]);
                    memNum += 2;
                    instNum++;
                    i++;
                    break;
                case "ifeq":
                    out[memNum] = (byte) 0x99;
                    dest = label.get(s[i + 1]);
                    out[memNum + 1] = (byte) (((dest - memNum) >> 8) % 256);
                    out[memNum + 2] = (byte) ((dest - memNum) % 256);
                    memNum += 3;
                    i++;
                    instNum++;
                    break;
                case "iflt":
                    out[memNum] = (byte) 0x9B;
                    dest = label.get(s[i + 1]);
                    out[memNum + 1] = (byte) (((dest - memNum) >> 8) % 256);
                    out[memNum + 2] = (byte) ((dest - memNum) % 256);
                    memNum += 3;
                    i++;
                    instNum++;
                    break;
                case "if_icmpeq":
                    out[memNum] = (byte) 0x9F;
                    dest = label.get(s[i + 1]);
                    out[memNum + 1] = (byte) (((dest - memNum) >> 8) % 256);
                    out[memNum + 2] = (byte) ((dest - memNum) % 256);
                    memNum += 3;
                    i++;
                    instNum++;
                    break;
                case "goto":
                    out[memNum] = (byte) 0xA7;
                    dest = label.get(s[i + 1]);
                    out[memNum + 1] = (byte) (((dest - memNum) >> 8) % 256);
                    out[memNum + 2] = (byte) ((dest - memNum) % 256);
                    memNum += 3;
                    i++;
                    instNum++;
                    break;
                case "iinc":
                    out[memNum] = (byte) 0x84;
                    out[memNum + 1] = (byte)(var.indexOf(s[i + 1]) * 4);
                    out[memNum + 2] = (byte) (Integer.parseInt(s[i + 2]));
                    memNum += 3;
                    i+= 2;
                    instNum++;
                    break;
                case "iload":
                    out[memNum] = 0x15;
                    out[memNum + 1] = (byte)(var.indexOf(s[i + 1]) * 4);
                    memNum += 2;
                    i++;
                    instNum++;
                    break;
                case "istore":
                    out[memNum] = 0x36;
                    out[memNum + 1] = (byte)(var.indexOf(s[i + 1]) * 4);
                    memNum += 2;
                    i++;
                    instNum++;
                    break;
                case "isub":
                    out[memNum] = 0x64;
                    memNum++;
                    instNum++;
                    break;
                case "iadd":
                    out[memNum] = 0x60;
                    memNum++;
                    instNum++;
                    break;
                case "nop":
                    out[memNum] = 0x00;
                    memNum++;
                    instNum++;
            }
        }

        out[memNum] = 10;

        String str = "";
        for (int i = 0; i < out.length; i++)
        {
            //str += String.format("0x%02X", out[i]) + "\n";
            str += out[i] + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("mem.txt"));
        writer.write(str);

        writer.close();

        return out;
    }
}

