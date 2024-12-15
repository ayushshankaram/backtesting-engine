package backtesting.project

cclass StockData {
    String symbol
    String industry
    double peRatio
    double pbRatio
    double earningsGrowthRate
    double momentum
    double volatility
    double score // Calculated score (used internally)
}
