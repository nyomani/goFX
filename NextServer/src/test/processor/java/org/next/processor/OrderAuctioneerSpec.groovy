package org.next.processor

import org.next.processor.order.OrderAuctioneer
import org.next.tradable.Auction
import org.next.tradable.AuctionTask
import org.next.tradable.Interest
import org.next.tradable.OrderInterest
import spock.lang.Specification

import java.util.function.Consumer
import java.util.function.Predicate

class OrderAuctioneerSpec extends Specification {
    Consumer<Auction> auctionMatchProcessor
    Consumer<Interest> regNMSProcessor
    Consumer<AuctionTask> auctionTimer
    Predicate<OrderInterest> auctionValidator
    OrderInterest order
    OrderAuctioneer auctioneer
    Auction auction

    def setup(){
        auctionValidator=Mock()
        auctionMatchProcessor = Mock()
        regNMSProcessor = Mock()
        auctionTimer = Mock()
        order = Mock()
        auction = Mock()
        order.createAuction() >> auction
        auction.getOrder() >> order
        auctioneer=OrderAuctioneer.builder()
                .auctionValidator(auctionValidator)
                .regNMSProcessor(regNMSProcessor)
                .auctionTimer(auctionTimer)
                .auctionMatchProcessor(auctionMatchProcessor).build()


    }
    def "order is eligible to auction, the auction timer accept should be called once"() {
        given:
            auctionValidator.test(order) >> true
        when:
            auctioneer.accept(order)
        then:
          1 *  auctionTimer.accept(_)
    }

    def "order is not eligible to auction, the auction timer accept should not be called once"() {
        given:
            auctionValidator.test(order) >> false
        when:
            auctioneer.accept(order)
        then:
            0 *  auctionTimer.accept(_)
    }

    def "After auction expired, auction match processor should be called once"() {
        given:
            auctionValidator.test(order) >> true
            auctionTimer.accept(_) >>{args ->
                args[0].run()
            }
        when:
            auctioneer.accept(order)
        then:
            1 *  auctionMatchProcessor.accept(auction)
    }
    def "After auction expired, regNMS match processor should be called once"() {
        given:
            auctionValidator.test(order) >> true
            auctionTimer.accept(_) >>{args ->
                args[0].run()
            }
        when:
            auctioneer.accept(order)
        then:
            1 *  regNMSProcessor.accept(order)
    }

}