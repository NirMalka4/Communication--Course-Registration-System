//
// Created by spl211 on 03/01/2021.
//
#include "../include/TaskIO.h"
#include "iostream"
#include "string"
using std:: cin;
using std::string;
using std::cout;
using std::endl;
using::std::unique_lock;

TaskIO::TaskIO(ConnectionHandler &connectionHandler,bool &terminate,mutex& mtx,condition_variable& cv)
        :_connectionHandler(connectionHandler),_terminate(terminate),_mutex(mtx),_conditionVariable(cv){}

void TaskIO::run()
{
    while(!_terminate)
    {
        const short bufsize = 1024;
        char buf[bufsize];
        cin.getline(buf, bufsize);
        string line(buf);
        string encoded= _connectionHandler.encode(line);
        //We can directly assign the address of 1st character of encoded to a pointer to char*.
        const char* bytes= &encoded[0];
        unsigned int bytesToWrite=encoded.length();
        if (!_connectionHandler.sendBytes(bytes,bytesToWrite)) {
            cout << "Disconnected. Exiting...\n" << endl;
            _terminate=true;
            break;
        }
        //Notify thread manage socket duties that a server response is expected
        unique_lock<mutex> lock(_mutex);
        _conditionVariable.notify_one();
    }
}