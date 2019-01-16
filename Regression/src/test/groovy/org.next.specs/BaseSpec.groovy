package org.next.specs

import org.next.adapter.FrogAdapter
import org.next.adapter.Session
import org.next.domain.LoginRequest
import org.next.domain.User
import spock.lang.Specification


class BaseSpec extends Specification {

    static Session session
    static {
        FrogAdapter adapter = new FrogAdapter()
        adapter.host = 'localhost'
        adapter.port = 8081
        session = adapter.connect()
    }

    User user

    def setup(){

    }

    def login(def user,def password){
        LoginRequest loginRequest = new LoginRequest()
        loginRequest.userId  = 'U1'
        loginRequest.password= 'P1'
        loginRequest.session = session
        loginRequest.send()
    }

}