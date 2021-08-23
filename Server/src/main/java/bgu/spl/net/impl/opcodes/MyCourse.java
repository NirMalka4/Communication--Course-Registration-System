package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class MyCourse extends Opcode{
    
    public MyCourse(){
        super((short)11, 0, 0);
    }

    @Override
    public Opcode act(Database db, String user){
        if (user == null) return new Err(getOpcode());
        if (db.isAdmin(user)) return new Err(getOpcode());
        return new Ack(getOpcode(), db.myCourses(user));
    }
}

