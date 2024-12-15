package backtesting.project

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class BacktestControllerSpec extends Specification implements ControllerUnitTest<BacktestController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
