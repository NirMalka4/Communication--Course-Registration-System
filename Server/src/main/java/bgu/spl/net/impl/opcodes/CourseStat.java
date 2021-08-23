package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.Opcode;

public class CourseStat extends Opcode{
    private short courseNum;
    
    public CourseStat(){
        super((short)7, 2, 0);
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
        if (db.isAdmin(user)){
            return new Ack(getOpcode(), db.courseStatus((int)courseNum));
        }
        else return new Err(getOpcode());
    }
}
