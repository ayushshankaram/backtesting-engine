package backtesting.project

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class YahooFinanceServiceSpec extends Specification implements ServiceUnitTest<YahooFinanceService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
