package org.next.domain

import org.next.adapter.Session

import java.nio.ByteBuffer

class Order {

    short type = 100
    int   quantity
    int   displayQuantity
    short priceType
    short decimal
    int   price
    long  instrument
    short priorityType
    char  timeInForce
    short orderType
    int   userToken
    String clientId

    Session session

    def send() {
        short compressedPriceType = priorityType<<4|decimal
        short msgLen = 40
        ByteBuffer b = ByteBuffer.allocate(msgLen)
        b.putShort((short)(msgLen-2))         //2 0,1
        b.putShort(type)                      //2 2,3
        b.putLong(instrument)                 //8 4,11
        b.putInt(quantity)                    //4 12,15
        b.putInt(displayQuantity)             //4 16,19
        b.putChar(compressedPriceType)        //1 20,20
        b.putInt(price)                       //4 21,24
        b.putChar(priorityType)               //1 25,25
        b.putChar(timeInForce)                //1 26,26
        b.putShort(orderType)                 //1 27,27
        b.putInt(userToken)                   //4 28,31
        b.put(clientId.bytes)                 //8 32,39
        b.rewind()
        byte[] data = new byte[msgLen]
        b.get(data)
        session.send(data)
    }
}
