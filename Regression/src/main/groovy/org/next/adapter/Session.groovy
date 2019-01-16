package org.next.adapter

import org.apache.mina.core.session.IoSession
import org.next.domain.User

class Session {

    IoSession ioSession

    Session(IoSession s){
        this.ioSession = s
    }

    def send(byte[] data){
        ioSession.write(data)
    }
    def login(String userId,String password){

        User u=  new User()
        u.userName = userId
    }
}
