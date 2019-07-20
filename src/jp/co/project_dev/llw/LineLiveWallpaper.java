package jp.co.project_dev.llw;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LineLiveWallpaper extends WallpaperService{
	
	private static final int DELAY_TIME = 66;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		return new WallpaperEngine();
	}

	public class WallpaperEngine extends WallpaperService.Engine implements Runnable{
	
		private final Handler handler = new Handler();
		private boolean visible = false;
		private int width = 0;
		private int height = 0;
		int cnt = 0;
		List<LineDraw> lines = null;
		
		public void run() {
			drawFrame();
		}

		public WallpaperEngine() {
			super();
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			lines = new ArrayList<LineDraw>();
			LineDraw line = null;
			for(int i = 0; i < 3; i++){
				line = new LineDraw();
				line.intalize();
				lines.add(line);
			}
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			handler.removeCallbacks(this);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {
//			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
//					xPixelOffset, yPixelOffset);
			drawFrame();
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			this.width = width;
			this.height = height;
			drawFrame();
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			visible = false;
			handler.removeCallbacks(this);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			//super.onVisibilityChanged(visible);
			this.visible = visible;
			if(visible){
				drawFrame();
			}else{
				handler.removeCallbacks(this);
			}
		}
		
		private void drawFrame(){
			SurfaceHolder holder = getSurfaceHolder();
			Canvas c = holder.lockCanvas();

			cnt++;
			if(cnt >= 2){
				cnt = 0;
				for(LineDraw line : lines){
					line.posMove();
				}
			}

			c.drawColor(Color.BLACK);
			Paint p = new Paint();
			LineDraw line = null;
			for(int i = 0; i < lines.size(); i++){
				line = lines.get(i);
				switch(i % 5){
				case 0:
					p.setColor(Color.GREEN);
					break;
				case 1:
					p.setColor(Color.BLUE);
					break;
				case 2:
					p.setColor(Color.RED);
					break;
				case 3:
					p.setColor(Color.WHITE);
					break;
				case 4:
					p.setColor(Color.YELLOW);
					break;
				}
				line.drawLines(c, p, width, height);
			}
			
			holder.unlockCanvasAndPost(c);
			handler.removeCallbacks(this);
			if(visible){
				handler.postDelayed(this, DELAY_TIME);
			}
		}
	}
}