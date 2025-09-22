package utils;

import static utils.Constants.EnemyConstants.ORC_BOYZ_HEIGHT;

import main.Game;

public class Constants {
	
	public static class EnemyConstants{
		public static final int ORC_BOYZ = 0;
		
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		public static final int ORC_BOYZ_WIDTH_DEFAULT = 32;
		public static final int ORC_BOYZ_HEIGHT_DEFAULT = 32;
		
		public static final int ORC_BOYZ_WIDTH = (int) (ORC_BOYZ_WIDTH_DEFAULT * Game.SCALE + 48);
		public static final int ORC_BOYZ_HEIGHT = (int) (ORC_BOYZ_HEIGHT_DEFAULT * Game.SCALE + 48);
		public static final int ORC_BOYZ_DRAWOFFSET_X = (int) (16* Game.SCALE);
		public static final int ORC_BOYZ_DRAWOFFSET_Y = (int) (9 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch(enemyType) {
			case ORC_BOYZ:
				switch(enemyState) {
				case IDLE:
					return 6;
				case RUNNING:
					return 8;
				case ATTACK:
					return 6;
				case HIT:
					return 4;
				case DEAD:
					return 4;
				}
				
			}
			return 0;
		}
	}
	
	public static class Environment{
		public static final int BIG_CLOUDS_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUDS_HEIGHT_DEFAULT = 101;
		public static final int BIG_CLOUDS_WIDTH = (int) (BIG_CLOUDS_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUDS_HEIGHT = (int) (BIG_CLOUDS_HEIGHT_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUDS_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUDS_HEIGHT_DEFAULT = 24;
		public static final int SMALL_CLOUDS_WIDTH = (int) (SMALL_CLOUDS_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUDS_HEIGHT = (int) (SMALL_CLOUDS_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final float SCALE = 1.5f;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (100 * Game.SCALE);
			public static final int SLIDER_HEIGHT = (int) (44 * Game.SCALE);
		}
		public static class URMButtons{
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
		}
		public static class VolumeButtons{
			public static final int VOLUME_WIDTH_DEFAULT = 28;
			public static final int VOLUME_HEIGHT_DEFAULT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK_1 = 2;
		public static final int JUMP = 3;
		public static final int FALLING = 4;
		public static final int GROUND = 5;
		public static final int ATTACK_2 = 6;
		public static final int HURT =7;
		public static final int DEATH = 8;
		
		public static final int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case IDLE:
				return 6;
			case RUNNING:
				return 8;
			case ATTACK_1:
				return 6;
			case JUMP:
				return 3;
			case FALLING:
				return 1;
			case GROUND:
				return 2;
			case ATTACK_2:
				return 9;
			case HURT:
				return 4;
			case DEATH:
				return 4;
			default:
				return 1;
			}
		}
	}
	
}