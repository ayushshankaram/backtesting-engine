package backtesting.project

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BacktestController {

    BacktestService backtestService

    def index() {
        render(view: 'index')
    }

    def runBacktest() {
        // Use DateTimeFormatter to parse date strings to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        def startDate = LocalDate.parse("2019-01-01", formatter)
        def endDate = LocalDate.parse("2020-01-02", formatter)

        def result = backtestService.performBacktest(
            startDate,
            endDate,
            10000.0
        )

        render(view: 'results', model: [result: result])
    }
}
