public class BeadTracker {

    // takes an integer min, a double value tau, a double value delta,
    // and a sequence of image filenames as command-line arguments;
    // identifies the beads (using the specified values for min and tau)
    // in each image (using BeadFinder);
    // and prints the distance that each bead moves from one frame to the next
    // (assuming that distance is no longer than delta).
    public static void main(String[] args) {
        // command line arguments
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);

        // take one picture to create a new beadFinder each time
        // and store the beads on beadFinder
        Picture currentPic = new Picture(args[3]);
        BeadFinder currentBF = new BeadFinder(currentPic, tau);
        Blob[] currentBeads = currentBF.getBeads(min);

        // loop through the pictures
        for (int i = 4; i < args.length; i++) {
            // create the next picture and create a new beadFinder to find beads
            Picture nextPic = new Picture(args[i]);
            BeadFinder nextBF = new BeadFinder(nextPic, tau);
            Blob[] nextBeads = nextBF.getBeads(min);

            // iterate through all beads in the second frame
            for (Blob beadFrame2 : nextBeads) {
                double minDist = Double.POSITIVE_INFINITY;

                // iterate through all the beads in the first frame to find
                // the bead that closest to the current bead in the second frame
                for (Blob beadFrame1 : currentBeads) {
                    double currentDist = beadFrame2.distanceTo(beadFrame1);
                    if (currentDist < minDist) {
                        minDist = currentDist;
                    }
                }

                // if the minimum distance is greater than delta, discard it,
                // otherwise add it to the queue
                if (minDist > delta) {
                    continue;
                }
                StdOut.printf("%.4f \n", minDist);
            }

            // the next beads (second frame) become the current beads (first frame)
            // in the next iteration
            currentBeads = nextBeads;
        }
    }
}
