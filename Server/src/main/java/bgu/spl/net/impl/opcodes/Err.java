package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class Err extends Opcode{
    
    private short msgOpcode;

    public Err(short opcode){
        super((short)13, 0, 0);
        msgOpcode = opcode;
    }

    public byte[] asByte(){
        byte[] bytesArr = new byte[4];
        bytesArr[0] = (byte)((getOpcode() >> 8) & 0xFF);
        bytesArr[1] = (byte)(getOpcode() & 0xFF);
        bytesArr[2] = (byte)((msgOpcode >> 8) & 0xFF);
        bytesArr[3] = (byte)(msgOpcode & 0xFF);
        return bytesArr;
    }

    @Override
    public Opcode act(Database db, String user){
        return null;
    }
    
}