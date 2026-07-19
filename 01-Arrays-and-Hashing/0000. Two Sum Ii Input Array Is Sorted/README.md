<h2><a href="https://leetcode.com/problems/two-sum-ii-input-array-is-sorted">0000. Two Sum Ii Input Array Is Sorted</a></h2>

<p>Given a <strong>1-indexed</strong> array of integers <code>numbers</code> that is already <strong><em>sorted in non-decreasing order</em></strong>, find two numbers such that they add up to a specific <code>target</code> number. Let these two numbers be <code>numbers[index<sub>1</sub>]</code> and <code>numbers[index<sub>2</sub>]</code> where <code>1 &lt;= index<sub>1</sub> &lt; index<sub>2</sub> &lt;= numbers.length</code>.</p>

<p>Return<em> the indices of the two numbers&nbsp;</em><code>index<sub>1</sub></code><em> and </em><code>index<sub>2</sub></code><em>, <strong>each incremented by one,</strong> as an integer array </em><code>[index<sub>1</sub>, index<sub>2</sub>]</code><em> of length 2.</em></p>

<p>The tests are generated such that there is <strong>exactly one solution</strong>. You <strong>may not</strong> use the same element twice.</p>

<p>Your solution must use only constant extra space.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> numbers = [<u>2</u>,<u>7</u>,11,15], target = 9
<strong>Output:</strong> [1,2]
<strong>Explanation:</strong> The sum of 2 and 7 is 9. Therefore, index<sub>1</sub> = 1, index<sub>2</sub> = 2. We return [1, 2].
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> numbers = [<u>2</u>,3,<u>4</u>], target = 6
<strong>Output:</strong> [1,3]
<strong>Explanation:</strong> The sum of 2 and 4 is 6. Therefore index<sub>1</sub> = 1, index<sub>2</sub> = 3. We return [1, 3].
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> numbers = [<u>-1</u>,<u>0</u>], target = -1
<strong>Output:</strong> [1,2]
<strong>Explanation:</strong> The sum of -1 and 0 is -1. Therefore index<sub>1</sub> = 1, index<sub>2</sub> = 2. We return [1, 2].
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= numbers.length &lt;= 3 * 10<sup>4</sup></code></li>
	<li><code>-1000 &lt;= numbers[i] &lt;= 1000</code></li>
	<li><code>numbers</code> is sorted in <strong>non-decreasing order</strong>.</li>
	<li><code>-1000 &lt;= target &lt;= 1000</code></li>
	<li>The tests are generated such that there is <strong>exactly one solution</strong>.</li>
</ul>


---

# 🛍️ Two-Sum-Ii-Input-Array-Is-Sorted | Explained

## Approach 1: Two-Pointer Technique with Direct Return (Active Code)

### Intuition
The core intuition behind this approach lies in leveraging the pre-sorted nature of the input array. 

Imagine you are trying to find two items in a sorted catalog that add up to a target price. If you pick the cheapest item (at the start) and the most expensive item (at the end):
- If their combined price is **too high**, the only logical move is to try a cheaper "expensive" item. You decrement your high pointer.
- If their combined price is **too low**, your only logical move is to try a more expensive "cheap" item. You increment your low pointer.

Because the array is sorted, this linear squeeze guarantees that we will find the target pair in a single pass without redundant comparisons.

### Algorithm Visualized
```mermaid
graph TD
    A[Start: left = 0, right = n-1] --> B{left < right}
    B -- No --> C[Return {-1, -1}]
    B -- Yes --> D[Calculate sum = arr[left] + arr[right]]
    D --> E{sum == target}
    E -- Yes --> F[Return {left + 1, right + 1}]
    E -- No --> G{sum < target}
    G -- Yes --> H[Increment left pointer: left++]
    G -- No --> I[Decrement right pointer: right--]
    H --> B
    I --> B
```

### Approach
1. **Initialize Pointers:** Place a `left` pointer at index `0` and a `right` pointer at index `n - 1`.
2. **Evaluate Range:** While `left < right`, calculate the sum of the elements at these two pointers.
3. **Check Conditions:**
   - If `sum == target`, return a new array containing the 1-based indices: `new int[]{left + 1, right + 1}`.
   - If `sum < target`, we need a larger sum. Since the array is sorted, incrementing `left` moves us to a larger value.
   - If `sum > target`, we need a smaller sum. Decrementing `right` moves us to a smaller value.
4. **Fallback:** If the loop terminates without finding a match, return `new int[]{-1, -1}`.

### Detailed Code Analysis
- **Lines 3-5:** 
  ```java
  int n = arr.length;
  int left = 0;
  int right = n-1;
  ```
  We cache the array length to avoid multiple property lookups (though JVM JIT compiler optimizes this, it is good practice). We initialize the two pointers at the boundaries of the array.
- **Line 9:**
  ```java
  while(left<right){
  ```
  The loop condition ensures that the two pointers never cross or point to the same element, as the problem constraints require two *distinct* elements.
