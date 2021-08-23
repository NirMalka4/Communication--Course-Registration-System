#ifndef ASSIGNMENT_3_DECODER_H
#define ASSIGNMENT_3_DECODER_H

#endif //ASSIGNMENT_3_DECODER_H

#include "string"
#include "unordered_map"
using::std::string;
using std::unordered_map;

class Decoder{
private:
    //Attach each server reply its corresponding value
    unordered_map<short,string> decodeUtil;
    //Used during decoding to update threads termination flag
    const string dismiss;
    void init();
    //Util function used to convert 2 bytes into short value
    short bytesToShort(char*,int,int);

public:
    Decoder();
    //Decode reply message to string in line with the assignment frame
    string decode(char*,bool&,unsigned short&);
};