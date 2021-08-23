#ifndef ASSIGNMENT_3_TASKSOCKET_H
#define ASSIGNMENT_3_TASKSOCKET_H

#endif //ASSIGNMENT_3_TASKSOCKET_H
#include "thread"
#include "mutex"
#include "../include/connectionHandler.h"
#include <condition_variable>
using::std::condition_variable;
using::std::string;
using::std::mutex;

class TaskSocket{
private:
    //Mutual resource
    ConnectionHandler& _connectionHandler;
    //Mutual flag,shall be updated at each decode phase
    bool _terminate;
    //Either of the objects below used for handling the threads sync properly
    mutex& _mutex;
    condition_variable& _conditionVariable;


public:
    TaskSocket(ConnectionHandler&,bool&,mutex&,condition_variable&);
    //Mange replies from the server
    void run();
};