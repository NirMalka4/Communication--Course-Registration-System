package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class Unknown extends Opcode {
    
    public Unknown(short opcode){
        super((short)opcode, 0, 0);
    }

    public Opcode act(Database db, String user){
        return new Err(getOpcode());
    }
}
