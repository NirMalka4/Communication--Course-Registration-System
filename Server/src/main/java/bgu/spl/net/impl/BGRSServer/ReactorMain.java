package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;
import bgu.spl.net.impl.*;

public class ReactorMain {

    public static void main(String[] args) {
        Server.reactor(
                Integer.parseInt(args[1]),
                Integer.parseInt(args[0]), //port
                () ->  new MessagingProtocolImpl(), //protocol factory
                () ->  new MessageEncoderDecoderImpl() //message encoder decoder factory
        ).serve();

    }
}
