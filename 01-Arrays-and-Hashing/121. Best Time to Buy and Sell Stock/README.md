<h2><a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock">121. Best Time to Buy and Sell Stock</a></h2>

<p>You are given an array <code>prices</code> where <code>prices[i]</code> is the price of a given stock on the <code>i<sup>th</sup></code> day.</p>

<p>You want to maximize your profit by choosing a <strong>single day</strong> to buy one stock and choosing a <strong>different day in the future</strong> to sell that stock.</p>

<p>Return <em>the maximum profit you can achieve from this transaction</em>. If you cannot achieve any profit, return <code>0</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> prices = [7,1,5,3,6,4]
<strong>Output:</strong> 5
<strong>Explanation:</strong> Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> prices = [7,6,4,3,1]
<strong>Output:</strong> 0
<strong>Explanation:</strong> In this case, no transactions are done and the max profit = 0.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= prices.length &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= prices[i] &lt;= 10<sup>4</sup></code></li>
</ul>


---

# 🛍️ Best-Time-to-Buy-and-Sell-Stock | Explained

## Approach 1: Brute Force (Nested Loops)
### Intuition
The most straightforward way to solve this problem is to simulate every possible transaction. Imagine you are a trader looking back at historical stock prices. To find the maximum possible profit, you can calculate the profit for every possible pair of buying and selling days, provided that you must buy before you sell. 

By comparing the profit of every single valid combination, we guarantee finding the absolute maximum profit.

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start]) --> Init[Initialize max_profit = 0]
    Init --> LoopI[Outer Loop i: Buy Day\nFrom 0 to n-1]
    LoopI --> LoopJ[Inner Loop j: Sell Day\nFrom i+1 to n-1]
    LoopJ --> Calc[Calculate profit = prices[j] - prices[i]]
    Calc --> Update[max_profit = max(max_profit, profit)]
    Update --> NextJ{Has j reached n?}
    NextJ -- No --> LoopJ
    NextJ -- Yes --> NextI{Has i reached n?}
    NextI -- No --> LoopI
    NextI -- Yes --> Return([Return max_profit])
```

### Approach
1. Initialize a variable `max_profit` to `0` to keep track of the best profit found.
2. Run an outer loop with pointer `i` from `0` to `n - 1` representing the day we purchase the stock.
3. Run an inner loop with pointer `j` from `i + 1` to `n - 1` representing the day we sell the stock. The condition `j = i + 1` ensures we only sell on a day *after* we buy.
4. For each pair `(i, j)`, compute the profit: `prices[j] - prices[i]`.
5. If this profit is greater than our current `max_profit`, update `max_profit`.
6. After evaluating all pairs, return `max_profit`.

### Detailed Code Analysis
The commented-out code implements this exact brute force strategy:
* **Lines 3-5:** `int n = prices.length; int profit = 0; int max_profit = 0;` initialize our metadata trackers.
* **Line 6:** `for(int i=0;i<n;i++)` sets up the buy-day pointer `i`.
* **Line 7:** `int buy = prices[i];` captures the price on our hypothetical buy day to avoid repeated array lookups.
* **Line 8:** `for(int j=i+1;j<n;j++)` sets up the sell-day pointer `j`. Because `j` starts at `i + 1`, we preserve the temporal constraint of the stock market (no time travel).
* **Line 9:** `profit = prices[j]-buy;` calculates the difference between the selling price and the buying price.
* **Line 10:** `max_profit= Math.max(max_profit,profit);` updates the running maximum if the current transaction yields a higher profit.

### Code
```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int profit = 0;
        int max_profit = 0;
        for (int i = 0; i < n; i++) {
            int buy = prices[i];
            for (int j = i + 1; j < n; j++) {
                profit = prices[j] - buy;
                max_profit = Math.max(max_profit, profit);
            }
        }
        return max_profit;
    }
}
```

### Complexity
- **Time:** $O(N^2)$ where $N$ is the length of the `prices` array. In the worst case, we perform $\frac{N \times (N-1)}{2}$ comparisons. This will result in a **Time Limit Exceeded (TLE)** error on LeetCode for larger inputs.
- **Space:** $O(1)$ auxiliary space. We only use primitive variables (`n`, `profit`, `max_profit`, `buy`, `i`, `j`) which require a constant amount of memory regardless of the input array size.

---

## Approach 2: Optimized One-Pass (Greedy / Min-Tracking)
### Intuition
To optimize the brute force approach, we can observe that we do not need to compare every single pair. If we are scanning the prices chronological order, the best day to buy *prior* to any day `i` is simply the lowest price we have encountered up to day `i`. 

Imagine walking through the price list day by day. You keep track of the historically lowest price you've seen so far (`minPrice`). For any current day's price, if you were to sell today, your profit would be `prices[i] - minPrice`. By keeping track of the maximum profit obtained this way, we can solve the problem in a single pass.

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start]) --> Init[minPrice = Integer.MAX_VALUE\nmaxProfit = 0]
    Init --> Loop[Iterate through prices with index i]
    Loop --> Cond{prices[i] < minPrice?}
    Cond -- Yes --> UpdateMin[minPrice = prices[i]]
    Cond -- No --> CalcProfit[profit = prices[i] - minPrice\nmaxProfit = max(maxProfit, profit)]
    UpdateMin --> Next{More prices?}
    CalcProfit --> Next
    Next -- Yes --> Loop
    Next -- No --> Return([Return maxProfit])
```

