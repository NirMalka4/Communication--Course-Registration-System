#include "../include/connectionHandler.h"
#include "../include/TaskIO.h"
#include "../include/TaskSocket.h"
#include "mutex"
#include "thread"
#include "iostream"
#include <condition_variable>
using std::cerr;
using std:: string;
using std::endl;
using::std::mutex;
using::std::thread;
using::std::condition_variable;
using::std::unique_lock;

int main (int argc, char *argv[]) {
    //Verify input
    if (argc < 3)
    {
        cerr << "Usage: " << argv[0] << " host port" << endl << endl;
        return -1;
    }
    string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect())
    {
        cerr << "Cannot connect to " << host << ":" << port << endl;
        return 1;
    }
    //Create 2 threads with different duties(IO,Socket),sharing mutual resources
    mutex mutualMutex;
    condition_variable mutualConditionVar;
    bool mutualTermination=false;

    TaskIO taskIO(connectionHandler,mutualTermination,mutualMutex,mutualConditionVar);
    TaskSocket taskSocketIO(connectionHandler,mutualTermination,mutualMutex,mutualConditionVar);

    thread threadIO(&TaskIO::run,&taskIO);
    thread socketThread(&TaskSocket::run,&taskSocketIO);

    socketThread.join();
    threadIO.detach();//Once socketThread has being terminated,threadIO will be canceled

    return 0;
}



