package backtesting.project

import groovy.json.JsonSlurper

class YahooFinanceService {

    String finnhubApiKey = "ctanvfpr01qrt5hi41n0ctanvfpr01qrt5hi41ng"
    String baseUrl = "https://finnhub.io/api/v1"
    int rateLimitDelay = 1000 // 1 second delay for free-tier rate limits

    List<StockData> fetchStockData(List<String> symbols) {
        List<StockData> stockDataList = []

        // Iterate over symbols
        symbols.eachWithIndex { String symbol, int index ->
            try {
                println "Fetching data for symbol: ${symbol}"

                // API URLs
                String quoteUrl = "${baseUrl}/quote?symbol=${symbol}&token=${finnhubApiKey}"
                String metricsUrl = "${baseUrl}/stock/metric?symbol=${symbol}&metric=all&token=${finnhubApiKey}"

                // Fetch API responses
                Map quoteResponse = fetchApiResponse(quoteUrl)
                Map metricsResponse = fetchApiResponse(metricsUrl)

                // Validate responses
                if (!quoteResponse || !metricsResponse) {
                    throw new IOException("Invalid response for symbol: ${symbol}")
                }

                // Extract stock data
                Double currentPrice = quoteResponse["c"] as Double ?: 0.0
                Double peRatio = metricsResponse?.metric?.peNormalizedAnnual as Double ?: 0.0
                Double pbRatio = metricsResponse?.metric?.pbAnnual as Double ?: 0.0

                // Create and add StockData object
                stockDataList.add(new StockData(
                    symbol: symbol,
                    date: new Date(),
                    price: currentPrice,
                    peRatio: peRatio,
                    pbRatio: pbRatio
                ))

                // Rate limiting
                if (index < symbols.size() - 1) {
                    println "Rate limiting: Waiting for ${rateLimitDelay} ms."
                    Thread.sleep(rateLimitDelay)
                }
            } catch (Exception e) {
                println "Error fetching data for symbol ${symbol}: ${e.message}"
            }
        }

        return stockDataList
    }

    private Map fetchApiResponse(String url) {
        println "Fetching URL: ${url}"
        def connection = new URL(url).openConnection()
        connection.setRequestMethod("GET")

        if (connection.responseCode == 200) {
            String responseText = connection.inputStream.text
            return new JsonSlurper().parseText(responseText)
        } else {
            throw new IOException("Failed to fetch data from API. Response Code: ${connection.responseCode}")
        }
    }
}
