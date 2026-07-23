<h2><a href="https://leetcode.com/problems/binary-search">704. Binary Search</a></h2>

<p>Given an array of integers <code>nums</code> which is sorted in ascending order, and an integer <code>target</code>, write a function to search <code>target</code> in <code>nums</code>. If <code>target</code> exists, then return its index. Otherwise, return <code>-1</code>.</p>

<p>You must write an algorithm with <code>O(log n)</code> runtime complexity.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [-1,0,3,5,9,12], target = 9
<strong>Output:</strong> 4
<strong>Explanation:</strong> 9 exists in nums and its index is 4
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [-1,0,3,5,9,12], target = 2
<strong>Output:</strong> -1
<strong>Explanation:</strong> 2 does not exist in nums so return -1
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>4</sup></code></li>
	<li><code>-10<sup>4</sup> &lt; nums[i], target &lt; 10<sup>4</sup></code></li>
	<li>All the integers in <code>nums</code> are <strong>unique</strong>.</li>
	<li><code>nums</code> is sorted in ascending order.</li>
</ul>


---

# 🛍️ Binary-Search | Explained

## Approach 1: Iterative Binary Search

### Intuition
Imagine searching for a specific word in a physical dictionary. Instead of starting from page one and reading every word sequentially, you open the book to the exact middle. If the word you are looking for comes alphabetically before the words on that page, you completely disregard the right half of the book and repeat the process on the left half. If it comes after, you eliminate the left half. 

This divide-and-conquer strategy works because the dictionary is sorted. Binary search leverages this exact same property in sorted arrays: by evaluating the middle element, we can discard half of the remaining search space in a single comparison.

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start Search]) --> InitPointers[Initialize low = 0, high = n - 1]
    InitPointers --> LoopCheck{low <= high?}
    
    LoopCheck -- No --> TargetNotFound[Return -1]
    LoopCheck -- Yes --> CalcMid[Calculate mid = low + high / 2]
    
    CalcMid --> CheckEqual{nums[mid] == target?}
    
    CheckEqual -- Yes --> TargetFound[Return mid]
    CheckEqual -- No --> CheckGreater{nums[mid] > target?}
    
    CheckGreater -- Yes --> ShiftHigh[high = mid - 1]
    CheckGreater -- No --> ShiftLow[low = mid + 1]
    
    ShiftHigh --> LoopCheck
    ShiftLow --> LoopCheck
```

### Approach
1. **Initialize Search Bounds**: Define two pointers, `low` pointing to the start of the array (`0`) and `high` pointing to the last index (`n - 1`).
2. **Iterative Halving Loop**: Run a loop while `low <= high`. The condition `<= ` ensures that single-element search spaces are evaluated.
3. **Midpoint Computation**: Calculate the middle index `mid`.
4. **Compare & Divide**:
   - If `nums[mid] == target`: You found the target index; return `mid`.
   - If `nums[mid] > target`: The target must lie in the left sub-array (if present). Update `high = mid - 1` to shrink the upper boundary.
   - If `nums[mid] < target`: The target must lie in the right sub-array (if present). Update `low = mid + 1` to shrink the lower boundary.
5. **Termination**: If `low` exceeds `high`, the target does not exist in the array. Return `-1`.

### Detailed Code Analysis

Let's dissect the provided implementation step-by-step:

- **Lines 2-6**: 
  ```java
  int n = nums.length;
  int low = 0;
  int high = n-1;
  ```
  Here, `n` stores the total size of the array. The algorithm sets `low` to index `0` and `high` to index `n - 1`, defining the inclusive active search window `[low, high]`.

- **Line 8**:
  ```java
  while(low<=high){
  ```
  The loop condition uses `<=` instead of `<`. This is critical because when `low == high`, the search window consists of exactly one element. If we used `<`, the algorithm would prematurely terminate without checking that final element.

- **Line 9**:
  ```java
  int mid = (low+high)/2;
  ```
  This computes the middle index. 
  *(Note for Production/Senior level code: In Java, `(low + high)` can potentially cause an 32-bit integer overflow if `low + high > Integer.MAX_VALUE`. The standard fix is `int mid = low + (high - low) / 2;` or using the bitwise unsigned right shift `int mid = (low + high) >>> 1;`)*.

- **Lines 11-13**:
  ```java
  if(nums[mid]== target) return mid;
  else if(nums[mid]>target) high = mid-1;
  else low = mid + 1;
  ```
  - **Line 11**: Direct equality check. If matched, the target is located and its index is returned immediately.
  - **Line 12**: If `nums[mid]` is strictly greater than `target`, every element from `mid` to `high` is also strictly greater than `target` (since `nums` is sorted). We safely set `high = mid - 1` to throw away the right half.
  - **Line 13**: If `nums[mid]` is strictly less than `target`, every element from `low` to `mid` is strictly less than `target`. We set `low = mid + 1` to throw away the left half.

- **Line 16**:
  ```java
  return -1;
  ```
  If the loop breaks (`low > high`), it means the search space has been completely exhausted without finding `target`. Returns `-1` per the problem specification.

### Code
```java
class Solution {
    public int search(int[] nums, int target) {
        int n = nums.length;

        int low = 0;
        int high = n-1;

        while(low<=high){
            int mid = (low+high)/2;

            if(nums[mid]== target) return mid;
            else if(nums[mid]>target) high = mid-1;
            else low = mid + 1;
        }

        return -1;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(\log N)$
  In every iteration of the `while` loop, the search window size is halved ($N, N/2, N/4, \dots, 1$). Thus, the maximum number of iterations required to reduce the search space to a single element is $\log_2(N)$.

- **Space Complexity:** $\mathcal{O}(1)$
  The algorithm runs iteratively using a constant number of primitive variables (`n`, `low`, `high`, `mid`). No extra space is allocated on the heap or call stack.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

### 1. How would you prevent Integer Overflow when calculating `mid`?
**Question**: In your code, you write `int mid = (low + high) / 2;`. Why might this cause a bug in large arrays, and how do you fix it?

**Answer**: If `low` and `high` are very large (e.g., near `2^31 - 1`), adding them together (`low + high`) will overflow the 32-bit signed integer limits in Java, resulting in a negative number. This leads to an `ArrayIndexOutOfBoundsException`. 
To fix this, we write:
```java
int mid = low + (high - low) / 2;
```
Alternatively, in Java, you can use the bitwise unsigned right shift operator:
```java
int mid = (low + high) >>> 1;
```

---

### 2. How would you convert this to a Recursive Solution, and what is the trade-off?
**Question**: Can you implement Binary Search recursively, and how does it compare to the iterative solution?

**Answer**: Yes, by passing `low` and `high` as parameters in a helper function. However, the iterative approach is superior in practice because:
1. **Space Complexity**: The iterative approach takes $\mathcal{O}(1)$ auxiliary space. The recursive approach takes $\mathcal{O}(\log N)$ space on the call stack due to recursion frames.
2. **Overhead**: Recursion incurs stack frame creation/destruction overhead and risks a `StackOverflowError` for extremely deep call stacks (though $\log N$ call depth is generally shallow).