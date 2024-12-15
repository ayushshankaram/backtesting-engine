package backtesting.project

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class StrategyServiceSpec extends Specification implements ServiceUnitTest<StrategyService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
