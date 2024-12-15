package backtesting.project

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class BacktestServiceSpec extends Specification implements ServiceUnitTest<BacktestService> {

     void "test something"() {
        expect:
        service.doSomething()
     }
}
