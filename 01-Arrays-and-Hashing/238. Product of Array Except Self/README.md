<h2><a href="https://leetcode.com/problems/product-of-array-except-self">238. Product of Array Except Self</a></h2>

<p>Given an integer array <code>nums</code>, return <em>an array</em> <code>answer</code> <em>such that</em> <code>answer[i]</code> <em>is equal to the product of all the elements of</em> <code>nums</code> <em>except</em> <code>nums[i]</code>.</p>

<p>The product of any prefix or suffix of <code>nums</code> is <strong>guaranteed</strong> to fit in a <strong>32-bit</strong> integer.</p>

<p>You must write an algorithm that runs in&nbsp;<code>O(n)</code>&nbsp;time and without using the division operation.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> nums = [1,2,3,4]
<strong>Output:</strong> [24,12,8,6]
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> nums = [-1,1,0,-3,3]
<strong>Output:</strong> [0,0,9,0,0]
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-30 &lt;= nums[i] &lt;= 30</code></li>
	<li>The input is generated such that <code>answer[i]</code> is <strong>guaranteed</strong> to fit in a <strong>32-bit</strong> integer.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow up:</strong>&nbsp;Can you solve the problem in <code>O(1)</code>&nbsp;extra&nbsp;space complexity? (The output array <strong>does not</strong> count as extra space for space complexity analysis.)</p>


---

# 🛍️ Product-of-Array-Except-Self | Explained

## Approach 1: Progressive Left Accumulation with Right-Scanning (Brute Force)

### Intuition
Imagine you are standing in a line of people, and you want to find the product of the heights of everyone except yourself. 

Under this brute-force approach, you keep track of the product of everyone to your left as you walk down the line. For each person, you stop, turn around, and look at every single person to your right, multiplying their heights one by one to get a "right product." You then multiply your accumulated "left product" by this freshly calculated "right product" to get your answer. Once done, you update your "left product" by multiplying it by your own height before moving to the next person.

While intuitive, this requires you to scan the remainder of the line for every single person, leading to redundant work.

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start]) --> Init[Initialize left = 1, ans array]
    Init --> LoopI{i < nums.length?}
    LoopI -- Yes --> InitRight[Initialize right = 1]
    InitRight --> LoopJ{j > i?}
    LoopJ -- Yes --> MultRight[right *= nums[j]]
    MultRight --> DecJ[j--]
    DecJ --> LoopJ
    LoopJ -- No --> CalcAns[ans[i] = left * right]
    CalcAns --> MultLeft[left *= nums[i]]
    MultLeft --> IncI[i++]
    IncI --> LoopI
    LoopI -- No --> End([End])
```

### Approach
1. Initialize a variable `left` to `1` to accumulate the running prefix product.
2. Allocate an array `ans` of the same length as `nums`.
3. Loop through the array from left to right using index `i`:
   - Initialize a local variable `right` to `1`.
   - Run an inner loop with index `j` starting from the end of the array down to `i + 1`. Multiply `right` by `nums[j]` at each step to compute the suffix product.
   - Calculate `ans[i]` as the product of the current accumulated `left` prefix and the freshly calculated `right` suffix.
   - Update `left` by multiplying it by `nums[i]` to prepare for the next iteration.
4. Return `ans`.

### Detailed Code Analysis
This approach corresponds to the commented-out code in your submission:

- **Line 3-4:** `int left = 1;` initializes our running prefix product. `int[] ans = new int[nums.length];` instantiates the output array.
- **Line 5:** `for(int i=0; i<nums.length; i++)` starts the main outer loop to determine the value for each index `i`.
- **Line 6:** `int right = 1;` resets our suffix product accumulator for the current element.
- **Lines 7-9:** `for(int j=nums.length-1; j>i; j--) { right *= nums[j]; }` performs a nested scan from the end of the array down to the element right after `i`. This is the bottleneck of the algorithm, executing up to $O(N)$ operations per outer loop iteration.
- **Line 10:** `ans[i] = left * right;` combines the pre-calculated left product with the post-calculated right product.
- **Line 11:** `left *= nums[i];` updates the running prefix product to include the current element for subsequent indices.

### Code
```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int left = 1;
        int[] ans = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            int right = 1;
            for(int j = nums.length - 1; j > i; j--) {
                right *= nums[j];
            }
            ans[i] = left * right;
            left *= nums[i];
        }
        return ans;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N^2)$ where $N$ is the length of the input array `nums`. For each element `i`, we iterate through the remaining $N - 1 - i$ elements. Summing this arithmetic progression gives $\frac{N(N-1)}{2}$ operations, which is quadratic.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space if we do not count the output array `ans` (which is standard practice for this problem). If we count the output array, the space complexity is $\mathcal{O}(N)$.

---

## Approach 2: Precomputed Suffix Array with Running Prefix Multiplier (Active Code)

### Intuition
Instead of scanning the right side of the array repeatedly for every single element, we can precompute all right-side products in a single pass. 

Imagine walking from the end of the line back to the beginning, carrying a notebook. At each spot, you write down the product of everyone's height to your right. Once you reach the front, you have a lookup table (`arr_right`). 

Then, you walk forward. As you visit each person, you instantly know the product of everyone to their right by looking at your notebook (`arr_right[i]`). You multiply that by your running `left` product, record the answer, and update your `left` product. This eliminates the nested loop entirely.

