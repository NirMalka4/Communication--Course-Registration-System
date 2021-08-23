package bgu.spl.net.impl;

import bgu.spl.net.impl.opcodes.*;

public abstract class Opcode {
    private short opcode;
    private Integer fixedSize = null;
    private Integer dynamicFields = null;


    public static Opcode getProtocol(short opcode){
        switch (opcode) {
            case 1:
                return new AdminReg();
        
            case 2:
                return new StudentReg();

            case 3:
                return new login();

            case 4:
                return new logout();
            
            case 5:
                return new CourseReg();

            case 6:
                return new KdamCheck();

            case 7:
                return new CourseStat();

            case 8:
                return new StudentStat();

            case 9:
                return new IsRegisterd();
            
            case 10:
                return new Unregister();

            case 11:
                return new MyCourse();

            default:
                return new Unknown(opcode);
        }
    }

    public Opcode(short opcode, Integer fixedSize, Integer dynamicFields){
        this.opcode = opcode;
        if (fixedSize == 0) this.fixedSize = null;
        else this.fixedSize = fixedSize;

        if (dynamicFields == 0) this.dynamicFields = null;
        else this.dynamicFields = dynamicFields;
    }

    public short getOpcode(){
        return opcode;
    }

    public Integer getFixedSize(){
        return fixedSize;
    }

    public Integer getDynamicSize(){
        return dynamicFields;
    }

    public void setFixed(short fixed) {
    }

    public short getFixed(){
        return (short)0;
    }

    public String get1(){
        return null;
    }

    public String get2(){
        return null;
    }

    public void set1(String set1){
    }

    public void set2(String set2){
    }

    public abstract Opcode act(Database db, String user);

    public byte[] asByte(){
        return null;
    }
}
