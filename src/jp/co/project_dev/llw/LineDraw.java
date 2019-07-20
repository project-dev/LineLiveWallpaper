package jp.co.project_dev.llw;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;

public class LineDraw {
	private static final int MAX_LINE_HIST=10;

	private List<int[][]> posList = new ArrayList<int[][]>(); 
	private int[] addX = null;
	private int[] addY = null;

	public void intalize(){
		int[][] base = null;
		Math.rint(Calendar.getInstance().getTimeInMillis());
		posList = new ArrayList<int[][]>(); 
		for(int i = 0; i < MAX_LINE_HIST; i++){
			if(i == 0){
				base = new int[][]{
						 {getRnd(), getRnd()}
						,{getRnd(), getRnd()}
						,{getRnd(), getRnd()}
						,{getRnd(), getRnd()}
				};
				posList.add(base);
			}else{
				posList.add(new int[][]{
						 {base[0][0],base[0][1]}
						,{base[1][0],base[1][1]}
						,{base[2][0],base[2][1]}
						,{base[3][0],base[3][1]}
				});
			}
		}
		
		addX = new int[]{getRndAdd(), getRndAdd(), getRndAdd(), getRndAdd()};
		addY = new int[]{getRndAdd(), getRndAdd(), getRndAdd(), getRndAdd()};
		
	}
	int getRnd(){
		return (int)(Math.random() * 400.0);
	}
	int getRndAdd(){
		int ret = (int)(Math.random() * 10.0) - 5;
		if(ret == 0){
			if(Math.random() * 10 > 5){
				ret = -1;
			}else{
				ret = 1;
			}
		}
		return ret;
	}
	
	public void posMove(){
		for(int i = posList.size() - 2; i >= 0; i--){
			posList.get(i + 1)[0][0] = posList.get(i)[0][0];
			posList.get(i + 1)[0][1] = posList.get(i)[0][1];
			posList.get(i + 1)[1][0] = posList.get(i)[1][0];
			posList.get(i + 1)[1][1] = posList.get(i)[1][1];
			posList.get(i + 1)[2][0] = posList.get(i)[2][0];
			posList.get(i + 1)[2][1] = posList.get(i)[2][1];
			posList.get(i + 1)[3][0] = posList.get(i)[3][0];
			posList.get(i + 1)[3][1] = posList.get(i)[3][1];
		}
	}

	public void drawLines(Canvas c, Paint p, int width, int height){
		float pts[] = new float[16];
		for(int[][] pos : posList){
			pts[ 0] = pos[0][0];
			pts[ 1] = pos[0][1];
			pts[ 2] = pos[1][0];
			pts[ 3] = pos[1][1];

			pts[ 4] = pos[1][0];
			pts[ 5] = pos[1][1];
			pts[ 6] = pos[2][0];
			pts[ 7] = pos[2][1];

			pts[ 8] = pos[2][0];
			pts[ 9] = pos[2][1];
			pts[10] = pos[3][0];
			pts[11] = pos[3][1];

			pts[12] = pos[3][0];
			pts[13] = pos[3][1];
			pts[14] = pos[0][0];
			pts[15] = pos[0][1];
			c.drawLines(pts, p);
		}

		for(int i = 0; i < 4; i++){
			posList.get(0)[i][0] += addX[i];
			posList.get(0)[i][1] += addY[i];
			if(posList.get(0)[i][0] <= 0){
				addX[i] = Math.abs(getRndAdd());
			}else if(posList.get(0)[i][0] >= width){
				addX[i] = Math.abs(getRndAdd()) * - 1;
			}
			if(posList.get(0)[i][1] <= 0){
				addY[i] = Math.abs(getRndAdd());
			}else if(posList.get(0)[i][1] >= height){
				addY[i] = Math.abs(getRndAdd()) * - 1;
			}
		}
		
	}

}