### Algorithm Visualized

```mermaid
flowchart TD
    subgraph Pass 1: Build Suffix Array (Right to Left)
        Start1([Start]) --> InitRightArr[Create arr_right, right = 1]
        InitRightArr --> LoopR{i >= 0?}
        LoopR -- Yes --> AssignRight[arr_right[i] = right]
        AssignRight --> UpdateRight[right *= nums[i]]
        UpdateRight --> DecR[i--]
        DecR --> LoopR
    end
    LoopR -- No --> StartPass2
    subgraph Pass 2: Calculate Result (Left to Right)
        StartPass2[Create ans array, left = 1] --> LoopL{i < nums.length?}
        LoopL -- Yes --> AssignAns[ans[i] = left * arr_right[i]]
        AssignAns --> UpdateLeft[left *= nums[i]]
        UpdateLeft --> IncL[i++]
        IncL --> LoopL
        LoopL -- No --> End([End])
    end
```

### Approach
1. **Suffix Accumulation Pass (Right-to-Left):**
   - Create an auxiliary array `arr_right` of size $N$.
   - Iterate backward from $N-1$ down to $0$. At each index `i`, store the accumulated product of all elements to the right of `i` in `arr_right[i]`.
   - Update the running product `right` by multiplying it by `nums[i]`.
2. **Prefix Accumulation & Assembly Pass (Left-to-Right):**
   - Create the final output array `ans`.
   - Maintain a running `left` product starting at `1`.
   - Iterate forward from $0$ to $N-1$. For each index `i`, the value of `ans[i]` is the product of the running `left` prefix and the precalculated suffix in `arr_right[i]`.
   - Update `left` by multiplying it by `nums[i]`.
3. Return `ans`.

### Detailed Code Analysis
This corresponds to your active, un-commented execution block:

- **Lines 14-15:** `int [] arr_right = new int[nums.length]; int right = 1;` instantiates the lookup table for suffix products and sets the initial multiplier to 1.
- **Lines 16-19:** 
  ```java
  for(int i=nums.length-1;i>=0;i--){
      arr_right[i]= right;
      right *= nums[i];
  }
  ```
  This loop runs backward. For the last element, there is nothing to its right, so `arr_right[nums.length-1]` gets the value of `right` (which is `1`). Then, `right` is updated to include `nums[nums.length-1]` for the next iteration.
- **Lines 21-22:** `int [] ans = new int[nums.length]; int left = 1;` initializes the final answer array and the prefix tracking variable.
- **Lines 23-26:**
  ```java
  for(int i=0;i<nums.length;i++){
      ans[i] = left*arr_right[i];
      left *= nums[i];
  }
  ```
  This loop runs forward. At index `i`, we combine our running prefix product `left` with the precalculated suffix product `arr_right[i]`. We then update `left` with `nums[i]` so it is ready for index `i+1`.

### Code
```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] arr_right = new int[nums.length];
        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            arr_right[i] = right;
            right *= nums[i];
        }

        int[] ans = new int[nums.length];
        int left = 1;
        for (int i = 0; i < nums.length; i++) {
            ans[i] = left * arr_right[i];
            left *= nums[i];
        }
        return ans;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of elements in `nums`. We perform exactly two sequential linear passes over the input array, resulting in $\mathcal{O}(2N) = \mathcal{O}(N)$ operations.
- **Space Complexity:** $\mathcal{O}(N)$ auxiliary space. This is because we allocate an additional array `arr_right` of size $N$ to store our suffix products. 

---

## 🕵️‍♂️ Follow-up Questions

### 1. How can we optimize this solution to use $\mathcal{O}(1)$ auxiliary space?
We can eliminate the auxiliary `arr_right` array entirely by reusing the final `ans` array to store the suffix products first. 

Instead of writing to a separate array, we perform the right-to-left pass directly on `ans`. Then, during the left-to-right pass, we multiply the running `left` variable directly into `ans[i]` in-place:

```java
public int[] productExceptSelf(int[] nums) {
    int[] ans = new int[nums.length];
    
    // Pass 1: Store suffix products directly in the output array
    int right = 1;
    for (int i = nums.length - 1; i >= 0; i--) {
        ans[i] = right;
        right *= nums[i];
    }
    
    // Pass 2: Multiply prefix products directly into the output array
    int left = 1;
    for (int i = 0; i < nums.length; i++) {
        ans[i] *= left;
        left *= nums[i];
    }
    
    return ans;
}
```
This reduces the auxiliary space complexity to $\mathcal{O}(1)$ because we only use a few tracking variables (`left` and `right`).

### 2. Why can't we just compute the product of all elements and divide by `nums[i]`?
There are two reasons:
1. **Constraint Requirement:** The problem description explicitly states: *"You must write an algorithm that runs in $\mathcal{O}(n)$ time and without using the division operation."*
2. **The Zero Bug:** If the input array contains one or more zeros, division by zero occurs. For example, in `[1, 2, 0, 4]`, the product of all elements is `0`. Dividing `0` by `nums[2]` (which is `0`) is undefined, and dividing `0` by `nums[0]` yields `0` instead of the correct answer `8`. Handling these edge cases with division logic adds unnecessary complexity compared to the clean prefix/suffix approach.