### Approach
1. Initialize `minPrice` to the largest possible integer value (`Integer.MAX_VALUE`). This ensures that the very first price we evaluate will automatically become our initial baseline minimum.
2. Initialize `maxProfit` to `0`.
3. Loop through the `prices` array exactly once:
   - **Step A:** Check if the current price `prices[i]` is strictly less than our recorded `minPrice`. If it is, update `minPrice = prices[i]`. We do not calculate profit on this step because selling on the same day we set a new minimum price would yield a profit of `0`.
   - **Step B:** If the current price is greater than or equal to `minPrice`, it means we could potentially sell here for a profit. Calculate the potential profit: `prices[i] - minPrice`.
   - **Step C:** Update `maxProfit` if this potential profit is higher than our previous `maxProfit`.
4. Return `maxProfit`.

### Detailed Code Analysis
Let's trace your active code block:
* **Line 15:** `int minPrice = Integer.MAX_VALUE;` prepares our running minimum. Using the maximum value of a 32-bit signed integer ensures any element at index `0` will satisfy the first condition.
* **Line 16:** `int maxProfit = 0;` establishes our baseline profit.
* **Line 18:** `for(int i = 0; i < prices.length; i++)` initiates the single-pass $O(N)$ sweep.
* **Line 19-21:** 
  ```java
  if(prices[i] < minPrice){
      minPrice = prices[i];
  }
  ```
  This conditional branch is highly optimal. If we find a new minimum price, we do not need to execute the profit calculation logic because subtracting `minPrice` from itself yields `0`, which can never beat our initialized `maxProfit = 0` (or any existing positive profit).
* **Line 21-24:** 
  ```java
  else {
      int profit = prices[i] - minPrice;
      maxProfit = Math.max(maxProfit, profit);
  }
  ```
  If the price is higher than the minimum seen so far, we calculate how much profit we would make by selling today. We then conditionally update `maxProfit` using `Math.max`.

### Code
```java
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for(int i = 0; i < prices.length; i++){
            if(prices[i] < minPrice){
                minPrice = prices[i];
            } else {
                int profit = prices[i] - minPrice;
                maxProfit = Math.max(maxProfit, profit);
            }
        }

        return maxProfit;
    }
}
```

### Complexity
- **Time:** $O(N)$ where $N$ is the number of elements in `prices`. We perform exactly one pass through the array, performing constant-time $O(1)$ operations at each index.
- **Space:** $O(1)$ auxiliary space. We only store two integer primitives (`minPrice` and `maxProfit`), executing the algorithm entirely in-place.

---

## 🕵️‍♂️ Follow-up Questions

### 1. What if you are allowed to complete as many transactions as you like (i.e., Best Time to Buy and Sell Stock II)?
* **Answer:** If we can make multiple transactions, we no longer look for a single global minimum and maximum. Instead, we can use a greedy approach to harvest every single positive price increment. Every time the price increases from day $i-1$ to day $i$, we can buy on day $i-1$ and sell on day $i$.
```java
public int maxProfitII(int[] prices) {
    int maxProfit = 0;
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            maxProfit += prices[i] - prices[i - 1];
        }
    }
    return maxProfit;
}
```

### 2. How would you handle this problem if you were given a transaction fee for every trade?
* **Answer:** When transaction fees or cooldown periods are introduced, greedy choices do not work because buying/selling on a given day affects your ability to trade on future days. We must transition to a **Dynamic Programming (State Machine)** approach where we maintain two states for each day:
  1. `hold`: The maximum profit we can have on day `i` if we currently own a stock.
  2. `free` (or `sold`): The maximum profit we can have on day `i` if we do not own a stock.
  
  The recurrence relations would look like:
  - `hold[i] = Math.max(hold[i-1], free[i-1] - prices[i])`
  - `free[i] = Math.max(free[i-1], hold[i-1] + prices[i] - fee)`