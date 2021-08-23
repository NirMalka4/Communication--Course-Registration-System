#include "../include/TaskSocket.h"
#include "iostream"
#include "string"
using std:: cin;
using std::string;
using std::cout;
using std::endl;
using std::lock_guard;
using::std::unique_lock;


TaskSocket::TaskSocket(ConnectionHandler &connectionHandler,bool &terminate,mutex& mtx,condition_variable& cv)
        :_connectionHandler(connectionHandler),_terminate(terminate),_mutex(mtx),_conditionVariable(cv){}

void TaskSocket::run()
{
    while(!_terminate){
        unique_lock<mutex> lock(_mutex);
        _conditionVariable.wait(lock);//wait until being notified by thread manage IO tasks
        const short bufsize = 4;
        char buf[bufsize];
        unsigned short type;
        if (!_connectionHandler.getBytes(buf, bufsize)) {
            cout << "Disconnected. Exiting...\n" << endl;
            _terminate=true;
            break;
        }
        //Determine response type(ACK/ERROR), determine message opcode.
        string reply = _connectionHandler.determineResponseType(buf, _terminate, type);
        if (type == 12) { // if it's ACK response, retrieve it's additional data.
            string additionalData;
            if (!_connectionHandler.getLine(additionalData)) {
                cout << "Disconnected. Exiting...\n" << endl;
                _terminate=true;
                break;
            }
            if (!additionalData.empty()) {
                reply.append(additionalData);
            }
        }
        cout << reply << endl;
    }
}
