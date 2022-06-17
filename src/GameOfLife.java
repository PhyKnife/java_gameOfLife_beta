public class GameOfLife {
    //场地的宽度
    private static final int CELLS_WIDTH = 85;
    //场地的高度
    private static final int CELLS_HEIGHT = 45;
    //活细胞显示
    private static final String LIVE_CELL = "█";
    //死细胞显示
    private static final String DEATH_CELL = "  ";
    //检测半径（以自身为中心，R为半径的圆形的外接正方形）
    private static final int R = 1;
    //检测范围内，使活细胞存活的 邻胞数量的最小值
    private static final int LIVE_NUM_MIN = 2;
    //检测范围内，使活细胞存活的 邻胞数量的最大值
    private static final int LIVE_NUM_MAX = 3;
    //检测范围内，使死细胞复活的 邻胞数量的最小值
    private static final int BIRTH_NUM_MIN = 3;
    //检测范围内，使死细胞复活的 邻胞数量的最大值
    private static final int BIRTH_NUM_MAX = 3;

    public static void main(String[] args) {
        //创建矩形全死细胞集
        byte[][] cells = newCells(CELLS_WIDTH, CELLS_HEIGHT);
        System.out.println(str(cells));

        //随机复活其中的细胞
        randPoint(cells);
        System.out.println(str(cells));

        //开始迭代演示
        while (true) {
            cells = iterative(cells);
            System.out.println(str(cells));
        }

    }

    /**
     * 生成一个空的全是0的二维数组并返回
     * input width 宽度
     * input height 高度
     * return 二维数组
     */
    private static byte[][] newCells(int width, int height){
        byte[][] cellsArray = new byte[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cellsArray[i][j] = 0;
            }
        }
        return cellsArray;
    }

    /**
     * 将二维数组随机化，该函数直接改传入数组的状态
     * input cellsArray 二维数组
     */
    private static void randPoint(byte[][] cellsArray){
        for (int i = 0; i < cellsArray.length; i++) {
            for (int j = 0; j < cellsArray[i].length; j++) {
                int rd = Math.random()>0.5 ? 1 : 0;
                if(rd == 1) {
                    cellsArray[i][j] = 1;
                }
            }
        }
    }

    /**
     * 传入二维数组并使其按照生命游戏规则迭代一次，迭代完成后返回一个新的迭代后的二维数组
     * input cellsArray 迭代前的二维数组
     * return 迭代后的二维数组
     */
    private static byte[][] iterative(byte[][] cellsArray){
        byte[][] afterCells = newCells(cellsArray[0].length, cellsArray.length);

        for (int i = 0; i < cellsArray.length; i++) {
            for (int j = 0; j < cellsArray[i].length; j++) {
                int neighborNum = 0;
                int top = Math.max(i - R, 0);
                int button = Math.min(cellsArray.length-1, i + R);
                int left = Math.max(j - R, 0);
                int right = Math.min(j + R, cellsArray[i].length-1);
                for( int y = top; y <= button; y++){
                    for (int x = left; x <= right; x++){
                        if(cellsArray[y][x] == 1){
                            if(j != x || i != y){
                                neighborNum++;
                            }
                        }
                    }
                }
                if(cellsArray[i][j] == 1){
                    //存活
                    if(LIVE_NUM_MIN <= neighborNum && neighborNum <= LIVE_NUM_MAX){
                        afterCells[i][j] = 1;
                    }
                }else if(cellsArray[i][j] == 0){
                    //诞生
                    if(BIRTH_NUM_MIN <= neighborNum && neighborNum <= BIRTH_NUM_MAX){
                        afterCells[i][j] = 1;
                    }
                }
            }
        }
        return afterCells;
    }

    /**
     * 传入二维数组并返回打印在屏幕上的字符串形式
     * input cellsArray 二维数组
     * return 打印在屏幕上的字符串形式
     */
    private static String str(byte[][] cellsArray){
        StringBuilder cellsString = new StringBuilder();
        for (byte[] bytes : cellsArray) {
            for (byte aByte : bytes) {
                if (aByte == 0) {
                    cellsString.append(DEATH_CELL);
                } else {
                    cellsString.append(LIVE_CELL);
                }
            }
            cellsString.append("\n");
        }
        return cellsString.toString();
    }


}

