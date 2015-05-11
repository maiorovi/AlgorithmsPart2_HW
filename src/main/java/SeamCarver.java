public class SeamCarver {
    private static final double BORDER_ENERGY = 195075.0;
    private Picture picture;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        initEnergy();
    }

    private void initEnergy() {
        energy = new double[picture.width()][picture.height()];
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                energy[col][row] = energy(col,row);
            }
        }
    }

    public Picture picture() {
        return this.picture;
    }

    public int width() {
        return energy.length;
    }

    public int height() {
        return energy[0].length;
    }

    public  double energy(int x, int y) {
        if (x > width() - 1 || y > height() - 1 || x < 0 || y < 0)
            throw new IndexOutOfBoundsException();
        if (isBorder(x,y)) {
            return BORDER_ENERGY;
        }
        return calculateDeltaForX(x, y) + calcualteDeltaForY(x, y);
    }

    private double calculateDeltaForX(int x, int y) {
        double blue = getBlue(x+1, y) - getBlue(x-1, y);
        double red = getRed(x+1, y) - getRed(x-1, y);
        double green = getGreen(x+1, y) - getGreen(x-1, y);

        return square(blue) + square(red) + square(green);
    }

    private double calcualteDeltaForY(int x, int y) {
        double blue = getBlue(x, y+1) - getBlue(x, y-1);
        double red = getRed(x, y+1) - getRed(x, y-1);
        double green = getGreen(x, y+1) - getGreen(x, y-1);

        return square(blue) + square(red) + square(green);
    }

    private double square(double value) {
        return Math.pow(value, 2);
    }

    private int getBlue(int x, int y) {
        return picture.get(x,y).getBlue();
    }

    private int getRed(int x, int y) {
        return picture.get(x,y).getRed();
    }

    private int getGreen(int x, int y) {
        return picture.get(x,y).getGreen();
    }

    private boolean isBorder(int x, int y) {
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return true;
        return false;
    }

    public   int[] findHorizontalSeam() {
        double[] distTo = new double[width()*height()];
        int[] edgeTo = new int[width() * height()];
        initArrays(distTo, edgeTo);

        for (int col = 0; col < width() - 1; col++) {
            for (int row = 0; row < height(); row++) {
                if (row - 1 >= 0) {
                    relax(pixelNumber(col,row), pixelNumber(col+1, row-1), edgeTo, distTo);
                }
                relax(pixelNumber(col,row), pixelNumber(col+1,row), edgeTo, distTo);
                if (row + 1 <= height() - 1) {
                    relax(pixelNumber(col,row), pixelNumber(col+1, row + 1), edgeTo, distTo);
                }
            }
        }

        //find minimal seam
        double minDist =Double.POSITIVE_INFINITY;
        int lastPixel = -1;
        for (int row = 0; row < height(); row++) {
            if (minDist > distTo[pixelNumber(width() - 1, row)]) {
                minDist = distTo[pixelNumber(width() - 1, row)];
                lastPixel = pixelNumber(width() - 1, row);
            }
        }

        return restoreHorSeam(lastPixel, edgeTo);
    }

    private int[] restoreHorSeam(int lastPixel, int[] edgeTo) {
        int[] path = new int[width()];
        int currentPixel = lastPixel;

        for (int col = width() - 1; col >= 0; col-- ){
            path[col] = rowFromNumber(currentPixel);
            currentPixel = edgeTo[currentPixel];
        }

        return path;
    }


    private void initArrays(double distTo[], int[] edgeTo) {
        for(int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                if (col == 0) {
                    distTo[pixelNumber(col, row)] = 0;
                } else {
                    distTo[pixelNumber(col,row)] = Double.POSITIVE_INFINITY;
                }
                edgeTo[pixelNumber(col,row)] = -1;
            }
        }
    }

    private void relax(int pixelFrom, int pixelTo,int[] edgeTo, double distTo[]) {
        if (distTo[pixelFrom] + energy[colFromNumber(pixelFrom)][rowFromNumber(pixelFrom)] < distTo[pixelTo]) {
            distTo[pixelTo] = distTo[pixelFrom] + energy[colFromNumber(pixelFrom)][rowFromNumber(pixelFrom)];
            edgeTo[pixelTo] = pixelFrom;
        }
    }

    private int pixelNumber(int col, int row) {
        return row*width() + col;
    }

    private int colFromNumber(int pixelNumber) {
        return pixelNumber % width();
    }

    private int rowFromNumber(int pixelNumber) {
        return pixelNumber / width();
    }

    public   int[] findVerticalSeam() {
        double backUpEnergy[][] = energy;
        transponMatrix();

        int[] result = findHorizontalSeam();

        energy = backUpEnergy;

        return result;
    }

    private void transponMatrix() {
        double[][] transponned = new double[height()][width()];
        for(int col = 0; col < width(); col++) {
            for(int row = 0; row < height(); row++) {
                transponned[row][col] = energy[col][row];
            }
        }
        energy = transponned;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }

        if (height() <= 1) {
            throw new IllegalArgumentException();
        }

        if (seam.length != width()){
            throw new IllegalArgumentException();
        }


        Picture updatedPicture = new Picture(width(), height()-1);
        double[][] updatedPicEnergy = new double[width()][height() - 1];

        for(int col = 0; col < width(); col++) {

            for(int row = 0; row < seam[col]; row++) {
                updatedPicture.set(col, row, picture.get(col,row));
                updatedPicEnergy[col][row] = energy[col][row];
            }

            for(int row = seam[col] + 1; row < height(); row++) {
                updatedPicture.set(col, row - 1, picture.get(col,row));
                updatedPicEnergy[col][row - 1] = energy[col][row];
            }
        }

        picture = updatedPicture;
        energy = updatedPicEnergy;
    }

    public    void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }

        if (width() <= 1) {
            throw new IllegalArgumentException();
        }

        if (seam.length != height()){
            throw new IllegalArgumentException();
        }

        Picture updatedPicture = new Picture(width() - 1, height());
        double[][] updatedPicEnergy = new double[width() - 1][height()];

        for(int row = 0; row < height(); row++) {

            for (int col = 0; col < seam[row]; col++) {
                updatedPicEnergy[col][row] = energy[col][row];
                updatedPicture.set(col, row, picture.get(col, row));
            }

            for (int col = seam[row] + 1; col < width(); col++) {
                updatedPicEnergy[col - 1][row] = energy[col][row];
                updatedPicture.set(col - 1, row, picture.get(col, row));
            }
        }

        picture = updatedPicture;
        energy = updatedPicEnergy;
    }

    public static void main(String[] args) {
        SeamCarver sc = new SeamCarver(new Picture("6x5.png"));

        System.out.println("Picture width = " + sc.width());
        System.out.println("Picture height = " + sc.height());

        sc.findVerticalSeam();
    }
}