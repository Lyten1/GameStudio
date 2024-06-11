package sk.tuke.kpi.oop.game;

public enum Direction {
    NONE(0,0),
    NORTH(0,1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),

    NORTHWEST(-1, 1),
    NORTHEAST(1, 1),
    SOUTHWEST(-1, -1),
    SOUTHEAST(1, -1);




    private final int dx;
    private final int dy;
    private float angle;

    static {
        NORTH.angle = 0;
        NORTHWEST.angle = 45;
        WEST.angle = 90;
        SOUTHWEST.angle = 135;
        SOUTH.angle = 180;
        SOUTHEAST.angle = 225;
        EAST.angle = 270;
        NORTHEAST.angle = 315;
    }

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction fromAngle (float angel) {
        switch ((int) angel) {
            case 0:
                return NORTH;
            case 45:
                return NORTHWEST;
            case 90:
                return WEST;
            case 135:
                return SOUTHWEST;
            case 180:
                return SOUTH;
            case 225:
                return SOUTHEAST;
            case 270:
                return EAST;
            case 315:
                return NORTHEAST;
            default:
                return NONE;
        }

    }
    public float getAngle() {
        return angle;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction combine(Direction newDir){
        int dx = getDx();
        int dy = getDy();

        if(newDir.getDx() != 0)
            if(dx + newDir.getDx() == 0){
                dx = 0;
            }
            else {
                dx = newDir.dx;
            }
        if(newDir.getDy() != 0)
            if(dy + newDir.getDy() == 0){
                dy = 0;
            }
            else {
                dy = newDir.dy;
            }


        Direction direct = NONE;

        for(Direction dir : Direction.values()){
            if(dir.getDx() == dx && dir.getDy() == dy)
                direct = dir;
        }
        return direct;
    }
}
