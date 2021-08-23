package bgu.spl.net.impl.opcodes;

import bgu.spl.net.impl.Opcode;
import bgu.spl.net.impl.Database;

public class logout extends Opcode {
    
    public logout(){
        super((short)4, 0, 0);
    }

    @Override
    public Opcode act(Database db, String user){
        if (user == null) return new Err(getOpcode());
                db.setLogout(user);
                return new Ack(getOpcode());
    }
}
