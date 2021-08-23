package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class IsRegisterd extends Opcode{
    private short courseNum;
    
    public IsRegisterd(){
        super((short)9, 2, 0);
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
        return new Ack(getOpcode(),db.isRegistered((int)courseNum, user));
    }
}
