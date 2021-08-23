package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class Unregister extends Opcode{
    private short courseNum;
    
    public Unregister(){
        super((short)10, 2, 0);
    }

    public short getFixed(){
        return courseNum;
    }

    public void setFixed(short courseNum){
        this.courseNum = courseNum;
    }

    @Override
    public Opcode act(Database db, String user){
        if (user == null) return new Err(getOpcode());
        if (db.isAdmin(user)) return new Err(getOpcode());
        if (db.unregister(user, (int)courseNum)) return new Ack(getOpcode());
        else return new Err(getOpcode());
    }
}
