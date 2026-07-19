<h2><a href="https://leetcode.com/problems/max-consecutive-ones">485. Max Consecutive Ones</a></h2>

<p>Given a binary array <code>nums</code>, return <em>the maximum number of consecutive </em><code>1</code><em>'s in the array</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,1,0,1,1,1]
<strong>Output:</strong> 3
<strong>Explanation:</strong> The first two digits or the last three digits are consecutive 1s. The maximum number of consecutive 1s is 3.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [1,0,1,1,0,1]
<strong>Output:</strong> 2
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>nums[i]</code> is either <code>0</code> or <code>1</code>.</li>
</ul>


---

# 🛍️ Max-Consecutive-Ones | Explained

## Approach 1: Single-Pass Iterative Counting
### Intuition
Imagine you are watching a flip-book or a conveyor belt of items, counting consecutive blue boxes (represented by `1`s). Every time you see a blue box, you increment your current streak count. If you encounter any other color (a `0`), your current streak is broken, and you must reset your counter to zero. Throughout this process, you keep a record of the longest streak you have seen so far in a separate notebook (`max_c`). This approach works because it processes the array in a single linear pass, tracking local state transitions (current streak vs. global maximum streak) dynamically.

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start]) --> Init[Initialize max_c = 0, count = 0]
    Init --> LoopStart{For i = 0 to n-1}
    LoopStart -- Yes --> Check{nums[i] == 1?}
    Check -- Yes --> Inc[count++]
    Check -- No --> Reset[count = 0]
    Inc --> UpdateMax[max_c = Math.max(max_c, count)]
    Reset --> UpdateMax
    UpdateMax --> LoopStart
    LoopStart -- No --> Return[Return max_c]
    Return --> End([End])
```

### Approach
The algorithm utilizes a single-pass greedy strategy to find the maximum number of consecutive ones:
1. **Initialize Counters:** Set up two integer variables: `max_c` to store the maximum streak found so far, and `count` to track the active running streak of `1`s.
2. **Iterate Through Array:** Traverse the array from left to right.
3. **Evaluate Elements:**
   - If the current element is `1`, increment the running `count` by 1.
   - If the current element is `0`, the streak is broken; reset the running `count` to `0`.
4. **Update Global Maximum:** At each step, compare the active `count` with `max_c` and store the larger value back into `max_c`.
5. **Return Result:** After completing the traversal, return the value of `max_c`.

### Detailed Code Analysis
Let's analyze the behavior of the code line-by-line:

- **Line 3 (`int n = nums.length;`):** Extracts the length of the input array and stores it in variable `n` to avoid repetitive length queries during loop condition evaluations.
- **Line 4-5 (`int max_c = 0; int count = 0;`):** 
  - `max_c` serves as the global accumulator holding the maximum streak length. It starts at `0` to handle edge cases like an empty array or an array containing only `0`s.
  - `count` is the local accumulator tracking the current continuous segment of `1`s.
- **Line 6 (`for(int i=0 ; i<n ; i++)`):** Standard `for` loop that iterates sequentially through index `0` to `n-1` to guarantee $O(N)$ time complexity.
- **Line 8-9 (`if (nums[i] == 1) count ++; else count =0;`):**
  - The conditional block evaluates whether the current element is `1`.
  - If true, `count++` extends the current window of consecutive ones.
  - If false (the element is `0`), the consecutive streak is broken, and `count` is reset to `0` immediately.
- **Line 11 (`max_c = Math.max(max_c, count);`):** Updates the global maximum at every iteration. Calling `Math.max` inside the loop ensures that even if the array ends on a streak of `1`s, the maximum value is correctly captured without needing post-loop cleanup logic.
- **Line 13 (`return max_c;`):** Returns the final computed maximum consecutive sequence of `1`s.

### Code
```java
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int n = nums.length;
        int max_c = 0;
        int count = 0;
        for(int i=0 ; i<n ; i++){
            
            if (nums[i] == 1) count ++;
            else count = 0;

            max_c = Math.max(max_c, count);
        }
        return max_c;
    }
}
```

### Complexity
- **Time:** $O(N)$, where $N$ is the length of the array `nums`. The algorithm visits each element in the array exactly once, performing constant-time operations ($O(1)$) inside the loop.
- **Space:** $O(1)$ auxiliary space. The memory footprint remains constant regardless of the input size, as only a few primitive integer variables (`n`, `max_c`, `count`, `i`) are allocated on the stack.

## 🕵️‍♂️ Follow-up Questions (Optional)

### 1. How can we optimize the number of writes/updates to `max_c`?
In the current implementation, `Math.max(max_c, count)` runs on every single iteration of the loop. This can be optimized by updating `max_c` only when a streak is broken (i.e., when encountering a `0`) or when we reach the end of the array.

**Revised Logic:**
```java
if (nums[i] == 1) {
    count++;
} else {
    max_c = Math.max(max_c, count);
    count = 0;
}
// After the loop, perform one final check to capture a streak ending at the last index
max_c = Math.max(max_c, count);
```
This reduces the number of operations, particularly in arrays with long sequences of `1`s.

### 2. What if the input array is extremely large and cannot fit into memory (Streaming/Iterator-based Input)?
If the input is streamed rather than loaded entirely as an array, the design holds up exceptionally well. Because the algorithm only requires knowing the current element, the running count, and the maximum count, it can process infinite streams of data with $O(1)$ space complexity. We would simply replace the array indexing with a stream reader or iterator:
```java
while (stream.hasNext()) {
    int val = stream.next();
    if (val == 1) count++;
    else {
        max_c = Math.max(max_c, count);
        count = 0;
    }
}
```