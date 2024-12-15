package backtesting.project

import java.util.Date

class Portfolio {
    Date date
    Map<String, Double> holdings // Stock symbol and quantity
    Double cashBalance
    Map<String, Double> industryAllocations // Industry and percentage allocation
    Double riskScore // Overall risk score for the portfolio
    static constraints = {}

    /**
     * Updates the portfolio based on a transaction.
     * @param stockSymbol Symbol of the stock.
     * @param quantity Quantity to buy/sell (negative for sell).
     * @param price Current price of the stock.
     */
    void updateHoldings(String stockSymbol, double quantity, double price) {
        if (!holdings.containsKey(stockSymbol)) {
            holdings[stockSymbol] = 0.0
        }
        holdings[stockSymbol] += quantity
        cashBalance -= quantity * price
        if (holdings[stockSymbol] <= 0) {
            holdings.remove(stockSymbol)
        }
    }

    /**
     * Recalculates industry allocations based on holdings.
     * @param stockIndustryMap Map of stock symbols to their industries.
     */
    void recalculateIndustryAllocations(Map<String, String> stockIndustryMap) {
        Map<String, Double> allocation = [:]
        double totalValue = holdings.collect { symbol, quantity ->
            stockIndustryMap.containsKey(symbol) ? quantity : 0
        }.sum()

        holdings.each { symbol, quantity ->
            String industry = stockIndustryMap[symbol] ?: "Unknown"
            allocation[industry] = allocation.getOrDefault(industry, 0.0) + quantity / totalValue
        }

        industryAllocations = allocation
    }

    /**
     * Updates the risk score based on portfolio volatility and diversification.
     * @param volatilityMap Map of stock symbols to their volatility.
     */
    void updateRiskScore(Map<String, Double> volatilityMap) {
        double totalVolatility = holdings.collect { symbol, quantity ->
            volatilityMap[symbol] ?: 0.0
        }.sum()
        riskScore = totalVolatility / holdings.size()
    }
}
