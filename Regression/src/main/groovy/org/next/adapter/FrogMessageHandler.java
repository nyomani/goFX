package org.next.adapter;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.nio.ByteBuffer;

public class FrogMessageHandler implements IoHandler {

    public FrogMessageHandler(){
    }

    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("sessionCreated");

    }

    @Override
    public void sessionOpened(IoSession session) {
        System.out.println("sessionOpened");


    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("Session Closed");

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("sessionIdle");

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("Exception caught "+cause);
        cause.printStackTrace();

    }

    @Override
    public void messageReceived(IoSession session, Object message) {
           ByteBuffer data =(ByteBuffer) message;
           short messageType = data.getShort();

    }

    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("Message sent!");
    }

    @Override
    public void inputClosed(IoSession session) {

    }
}
