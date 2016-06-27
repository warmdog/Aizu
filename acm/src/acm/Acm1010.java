package acm;

import java.util.Scanner;

public class Acm1010 {
	public static char[][] maze = new char[7][7];
	public static boolean[][] visited = new boolean[7][7];
	public static int N, M, T;
	public static int start_i, start_j, end_i, end_j;
	
	/*
	 * 函数参数：1、当前搜索到的位置所在行号；2、当前搜索到的位置所在列号；3、当前的时刻
	 * 函数返回值：能否准时到达门口
	 * 用到的全局变量：maze,N,M,T,end_i, end_j;
	 * 函数结构：先列出出口条件，再进行自身递归调用
	 * */
	public static boolean DFS(int cur_pos_i, int cur_pos_j, int cur_time) {
		//判断是否越界
		if(cur_pos_i < 0 || cur_pos_i >= N || cur_pos_j < 0 || cur_pos_j >= M) {
			return false;
		}
		//判断是否超时
		if(cur_time > T) {
			return false;
		}
		//当前位置是否是墙
		if(maze[cur_pos_i][cur_pos_j] == 'X') {
			return false;
		}
		//当前位置是否已经走过
		if(visited[cur_pos_i][cur_pos_j]) {
			return false;
		}
		//奇偶性剪枝
		if((cur_pos_i + cur_pos_j + end_i + end_j + (T - cur_time)) % 2 == 1) {
			return false;
		}
		//如果已经到达门口
		if(maze[cur_pos_i][cur_pos_j] == 'D') {
			if(cur_time == T) {
				return true;
			}
			else {
				return false;
			}
		}
		//将当前位置标记为已走过
		visited[cur_pos_i][cur_pos_j] = true;
		//递归调用自身
		boolean ok = DFS(cur_pos_i + 1, cur_pos_j, cur_time + 1) || DFS(cur_pos_i - 1, cur_pos_j, cur_time + 1)
				|| DFS(cur_pos_i, cur_pos_j + 1, cur_time + 1) || DFS(cur_pos_i, cur_pos_j - 1, cur_time + 1);
		//将当前位置标记回未走过
		visited[cur_pos_i][cur_pos_j] = false;
		return ok;
	}
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		while(true) {
			N = cin.nextInt();
			M = cin.nextInt();
			T = cin.nextInt();
			
			if(N == 0 && M == 0 && T == 0) {
				break;
			}
			int blocks = 0;
			for(int i = 0; i < N; i ++) {
				//String str = cin.nextLine();
				String str = cin.next();
				for(int j = 0; j < M; j ++) {
					maze[i][j] = str.charAt(j);
					if(maze[i][j] == 'S') {
						start_i = i;
						start_j = j;
					}
					else if(maze[i][j] == 'D') {
						end_i = i;
						end_j = j;
					}
					else if(maze[i][j] == 'X') {
						blocks ++;
					}
				}
			}
			//剪枝，如果可走的位置数少于时间，则不可能在T时刻到达门口，因为走完所有位置后会无路可走
			if(M * N - blocks < T) {
				System.out.println("NO");
			}
			else {
				//因为DFS函数用到了全局变量visited，所以需要在调用DFS之前，将visited数组初始化
				for(int i = 0; i < N; i ++) {
					for(int j = 0; j < M; j ++) {
						visited[i][j] = false;
					}
				}
				if(DFS(start_i, start_j, 0)) {
					System.out.println("YES");
				}
				else {
					System.out.println("NO");
				}
			}
		}
	}

}