package org.next.adapter

import org.apache.mina.core.RuntimeIoException
import org.apache.mina.core.future.ConnectFuture
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.logging.LoggingFilter
import org.apache.mina.transport.socket.nio.NioSocketConnector

class FrogAdapter {
      String host
      int port

    Session connect(){
        NioSocketConnector connector = new NioSocketConnector()
        connector.setConnectTimeoutMillis(500)

        connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(
                            FrogCodecFactory
                                    .builder()
                                    .decoder(new FrogProtocolDecoder())
                                    .encoder(new FrogProtocolEncoder())
                                    .build()))

        connector.getFilterChain().addLast("logger", new LoggingFilter())
        connector.setHandler(new FrogMessageHandler())
        IoSession session=null

        for (;;) {
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress(host, port))
                future.awaitUninterruptibly()
                session = future.getSession()
                break
            } catch (RuntimeIoException e) {
                println "Failed to connect."
                e.printStackTrace()
                Thread.sleep(5000)
            }
        }
       new Session(session)
    }
}
