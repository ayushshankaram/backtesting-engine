package backtesting.project

class MetricsService {

    Double calculateSharpeRatio(List<Double> returns, Double riskFreeRate = 0.02) {
        def avgReturn = returns.sum() / returns.size()
        def stdDev = Math.sqrt(returns.collect { (it - avgReturn) ** 2 }.sum() / returns.size())
        return (avgReturn - riskFreeRate) / stdDev
    }

    Double calculateVar(List<Double> returns, Double confidenceLevel = 0.95) {
        def sortedReturns = returns.sort()
        return sortedReturns[(1 - confidenceLevel) * sortedReturns.size()]
    }

    Double calculateSortinoRatio(List<Double> returns, Double riskFreeRate = 0.02) {
        def avgReturn = returns.sum() / returns.size()
        def downsideDeviation = Math.sqrt(returns.findAll { it < 0 }.collect { it ** 2 }.sum() / returns.size())
        return (avgReturn - riskFreeRate) / downsideDeviation
    }

    Double calculateMaxDrawdown(List<Double> prices) {
        double maxDrawdown = 0.0
        double peak = prices[0]
        prices.each { price ->
            if (price > peak) {
                peak = price
            }
            double drawdown = (peak - price) / peak
            if (drawdown > maxDrawdown) {
                maxDrawdown = drawdown
            }
        }
        return maxDrawdown
    }