package org.next.feature.user

import org.next.specs.BaseSpec

class LoginSpec extends BaseSpec {
    def "Test login message"(){
        when:
        login 'ABC', 'ABC'
        sleep 3000
        then:
        notThrown(Exception)
    }

}