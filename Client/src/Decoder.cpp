#include "string"
#include "../include/Decoder.h"
using::std::string;
using std::unordered_map;

Decoder::Decoder() : decodeUtil(),dismiss("ACK 4"){init();}

void Decoder::init()
{
    decodeUtil[1]="1";
    decodeUtil[2]="2";
    decodeUtil[3]="3";
    decodeUtil[4]="4";
    decodeUtil[5]="5";
    decodeUtil[6]="6";
    decodeUtil[7]="7";
    decodeUtil[8]="8";
    decodeUtil[9]="9";
    decodeUtil[10]="10";
    decodeUtil[11]="11";
    decodeUtil[12]= "ACK";
    decodeUtil[13]= "ERROR";
}

short Decoder::bytesToShort(char *bytesArr, int start, int end)
{
    short result = (short)((bytesArr[start] & 0xff) << 8);
    result += (short)(bytesArr[end] & 0xff);
    return result;
}

string Decoder::decode(char* bytesArr, bool &terminate,unsigned short& type)
{
    string output;
    //Conduct two iteration, at first add the appropriate server response:ACK/ERROR to output string
    //at second concatenate the reply message opcode, update terminate value.
    for(int i=0; i<3; i=i+2)
    {
        short opcode=bytesToShort(bytesArr,i,i+1); // convert 2 adjacent bytes represent opcode into short value
        if(decodeUtil.find(opcode)==decodeUtil.end())
            throw std::invalid_argument("Illegal opcode");
        output.append(decodeUtil.at(opcode)); // convert short opcode into string,append to output string
        if(i==0) // update type only at first iteration
            type=opcode;
        //verify whether its ACK in response to logout request;
        if(i==2) {
            terminate = output == dismiss;
            break;
        }
        output.append(" ");
    }
    return output;
}

