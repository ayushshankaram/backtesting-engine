package backtesting.project

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class StockdataSpec extends Specification implements DomainUnitTest<StockData> {

     void "test domain constraints"() {
        when:
        StockData domain = new StockData()
        //TODO: Set domain props here

        then:
        domain.validate()
     }
}
