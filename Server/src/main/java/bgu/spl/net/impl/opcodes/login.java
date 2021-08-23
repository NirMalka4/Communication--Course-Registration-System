package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class login extends Opcode {
    private String username;
    private String password;
    
    public login(){
        super((short)3, 0, 2);
    }

    @Override
    public String get1(){
        return username;
    }

    @Override
    public String get2(){
        return password;
    }

    @Override
    public void set1(String username){
        this.username = username;
    }

    @Override
    public void set2(String password){
        this.password = password;
    }

    @Override
    public Opcode act(Database db, String name){
        if (!db.login(username, password)) return new Err(getOpcode());
        return new Ack(getOpcode());
    }
}
