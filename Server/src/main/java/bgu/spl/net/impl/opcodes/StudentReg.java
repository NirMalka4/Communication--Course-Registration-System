package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class StudentReg extends Opcode {
    private String username;
    private String password;
    
    public StudentReg(){
        super((short)2, 0, 2);
    }

    public String get1(){
        return username;
    }

    public String get2(){
        return password;
    }

    public void set1(String username){
        this.username = username;
    }

    public void set2(String password){
        this.password = password;
    }

    @Override
    public Opcode act(Database db, String user){
        if (user != null) return new Err(getOpcode());
        if (!db.registerStudent(username, password)) return new Err(getOpcode());
        else return new Ack(getOpcode());
    }
}
