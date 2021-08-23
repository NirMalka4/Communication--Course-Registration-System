package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class StudentStat extends Opcode{
    private String username;
    
    public StudentStat(){
        super((short)8, 0, 1);
    }

    @Override
    public String get2(){
        return username;
    }

    @Override
    public void set2(String username){
        this.username = username;
    }

    @Override
    public Opcode act(Database db, String user){
        if (user == null) return new Err(getOpcode());
        if (db.isAdmin(user)){
            String result = db.studentStatus(username);
            if (result != null && !db.isAdmin(username)) return new Ack(getOpcode(), result);
        }
        return new Err(getOpcode());
    }
}

