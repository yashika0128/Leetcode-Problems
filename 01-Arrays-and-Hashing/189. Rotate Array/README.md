<h2><a href="https://leetcode.com/problems/rotate-array">189. Rotate Array</a></h2>

<p>Given an integer array <code>nums</code>, rotate the array to the right by <code>k</code> steps, where <code>k</code> is non-negative.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,2,3,4,5,6,7], k = 3
<strong>Output:</strong> [5,6,7,1,2,3,4]
<strong>Explanation:</strong>
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [-1,-100,3,99], k = 2
<strong>Output:</strong> [3,99,-1,-100]
<strong>Explanation:</strong> 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-2<sup>31</sup> &lt;= nums[i] &lt;= 2<sup>31</sup> - 1</code></li>
	<li><code>0 &lt;= k &lt;= 10<sup>5</sup></code></li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow up:</strong></p>

<ul>
	<li>Try to come up with as many solutions as you can. There are at least <strong>three</strong> different ways to solve this problem.</li>
	<li>Could you do it in-place with <code>O(1)</code> extra space?</li>
</ul>


---

# 🛍️ Rotate-Array | Explained

## Approach 1: Reversing Subarrays (The Triple-Reverse Trick)
### Intuition
Think of the array as a physical banner. If you want to shift the end of the banner to the front, you can achieve this by reversing sections of it.

For example, imagine we want to rotate the word `"TRAIN"` by $2$ steps to the right. The expected result is `"INTRA"`.
1. If we reverse the entire word `"TRAIN"`, we get `"NIART"`.
2. Now, split this reversed string into two parts at our rotation boundary $k = 2$: the first $2$ characters (`"NI"`) and the remaining $3$ characters (`"ART"`).
3. Reverse each part individually in place:
   - Reverse `"NI"` $\rightarrow$ `"IN"`
   - Reverse `"ART"` $\rightarrow$ `"TRA"`
4. Combined, they form `"INTRA"`, which is exactly the rotated result! By reversing the entire array and then reversing the two partitioned segments, the elements end up in their correct positions with zero extra memory overhead.

### Algorithm Visualized
```mermaid
graph TD
    Start([Original Array: 1, 2, 3, 4, 5, 6, 7 | k = 3]) --> Step1[Step 1: Reverse Entire Array]
    Step1 --> Vis1["[7, 6, 5, 4, 3, 2, 1]"]
    Vis1 --> Step2[Step 2: Reverse First k elements index 0 to k-1]
    Step2 --> Vis2["[5, 6, 7,  4, 3, 2, 1]"]
    Vis2 --> Step3[Step 3: Reverse Remaining n-k elements index k to n-1]
    Step3 --> Vis3["[5, 6, 7,  1, 2, 3, 4]"]
    Vis3 --> End([Final Rotated Array])
```

### Approach
1. **Normalize $k$**: Rotating an array of size $n$ by $n$ times results in the original array. Therefore, we update $k = k \pmod n$ to avoid redundant complete rotations and prevent out-of-bounds errors when $k \ge n$.
2. **Reverse the entire array**: This shifts the elements destined for the front to the front half, and elements destined for the back to the back half, though both segments will temporarily be backward.
3. **Reverse the first $k$ elements**: This restores the correct original relative order of the elements that were shifted to the front.
4. **Reverse the remaining $n - k$ elements**: This restores the correct original relative order of the elements that were shifted to the back.

### Detailed Code Analysis
- **Lines 4-5**: 
  ```java
  int n= nums.length;
  k= k%n;
  ```
  We determine the length of the array $n$, and then resolve $k$ using modulo arithmetic to ensure $k$ lies strictly in the range $[0, n-1]$.
- **Line 6**: 
  ```java
  reverse(nums,0,n-1);
  ```
  Invokes the helper method to reverse the entire array from index `0` to index `n-1`.
- **Line 7**: 
  ```java
  reverse(nums,0,k-1);
  ```
  Reverses the newly placed prefix portion containing the first $k$ elements (indices `0` to `k-1`).
- **Line 8**: 
  ```java
  reverse(nums,k,n-1);
  ```
  Reverses the remaining suffix portion containing the rest of the elements (indices `k` to `n-1`).
- **Lines 11-19**: 
  ```java
  public void reverse(int [] arr, int i, int j){
      while(i<j){
          int temp= arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
          i++;
          j--;
      }
  }
  ```
  A utility function implementing an in-place two-pointer swap. Pointer `i` starts at the beginning of the target range, and pointer `j` starts at the end. We swap their elements, increment `i`, and decrement `j` until the pointers meet or cross in the middle.

### Code
```java
class Solution {
    public void rotate(int[] nums, int k) {
        
        int n= nums.length;
        k= k%n;
        reverse(nums,0,n-1);
        reverse(nums,0,k-1);
        reverse(nums,k,n-1);

    }
    public void reverse(int [] arr, int i, int j){
        while(i<j){
            int temp= arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}
```

### Complexity
- **Time:** $\mathcal{O}(n)$  
  Reversing the entire array takes $\mathcal{O}(n)$ swaps. Reversing the two subparts takes $\mathcal{O}(k)$ and $\mathcal{O}(n - k)$ swaps respectively. The total operations sum up to $\mathcal{O}(n) + \mathcal{O}(k) + \mathcal{O}(n-k) = \mathcal{O}(2n)$, which simplifies to linear complexity $\mathcal{O}(n)$.
- **Space:** $\mathcal{O}(1)$  
  The algorithm modifies the input array in place without allocating any auxiliary data structures, using only a constant amount of extra space for the pointer variables and temp variable during swapping.

## 🕵️‍♂️ Follow-up Questions
1. **How would you adapt this solution if $k$ could be negative (representing a left rotation instead of a right rotation)?**
   - A left rotation by $k$ elements is equivalent to a right rotation by $n - |k|$ elements. To handle negative $k$, we can normalize the offset using `k = (k % n + n) % n` at the start of the function, and then run the exact same reverse steps.
2. **Is there another way to achieve $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ space complexity without the triple-reverse trick?**
   - Yes, using **Cyclic Replacements**. We can move each element directly to its target index `(i + k) % n`. To prevent getting stuck in cycles when $n$ and $k$ are not coprime, we keep a count of elements shifted and step to the next starting position whenever a cycle closes. While also $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ space, the cyclic approach has more complex bookkeeping and is generally harder to write bug-free under interview pressure.