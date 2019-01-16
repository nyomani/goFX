package org.next.domain

import org.next.adapter.Session

import java.nio.ByteBuffer

class LoginRequest {
    short  msgType=1
    String userId
    String password
    Session session
    def send(){
        short msgLen = 4+2+userId.trim().length()+2 + password.trim().length()
        ByteBuffer b = ByteBuffer.allocate(msgLen)
        b.putShort((short)(msgLen-2))
        b.putShort(msgType)
        b.putShort((short)userId.trim().length())
        b.put(userId.trim().bytes)
        b.putShort((short)password.trim().length())
        b.put(password.trim().bytes)
        b.rewind()
        byte[] data = new byte[msgLen]
        b.get(data)
        session.send(data)

    }
}
