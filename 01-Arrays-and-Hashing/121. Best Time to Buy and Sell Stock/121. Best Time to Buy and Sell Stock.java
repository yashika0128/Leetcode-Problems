1class Solution {
2    public int maxProfit(int[] prices) {
3        // int n= prices.length;
4        // int profit=0;
5        // int max_profit=0;
6        // for(int i=0;i<n;i++){
7        //     int buy = prices[i];
8        //     for(int j=i+1;j<n;j++){
9        //         profit= prices[j]-buy;
10        //         max_profit= Math.max(max_profit,profit);
11        //     }
12        // }
13        // return max_profit;
14
15        int minPrice = Integer.MAX_VALUE;
16        int maxProfit = 0;
17
18        for(int i = 0; i < prices.length; i++){
19            if(prices[i] < minPrice){
20                minPrice = prices[i];
21            } else {
22                int profit = prices[i] - minPrice;
23                maxProfit = Math.max(maxProfit, profit);
24            }
25        }
26
27        return maxProfit;
28    }
29}