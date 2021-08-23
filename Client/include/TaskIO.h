#ifndef ASSIGNMENT_3_TASKIO_H
#define ASSIGNMENT_3_TASKIO_H

#endif //ASSIGNMENT_3_TASKIO_H
#include "thread"
#include "../include/connectionHandler.h"
#include <condition_variable>
#include <mutex>
using::std::condition_variable;
using::std::mutex;
using::std::string;

class TaskIO{
private:
    //Mutual resource
    ConnectionHandler& _connectionHandler;
    //Mutual flag,shall be updated at each decode phase
    bool& _terminate;
    //Either of the objects below used for handling the threads sync properly
    mutex& _mutex;
    condition_variable& _conditionVariable;


public:
    TaskIO(ConnectionHandler&,bool&,mutex&,condition_variable&);
    //Manage IO taks
    void run();
};