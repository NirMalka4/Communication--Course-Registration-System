#ifndef ASSIGNMENT_3_ENCODER_H
#define ASSIGNMENT_3_ENCODER_H

#endif //ASSIGNMENT_3_ENCODER_H
#include "string"
#include "unordered_map"
#include "vector"
using::std::string;
using::std::vector;
using std::unordered_map;

class Encoder{

private:
    //Util map, attach each command its appropriate opcode
    unordered_map<string,short> encodeUtil;
    const char nullChar;
    //Initialize map
    void init();
    //Util function used to convert char value to byte(uint8=8 bits)
    void addBytes(string&,string&);
    //Util function used to convert short value to 2 byte(char) value-->opcode.
    void shortToBytes(short&,string& );
    //Util function used to tokenize keyboard input
    vector<string> tokenize(const string&);
    //encode IO inputs sharing mutual structure: contains two opcodes
    void containsTwoOpcode(string&,vector<string>&);
    //encode IO inputs sharing mutual structure: contains only one short number, appears at first argument
    void containsOneOpcode(string&,vector<string>&);

public:
    Encoder();
    //encode keyboard command to string according to assignment temple
    string encode(const string&);

};
