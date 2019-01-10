package org.next.tradable

import org.next.api.Price
import spock.lang.Specification
import spock.lang.Unroll

import static org.next.api.Price.*

/*
Price is stored in long. The actual price is stored value divided by 1 000 000
1.0  stored as 1000000
1.2  stores as 1200000
 */
class PriceSpec extends Specification {
    def debit(def p){
        Side.DEBIT.limit(p)

    }
    def debit(def p,def n){
        Side.DEBIT.limit(p,n)

    }
    def credit(def p){
        Side.CREDIT.limit(p)

    }
    def credit(def p,def n){
        Side.CREDIT.limit(p,n)

    }

    def "test decimal price creation zero decimal"(){
        given:
        Price p = debit(1.0)
        when:
        long value = p.value;
        then:
        value == 1000000
    }
    def "test decimal price creation one decimal"(){
        given:
        Price p = debit(1.1)
        when:
        long value = p.value;
        then:
        value == 1100000
    }
    def "test decimal price creation two decimals"(){
        given:
        Price p = debit(1.12)
        when:
        long value = p.value;
        then:
        value == 1120000
    }
    def "test decimal price creation tree decimals"(){
        given:
        Price p = debit(1.123)
        when:
        long value = p.value;
        then:
        value == 1123000
    }
    def "test decimal price creation four decimals"(){
        given:
        Price p = debit(4321.1234)
        when:
        long value = p.value;
        then:
        value == 4321123400
    }
    def "test decimal price creation five decimals"(){
        given:
        Price p = debit(54321.12345)
        when:
        long value = p.value;
        then:
        value == 54321123450
    }
    def "test decimal price creation six decimals"(){
        given:
        Price p = debit(654321.123456)
        when:
        long value = p.value;
        then:
        value == 654321123456
    }
    def "test decimal price creation with 7 digit whole and six decimals"(){
        given:
        Price p = debit(7654321.123456)
        when:
        long value = p.value;
        then:
        value == 7654321123456
    }

    def "test decimal price creation with 8 digit whole and six decimals"(){
        given:
        Price p = debit(87654321.123456)
        when:
        long value = p.value;
        then:
        value == 87654321123456
    }
    def "test price in 2 decimal points"(){
        given:
        Price p = debit(1001,2)
        when:
        long value = p.value;
        then:
        value == 10010000
    }
    def "test price in 3 decimal points"(){
        given:
        Price p = debit(1001,3)
        when:
        long value = p.value;
        then:
        value == 1001000
    }
    def "test price in 4 decimal points"(){
        given:
        Price p = debit(10001,4)
        when:
        long value = p.value;
        then:
        value == 1000100
    }
    def "test price in 5 decimal points"(){
        given:
        Price p = debit(10001,5)
        when:
        long value = p.value;
        then:
        value == 100010
    }
    def "test price in 6 decimal points"(){
        given:
        Price p = debit(10001,6)
        when:
        long value = p.value;
        then:
        value == 10001
    }

    def "test Non price to string"(){
        when:
        Price p = Price.bidAtMarket()
        then:
        p.toString() == 'MARKET PRICE'
    }
    def "test Market price to string"(){
        when:
        Price p = Price.none()
        then:
        p.toString() == 'NON PRICE'
    }
    def "test limit price to string"(){
        when:
        Price p = debit(1.234)
        then:
        p.toString() == '1.234 DEBIT'
    }
    def "When bid price equal to ask price canTrade should returns true"() {
        expect:
        debit(1.1).canTrade(credit(1.1)) == true
    }

    @Unroll
    def "when bid price = #bid and ask price = #ask then can trade is #canTrade "() {
        expect:
        bid.canTrade(ask) == canTrade

        where:
        bid          | ask           ||canTrade
        debit(1.1)| credit(1.1)||true
        debit(1.1)| credit(1.0)||true
        debit(1.1)| credit(1.2)||false
        bidAtMarket()| credit(1.2)||true
        debit(1.0)| sellAtMarket()||true
        none         | credit(1.0)||false
        debit(1.1)| none               ||false
    }
    @Unroll
    def "when sell price = #ask and buy price = #bid then can trade is #canTrade "() {
        expect:
        ask.canTrade(bid) == canTrade

        where:
        ask           | bid          ||canTrade
        credit(1.1)| debit(1.1)||true
        credit(1.1)| debit(1.0)||false
        credit(1.1)| debit(1.2)||true
        sellAtMarket()| debit(1.2)||true
        debit(1.0) | bidAtMarket()||true
        none          | debit(1.0)||false
        credit(1.1)| none         ||false
    }
}