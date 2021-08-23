#include "../include/Encoder.h"
#include "string"
#include "vector"
#include "iostream"
#include "boost/lexical_cast.hpp"
using::std::stringstream;
using::std::string;
using std::unordered_map;
using::std::vector;



Encoder::Encoder():encodeUtil(),nullChar('\0') {init();}

void Encoder::init()
{
    encodeUtil["ADMINREG"] = 1;
    encodeUtil["STUDENTREG"] = 2;
    encodeUtil["LOGIN"] = 3;
    encodeUtil["LOGOUT"] = 4;
    encodeUtil["COURSEREG"] = 5;
    encodeUtil["KDAMCHECK"] = 6;
    encodeUtil["COURSESTAT"] = 7;
    encodeUtil["STUDENTSTAT"] = 8;
    encodeUtil["ISREGISTERED"] = 9;
    encodeUtil["UNREGISTER"] = 10;
    encodeUtil["MYCOURSES"] = 11;
}

void Encoder::addBytes(string& token, string& bytesArr)
{
    for (std::string::iterator it = token.begin(); it != token.end(); ++it)
    {
        uint8_t ch = *it;
        if (ch < 0x80) {
            bytesArr.push_back(ch);
        }
        else {
            bytesArr.push_back(0xc0 | ch >> 6);
            bytesArr.push_back(0x80 | (ch & 0x3f));
        }
    }
}

void Encoder::shortToBytes(short& num, string& bytesArr)
{
    bytesArr.push_back((num >> 8) & 0xFF);
    bytesArr.push_back((num & 0xFF));
}

string Encoder::encode(const string& data)
{
    //Tokenize input string
    vector<string>tokens=tokenize(data);
    //Determine command opcode,append it to encoded string(bytesArr)
    string opcode=tokens.front();//command opcode
    tokens.erase(tokens.begin());
    //Verify validity of the given opcode
    if(encodeUtil.find(opcode)==encodeUtil.end())
        throw std::invalid_argument("Illegal argument");
    string bytesArr;
    short num=encodeUtil.at(opcode);
    shortToBytes(num,bytesArr);
    //Add additional fields according to mutual frame
    if( (num==10) | (num==9) | (num==7)| (num==6) | (num==5))
        containsTwoOpcode(bytesArr,tokens);
    else if( (num==1) | (num==2) | (num==3) | (num==8))
        containsOneOpcode(bytesArr,tokens);
    return bytesArr;
}

vector<string> Encoder::tokenize(const string &data)
{
    vector<string> tokens;
    stringstream stream(data);
    string intermediate;
    while(getline(stream,intermediate,' ')){
        tokens.push_back(intermediate);
    }
    return tokens;
}

void Encoder::containsOneOpcode(string &bytesArr,vector<string>& tokens)
{
    int numOfStringFields=0;
    while(!tokens.empty()){
        string token=tokens.front();
        tokens.erase(tokens.begin());
        //data adjacent to the opcode shall not be separated by null char
        if(numOfStringFields==0)
            addBytes(token,bytesArr);
        else { // other fields will be separated by null char(such as Registration commands,Login,Logout)
            bytesArr.push_back(nullChar);
            addBytes(token,bytesArr);
        }
        numOfStringFields++;
    }
    bytesArr.push_back(nullChar);
}

void Encoder::containsTwoOpcode(string &bytesArr, vector<string>& tokens)
{
    string token=tokens.front();
    tokens.erase(tokens.begin());
    try
    {
        short secondOpCode = boost::lexical_cast<short>(token);//convert string to short
        shortToBytes(secondOpCode,bytesArr);//convert short to bytes
    }
    catch(boost::bad_lexical_cast &error)
    {
        throw std::invalid_argument("Invalid short value");
    }
}


