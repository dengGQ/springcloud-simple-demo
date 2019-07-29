package com.fotic.webproject.leetcode;

import javassist.expr.NewArray;
import oracle.net.aso.g;

/**
 * @ClassName: Solution
 * @Description: 找到指定数组中的两个数之和等于指定参数的位置
 * @author Administrator
 * @date 2019年7月22日
 */
public class Solution {
	int index;
    int[] map = new int[2048];
    public int[] twoSum(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++){
            index = nums[i]&2047;
            if(map[index] != 0){
                return new int[]{map[index]-1, i};
            }else{
                map[(target-index)&2047] = i+1;
            }
        }
        return null;
    }
    
    /**
     * @Description: 构建一个较大的临时数组map，
     * 				然后遍历数组中的元素，以当前遍历的元素的value为索引提取map中的value,
     * 				如果value==0 即找到与当前遍历元素之和为目标值的元素，
     * 				!=0 则将目标值target与value的差作为临时map的index, 值为当前遍历元素的索引+1，之所以加一是因为int的默认值是0, 存入map
     * @param @param nums
     * @param @param target
     * @param @return    参数
     * @return int[]    返回类型
     * @throws
     */
    public int[] twoSum2(int nums[], int target) {
    	int[] map = new int[1*1024*1024*1024/4];
    	
    	for (int i = 0; i < nums.length; i++) {
			int value = nums[i];
			if(map[value] != 0) {
				return new int[] {map[value]-1, i};
			}else if((target - value) > 0){
				map[target - value] = i+1;
			}
		}
    	return null;
    }
    
    public static void main(String[] args) {
//		Solution sol = new Solution();
//		int[] twoSum = sol.twoSum2(new int[] {6, 6, 4, 2, 9, 3}, 1);
//    	System.out.println(twoSum[0]);
//    	System.out.println(twoSum[1]);
//		System.out.println(-3&2047);
    
    	Integer a = 10;
    	System.out.println(Integer.toBinaryString(a));
    	System.out.println(Integer.toBinaryString(a >> 1));
    
    	StringBuilder sb = new StringBuilder("abcdef");
    	System.out.println(sb.reverse());
    }
}
