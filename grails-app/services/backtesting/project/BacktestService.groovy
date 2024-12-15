package backtesting.project

import java.time.LocalDate

class BacktestService {

    YahooFinanceService yahooFinanceService
    StrategyService strategyService
    MetricsService metricsService

    Map<String, Object> performBacktest(LocalDate startDate, LocalDate endDate, Double initialCapital) {
        def portfolio = new Portfolio(date: startDate, holdings: [:], cashBalance: initialCapital)
        def returns = []
        def totalPortfolioValue = []

        while (startDate.isBefore(endDate)) {
            def stockData = yahooFinanceService.fetchStockData(['AAPL', 'MSFT', 'GOOGL', 'AMZN', 'TSLA', 'NVDA', 'META'])
            def selectedStocks = strategyService.selectStocks(stockData)

            // Rebalance portfolio
            def rebalanceResult = rebalancePortfolio(portfolio, stockData, selectedStocks)
            portfolio = rebalanceResult.portfolio
            returns << rebalanceResult.dailyReturn
            totalPortfolioValue << rebalanceResult.totalValue

            startDate = startDate.plusDays(1) // Increment by 1 day
        }

        return [
            sharpeRatio      : metricsService.calculateSharpeRatio(returns),
            maxDrawdown      : totalPortfolioValue.max() - totalPortfolioValue.min(),
            var              : metricsService.calculateVar(returns),
            avgDailyReturn   : returns.sum() / returns.size(),
            portfolioValue   : portfolio.cashBalance + portfolio.holdings.collect { symbol, quantity ->
                def stock = stockData.find { it.symbol == symbol }
                stock ? stock.price * quantity : 0.0
            }.sum(),
            holdings         : portfolio.holdings
        ]
    }

    private Map rebalancePortfolio(Portfolio portfolio, List<StockData> stockData, List<String> selectedStocks) {
    // Sell and buy stocks based on the strategy and update portfolio
    def newHoldings = [:]
    selectedStocks.each { symbol ->
        def stock = stockData.find { it.symbol == symbol }
        if (stock && stock.price) { // Ensure stock and price are valid
            def quantity = (portfolio.cashBalance / stock.price).toInteger()
            newHoldings[symbol] = quantity
            portfolio.cashBalance -= quantity * stock.price
        }
    }

    // Calculate the total portfolio value
    def totalValue = portfolio.cashBalance + newHoldings.collect { symbol, quantity ->
        def stock = stockData.find { it.symbol == symbol }
        if (stock?.price && quantity) {
            stock.price * quantity
        } else {
            0.0 // Default to 0.0 for invalid or missing values
        }
    }.sum(0.0) // Explicitly start summation from 0.0 to handle empty or null cases

    return [portfolio: portfolio, dailyReturn: 0.02, totalValue: totalValue]
}

}
