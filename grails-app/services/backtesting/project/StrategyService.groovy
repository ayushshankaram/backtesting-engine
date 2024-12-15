package backtesting.project

import groovy.transform.CompileStatic

@CompileStatic
class StrategyService {

    /**
     * Selects a diversified portfolio of stocks based on a multi-factor strategy.
     * Strategy includes:
     * - Low P/E and P/B (value factor)
     * - High earnings growth rate (growth factor)
     * - Positive momentum (technical factor)
     * - Risk filters (e.g., low volatility, stable beta)
     * - Industry diversification
     *
     * @param stockData List of StockData objects with metrics
     * @return List of selected stock symbols
     */
    List<String> selectStocks(List<StockData> stockData) {
        if (!stockData) return []

        // Filter out stocks with missing or invalid data
        List<StockData> filteredData = stockData.findAll {
            it.peRatio > 0 && it.pbRatio > 0 && it.earningsGrowthRate >= 0 && it.momentum >= 0
        }

        // Apply scoring function for each stock
        filteredData.each { stock ->
            stock.score = calculateScore(stock)
        }

        // Sort stocks by score in descending order (higher score is better)
        filteredData.sort { -it.score }

        // Select top stocks with industry diversification
        return selectDiversifiedPortfolio(filteredData, 5, 2).collect { it.symbol }
    }

    /**
     * Calculates a composite score for a stock based on multiple factors.
     *
     * @param stock StockData object
     * @return Composite score
     */
    private double calculateScore(StockData stock) {
        double valueFactor = 1 / (stock.peRatio + stock.pbRatio) // Low P/E and P/B preferred
        double growthFactor = stock.earningsGrowthRate          // High earnings growth preferred
        double momentumFactor = stock.momentum                  // Positive momentum preferred
        double riskFactor = 1 / (stock.volatility + 0.1)         // Low volatility preferred

        // Weighted scoring
        return (0.4 * valueFactor) + (0.3 * growthFactor) + (0.2 * momentumFactor) + (0.1 * riskFactor)
    }

    /**
     * Selects a diversified portfolio by limiting the number of stocks per industry.
     *
     * @param sortedStocks List of sorted StockData by score
     * @param maxStocks Total number of stocks to select
     * @param maxPerIndustry Maximum number of stocks per industry
     * @return List of selected StockData objects
     */
    private List<StockData> selectDiversifiedPortfolio(List<StockData> sortedStocks, int maxStocks, int maxPerIndustry) {
        Map<String, Integer> industryCount = [:].withDefault { 0 }
        List<StockData> selected = []

        for (stock in sortedStocks) {
            if (selected.size() >= maxStocks) break
            if (industryCount[stock.industry] < maxPerIndustry) {
                selected << stock
                industryCount[stock.industry]++
            }
        }

        return selected
    }
}

/**
 * Represents stock data with various financial and technical metrics.
 */
class StockData {
    String symbol
    String industry
    double peRatio
    double pbRatio
    double earningsGrowthRate
    double momentum
    double volatility
    double score // Calculated score (used internally)
}
