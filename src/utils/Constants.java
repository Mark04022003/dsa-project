package utils;

public class Constants {
	
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