- **Line 10:**
  ```java
  int sum = arr[left] + arr[right];
  ```
  We sum the values at both pointers. Note: Under extreme test cases, adding two large integers could cause an integer overflow. However, for LeetCode constraints on this specific problem, a standard `int` is sufficient.
- **Lines 12-18:**
  ```java
  if(sum == target){
      return new int[]{left+1 , right+1};
  }
  ```
  This is the active return statement. Once the target is hit, we immediately instantiate and return the 1-based indexed array. This is highly efficient as it escapes the method context immediately, avoiding unnecessary breaks or local variable writes.
- **Lines 19-21:**
  ```java
  else if(sum<target) left++;
  else right--;
  ```
  The branching logic to adjust the window boundaries based on how the current `sum` compares to the `target`.
- **Line 24:**
  ```java
  return new int[]{-1, -1};
  ```
  Defensive coding. If no solution is found, we return a sentinel value of `{-1, -1}`.

### Code
```java
class Solution {
    public int[] twoSum(int[] arr, int target) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$. In the worst-case scenario, the pointers will traverse the array from both ends and meet in the middle. Each element is visited at most once.
- **Space Complexity:** $\mathcal{O}(1)$. The algorithm runs in strict constant auxiliary space because we only allocate a few pointer variables (`left`, `right`, `sum`).

---

## Approach 2: Two-Pointer Technique with Local Array Mutation (Commented-out Variant)

### Intuition
This approach utilizes the exact same mathematical properties and pointer mechanics as Approach 1. However, it manages execution state differently. Instead of returning immediately upon finding the target, it writes the result to a pre-allocated array, breaks out of the loop using a control flow keyword (`break`), and returns the allocated array at the end of the method execution.

### Approach
1. **Pre-allocate Memory:** Instantiate a result array `arr1` of size 2 at the start of the method.
2. **Execute Two-Pointer Loop:** Move the pointers inward based on the relationship between `sum` and `target`.
3. **Capture State and Break:** Once `sum == target`, populate `arr1[0]` and `arr1[1]` with the 1-based indices, then call `break` to exit the loop.
4. **Unified Exit Point:** Return the pre-allocated `arr1` at the end of the method block.

### Detailed Code Analysis
- **Line 7:**
  ```java
  int [] arr1 = new int [2];
  ```
  This statement pre-allocates an array on the heap. While seemingly harmless, if the target is never found (or if the method needs to fail fast), this memory allocation occurs needlessly.
- **Lines 13-15:**
  ```java
  arr1[0]= left+1;
  arr1[1] = right+1;
  break;
  ```
  Instead of an immediate return, this assigns values to the array indices. The `break` keyword stops the execution of the `while` loop, shifting control to the line immediately following the loop block.
- **Line 23:**
  ```java
  return arr1;
  ```
  This serves as a single exit point for the happy path where the target is found.

*Senior Engineer Review Note:* This pattern is often a byproduct of older procedural programming styles or strict "Single Entry, Single Exit" (SESE) guidelines. Modern Java style guides prefer the direct return pattern shown in **Approach 1** because it avoids unnecessary state mutation, eliminates the need for temporary variables, and makes the code cleaner and easier to read.

### Code
```java
class Solution {
    public int[] twoSum(int[] arr, int target) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;

        int[] arr1 = new int[2];

        while (left < right) {
            int sum = arr[left] + arr[right];

            if (sum == target) {
                arr1[0] = left + 1;
                arr1[1] = right + 1;
                break;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return arr1;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$. Pointer traversal behaves identically to Approach 1, taking linear time in the worst case.
- **Space Complexity:** $\mathcal{O}(1)$. Although we allocate `arr1` at the beginning, its size is fixed at `2` regardless of the input size $N$, maintaining constant space complexity.

---

## 🕵️‍♂️ Follow-up Questions

### 1. What happens if the sum of `arr[left]` and `arr[right]` overflows the standard integer range? How do you fix it?
**Answer:**
If the input values are close to `Integer.MAX_VALUE` or `Integer.MIN_VALUE`, executing `arr[left] + arr[right]` can cause integer overflow, leading to undefined or wrapping behavior. To resolve this, you should cast the sum calculation to a 64-bit integer (`long`):
```java
long sum = (long) arr[left] + arr[right];
```
This ensures the comparison with the `target` remains mathematically accurate.

### 2. Can we optimize this to $\mathcal{O}(\log N)$ time complexity?
**Answer:**
No, a global $\mathcal{O}(\log N)$ solution is impossible because, in the worst case, we must look at up to $N$ elements to find the correct pair. However, we *can* perform a hybrid optimization: if `arr[left] + arr[right] < target`, instead of doing `left++`, we can use **Binary Search** to find the smallest index `i` such that `arr[i] >= target - arr[right]`, and jump our `left` pointer directly to `i`. This optimization is highly effective when the target indices are far apart, but the worst-case complexity remains $\mathcal{O}(N)$.