<h1>Backtest Results</h1>
<p>Sharpe Ratio: ${sharpeRatio}</p>
<p>Max Drawdown: ${maxDrawdown}</p>
<p>Value at Risk (VaR): ${var}</p>
<p>Average Daily Return: ${avgDailyReturn}</p>
<p>Total Portfolio Value: ${portfolioValue}</p>
<h2>Holdings</h2>
<ul>
    <g:each var="holding" in="${holdings}">
        <li>${holding.key}: ${holding.value}</li>
    </g:each>
</ul>
