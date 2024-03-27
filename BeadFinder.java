public class BeadFinder {

    private Queue<Blob> blobs; // all blobs in a picture

    //  finds all blobs in the specified picture using luminance threshold tau
    public BeadFinder(Picture picture, double tau) {
        // instantiation
        blobs = new Queue<Blob>();

        // get dimensions
        int h = picture.height();
        int w = picture.width();

        // 2-D boolean array to keep track of whether a pixel is checked
        boolean[][] isChecked = new boolean[h][w];

        // check each pixel to find blobs using dfs()
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (isChecked[i][j]) {
                    continue;
                }
                // if the pixel is not checked, call the dfs that returns a blob
                Blob blob = dfs(isChecked, picture, i, j, tau);

                // adding new blob found to queue, if not null
                if (blob == null) {
                    continue;
                }
                blobs.enqueue(blob);
            }
        }
    }

    // private helper method: create new blob when first pixel that is bright enough
    // (luminance >= tau) is encountered
    // call the other dfs (which doesn't return and takes a blob as an additional
    // argument) to add to that blob the bright-enough pixels around it
    // return the blob
    private Blob dfs(boolean[][] isChecked, Picture picture,
                     int row, int col, double tau) {
        // get the dimensions of the picture
        int h = picture.height();
        int w = picture.width();

        // base case: index out of bounds or checked
        if (row < 0 || col < 0 || row >= h || col >= w) return null;
        if (isChecked[row][col]) return null;

        isChecked[row][col] = true;

        // base case: if luminance below threshold
        double lum = Luminance.intensity(picture.get(col, row));
        if (lum < tau) return null;

        // create a new blob when a new one is found and add the current pixel to it
        Blob blob = new Blob();
        blob.add(col, row);

        // recursively call the other dfs to check the pixels around the current pixel
        dfs(isChecked, picture, row + 1, col, tau, blob);
        dfs(isChecked, picture, row - 1, col, tau, blob);
        dfs(isChecked, picture, row, col + 1, tau, blob);
        dfs(isChecked, picture, row, col - 1, tau, blob);

        return blob;
    }

    // helper method: recursively check the picture and
    // find the other pixels in the blob
    private void dfs(boolean[][] isChecked, Picture picture,
                     int row, int col, double tau, Blob blob) {
        // get the dimensions of the picture
        int h = picture.height();
        int w = picture.width();

        // base case: index out of bounds or checked
        if (row < 0 || col < 0 || row >= h || col >= w) return;
        if (isChecked[row][col]) return;

        isChecked[row][col] = true;

        // base case: if luminance below threshold
        double lum = Luminance.intensity(picture.get(col, row));
        if (lum < tau) return;

        // add pixel to the blob
        blob.add(col, row);

        // recursively check neighbours
        dfs(isChecked, picture, row + 1, col, tau, blob);
        dfs(isChecked, picture, row - 1, col, tau, blob);
        dfs(isChecked, picture, row, col + 1, tau, blob);
        dfs(isChecked, picture, row, col - 1, tau, blob);
    }

    //  returns all beads (blobs with >= min pixels)
    public Blob[] getBeads(int min) {
        Queue<Blob> beadsQueue = new Queue<Blob>();
        int numBeads = 0;

        // add to the queue all the qualified beads
        for (Blob b : blobs) {
            if (b.mass() < min) {
                continue;
            }
            beadsQueue.enqueue(b);
            numBeads++;
        }

        // transfer beads in the queue to the array that will be returned
        Blob[] beads = new Blob[numBeads];
        int i = 0;
        for (Blob bead : beadsQueue) {
            beads[i] = bead;
            i++;
        }
        return beads;
    }

    // takes an integer min, a floating-point number tau, and the name of
    // an image file as command-line arguments;
    // creates a BeadFinder object using a luminance threshold of tau;
    // and prints all beads (blobs containing at least min pixels)
    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture pic = new Picture(args[2]);

        BeadFinder bf = new BeadFinder(pic, tau);
        for (Blob bead : bf.getBeads(min)) {
            StdOut.println(bead);
        }
    }
}
