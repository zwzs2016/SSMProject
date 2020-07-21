package com.company;

import java.util.*;

/***
 * 所有实现均基于JDK，不可使用第三方中间件
* 1.实现下方的抽奖方法(luckdraw)，提供5000次抽奖机会，奖品有一等奖3个，二等奖 100个，三等奖50个，各个奖品需在5000次抽奖中尽量平均分布，但不可有规律被抽中
* 2.实现下方的数字查找方法，查找最大数和最小数，并得出它们的乘积（不可使用集合工具类）
* 3.实现下方的查找方法（findAndReturn）供他人调用，传入参数为不可预知的List，分别查找List中2的倍数，5的倍数出现的次数，调用checkEqual方法判断如果返回true就不计入次数，最后返回结果集，此方法有性能要求。
 */
public class Demo {

	
	/***
	 * 检查是否等于100
	 *@param num 比较值
	 *@return 相等返回true 否则false
	 */
	public static boolean checkEqual(Integer num) {
			return num==100;
	}
	
	public void luckDraw() {
		// do somhing
		int num=5000;
		HashSet<HashSet<Integer>> prize=new HashSet<HashSet<Integer>>();
		HashSet<Integer> one_prize=new HashSet<>();
		HashSet<Integer> two_prize=new HashSet<>();
		HashSet<Integer> three_prize=new HashSet<>();
		Random random=new Random();
		int c_num;//chouzhong num
		int t_num=0;
		int prize_size;
		while (num>0){
			//1
			one_prize.add(random.nextInt(1666));
			one_prize.add(random.nextInt(1666)+1667);
			one_prize.add(random.nextInt(1666)+3333);
			//2
			while (two_prize.size()<100){
				prize_size=two_prize.size();
				two_prize.add(random.nextInt(50)+t_num);
				System.out.println(String.valueOf(two_prize.size()));
				if (prize_size<two_prize.size()){
					t_num+=50;
				}
//				t_num+=50;
			}
			for (int two_num:two_prize) {
				System.out.println(String.valueOf(two_num));
			}
			t_num=0;
			//3
			while (three_prize.size()<50){
				prize_size=three_prize.size();
				three_prize.add(random.nextInt(100)+t_num);
				System.out.println(String.valueOf(three_prize.size()));
				if (prize_size<three_prize.size()){
					t_num+=100;
				}
			}
			for (int snum:three_prize) {
				System.out.println(String.valueOf(snum));
			}
			t_num=0;
			prize.add(one_prize);
			prize.add(two_prize);
			prize.add(three_prize);
			System.out.printf("c_num:");
			c_num=new Scanner(System.in).nextInt();
			if (prize.contains(c_num)){
				if (one_prize.contains(c_num)){
					System.out.println("zhong1");
				}
				if (two_prize.contains(c_num)){
					System.out.println("zhong2");
				}
				if (three_prize.contains(c_num)){
					System.out.println("zhong3");
				}
			}else {
				System.out.println("no thinks");
			}
			num--;
		}
		}
	
	public void findNumber() {
		String[] array={"1","5","33","7","8","27","5","90","2","6","25"};
		// do something
		int[] intarray = new int[array.length];

		for(int i=0;i<array.length;i++){

			intarray[i] = Integer.parseInt(array[i]);

		}
		//maopao
		for( int i = 0;i < array.length - 1;i++ ){
			for( int j = 0;j < array.length - i - 1;j++ ){
				if( intarray[j] > intarray[j+1] ){
					int temp = intarray[j];
					intarray[j] = intarray[j+1];
					intarray[j+1] = temp;
				}
			}
		}
		System.out.println("max_num:"+intarray[intarray.length-1]);
		System.out.println("min_num:"+intarray[0]);
		System.out.println("product:"+(intarray[intarray.length-1]*intarray[0]));
	}

	public Map<Integer,Integer> findAndReturn(List<Integer> list){
		Map<Integer,Integer> map=new HashMap<>();
		int key=0;
		int value;
		for (int num:list){
			if (num%2==0 || num%5==0){
				if (checkEqual(num)==false){
					value=num;
					map.put(key,value);
					key++;
				}
			}
		}
		return map;
	}
	public static void main(String[] args) {
		new Demo().luckDraw();
		new Demo().findNumber();
		List<Integer> list=new ArrayList<>();
		list.add(2);
		list.add(4);
		list.add(5);
		list.add(100);
		Map<Integer,Integer> map=new Demo().findAndReturn(list);
		for (Map.Entry<Integer,Integer> entry:map.entrySet()){
			Integer mapKey=entry.getKey();
			Integer mapValue=entry.getValue();
			System.out.println(mapKey+":"+mapValue);
		}
	}

}
