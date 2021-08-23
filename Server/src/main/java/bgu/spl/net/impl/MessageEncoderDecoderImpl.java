package bgu.spl.net.impl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import bgu.spl.net.api.MessageEncoderDecoder;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<Opcode> {
    
    private byte[] bytes = new byte[1 << 10];
    private int len = 0;
    private Integer fixedByte;
    private Integer dynamicByte;
    private Opcode opcode;
    private Boolean endOfMsg = false;

    @Override
    public Opcode decodeNextByte(byte nextByte){
        if (nextByte != 0 || opcode == null || fixedByte != null){
            pushByte(nextByte);

            if (opcode == null){
                if (len == 2) 
                {
                    opcode = Opcode.getProtocol(popShort());
                    fixedByte = opcode.getFixedSize();
                    dynamicByte = opcode.getDynamicSize();
                }
            }
            if (fixedByte != null && !endOfMsg){
                if (len == fixedByte){
                    opcode.setFixed(popShort());
                    endOfMsg = true;
                }
            }
            if (opcode != null && dynamicByte == null && fixedByte == null) endOfMsg = true;
            
        }
        else {
            if (dynamicByte == 2){
                opcode.set1(popString());
            }
            if (dynamicByte == 1){
                opcode.set2(popString());
                endOfMsg = true;
            }
            dynamicByte--;
        }

        if (endOfMsg){
            Opcode opcodeTemp = opcode;
            clean();
            return opcodeTemp;
        }
        else return null;
    }

    public byte[] encode(Opcode message){
        return message.asByte();
    }

    private void pushByte(byte nextByte){
        if (len >= bytes.length){
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++] = nextByte;
    }

    private String popString() {
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        bytes = new byte[1 << 10];
        return result
        ;
    }

    private short popShort() {
        short result = (short)((bytes[0] & 0xff) << 8);
        result += (short)(bytes[1] & 0xff);
        len = 0;
        bytes = new byte[1 << 10];
        return result;
    }

    public void clean(){
        fixedByte = null;
        dynamicByte = null;
        opcode = null; 
        endOfMsg = false;
        len = 0;
    }
}