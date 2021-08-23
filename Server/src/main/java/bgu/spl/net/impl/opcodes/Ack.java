package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;

import java.util.Arrays;

import bgu.spl.net.impl.Database;

public class Ack extends Opcode{

    private String message;
    private short msgOpcode;

    public Ack(short opcode, String msg){
        super((short)12, 0, 0);
        msgOpcode = opcode;
        message =  msg;
    }

    public Ack(short opcode){
        super((short)12, 0, 0);
        msgOpcode = opcode;
    }

    public byte[] asByte(){
        byte[] bytesArr = new byte[5];
        bytesArr[0] = (byte)((getOpcode() >> 8) & 0xFF);
        bytesArr[1] = (byte)(getOpcode() & 0xFF);
        bytesArr[2] = (byte)((msgOpcode >> 8) & 0xFF);
        bytesArr[3] = (byte)(msgOpcode & 0xFF);

        if (message != null) bytesArr = unite(bytesArr, message);

        bytesArr[bytesArr.length-1] = 0;
        return bytesArr;
    }

    private byte[] unite(byte[] bytes, String msg){
        bytes = Arrays.copyOf(bytes, 6 + msg.length());
        bytes[4] = (short)10;
        byte[] strByte = (msg).getBytes();
        for (int i=0; i <= strByte.length-1; i++){
            bytes[i+5] = strByte[i];
        }
        return bytes;
    }

    @Override
    public Opcode act(Database db, String user){
        return null;
    }
    
}
