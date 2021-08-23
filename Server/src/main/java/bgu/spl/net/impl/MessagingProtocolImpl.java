package bgu.spl.net.impl;

import bgu.spl.net.api.*;

public class MessagingProtocolImpl implements MessagingProtocol<Opcode>{
    
    // Opcodes
    private final short ADMINREG     = (short)1;
    private final short STUDENTREG   = (short)2;
    private final short LOGIN        = (short)3;
    private final short LOGOUT       = (short)4;
    private final short COURSEREG    = (short)5;
    private final short KDAMCHECK    = (short)6;
    private final short COURSESTAT   = (short)7;
    private final short STUDENTSTAT  = (short)8;
    private final short ISREGISTERED = (short)9;
    private final short UNREGISTER   = (short)10;
    private final short MYCOURSES    = (short)11;
    private final short ACK          = (short)12;
    private final short ERROR        = (short)13;


    // data members
    private String user = null; // if null then user did not login 
    private Database db = null;
    private Boolean terminate = false;

    public Opcode process(Opcode opcode) {

        if (db == null) db = Database.getInstance();

        Opcode result = opcode.act(db, user);

        switch (opcode.getOpcode()){
            case (LOGIN):
                if (result.getOpcode() == ACK) user = opcode.get1();
                break;

            case (LOGOUT):
            if (result.getOpcode() == ACK) {
                terminate = true;
            }

            break;
        }

        return result;
    }


    public boolean shouldTerminate() {
        return terminate;
    }
    
}
