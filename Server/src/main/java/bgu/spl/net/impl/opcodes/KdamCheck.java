package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class KdamCheck extends Opcode {
    private short courseNum;
    
    public KdamCheck(){
        super((short)6, 2, 0);
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
        String result = db.kadmCheck((int)courseNum).toString();
        if (result.equals(null)) return new Err(getOpcode());
        else return new Ack(getOpcode(), result);
    }
}
