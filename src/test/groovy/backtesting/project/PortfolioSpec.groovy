package backtesting.project

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PortfolioSpec extends Specification implements DomainUnitTest<Portfolio> {

     void "test domain constraints"() {
        when:
        Portfolio domain = new Portfolio()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
