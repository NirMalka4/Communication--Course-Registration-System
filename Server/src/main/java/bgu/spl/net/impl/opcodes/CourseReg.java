package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class CourseReg extends Opcode {
    private short courseNum;
    
    public CourseReg(){
        super((short)5, 2, 0);
    }

    @Override
    public short getFixed(){
        return courseNum;
    }

    @Override
    public void setFixed(short courseNum){
        this.courseNum = courseNum;
    }

    @Override
    public Opcode act(Database db, String user){
        if (user == null) return new Err(getOpcode());
        if (db.isAdmin(user)) return new Err(getOpcode());
        if (!db.CourseReg(user, (int)courseNum)) return new Err(getOpcode());
        return new Ack(getOpcode());
    }
}
