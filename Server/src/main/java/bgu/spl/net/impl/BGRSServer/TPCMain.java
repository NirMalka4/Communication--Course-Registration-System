package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;
import bgu.spl.net.impl.*;

public class TPCMain {

    public static void main(String[] args) {
        Server.threadPerClient(
                Integer.parseInt(args[0]), //port
                () ->  new MessagingProtocolImpl(), //protocol factory
                () ->  new MessageEncoderDecoderImpl() //message encoder decoder factory
        ).serve();

    }
}
