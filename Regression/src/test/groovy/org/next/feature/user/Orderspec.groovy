package org.next.feature.user

import org.next.domain.Order
import org.next.specs.BaseSpec

class Orderspec extends BaseSpec {

    def "test order"(){
        given:
            Order o = new Order()
            o.session = session
            o.displayQuantity=10
            o.instrument=111111
            o.orderType=1
            o.priceType=1
            o.decimal=4
            o.price=10000000
            o.priorityType=1
            o.quantity=10
            o.timeInForce='D'
            o.userToken=11111111
        when:
            o.send()
        then:
            notThrown(Exception)

    }

}