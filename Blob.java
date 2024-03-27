public class Blob {

    private int mass; // mass / number of pixels
    private double cmx; // x-pos of center of mass
    private double cmy; // y-pos of center of mass

    //  creates an empty blob
    public Blob() {
        cmx = 0.0;
        cmy = 0.0;
        mass = 0;
    }

    // adds pixel (x, y) to this blob
    // mass of one pixel = 1
    public void add(int x, int y) {
        cmx = (cmx * mass + x) / (mass + 1);
        cmy = (cmy * mass + y) / (mass + 1);
        mass += 1;
    }

    //  number of pixels added to this blob
    public int mass() {
        return mass;
    }

    //  Euclidean distance between the center of masses of the two blobs
    public double distanceTo(Blob that) {
        // corner case:
        if (this.mass == 0 || that.mass == 0) {
            return Double.NaN;
        }
        double dx = this.cmx - that.cmx;
        double dy = this.cmy - that.cmy;
        return Math.sqrt(dx * dx + dy * dy);
    }

    //  string representation of this blob (see below)
    public String toString() {
        // corner case:
        if (mass == 0) {
            return "0 (NaN, NaN)";
        }
        return String.format("%2d (%8.4f, %8.4f)", mass, cmx, cmy);
    }

    //  tests this class by directly calling all instance methods
    public static void main(String[] args) {
        // blob 1
        Blob blob1 = new Blob();
        blob1.add(0, 0);
        StdOut.println("blob 1: " + blob1.toString());
        StdOut.println("blob 1 mass (1): " + blob1.mass());
        blob1.add(0, 2);
        StdOut.println("after adding new pt, blob 1: " + blob1.toString());
        StdOut.println("after adding new pt, blob 1 mass (2): " + blob1.mass());

        // blob 2
        Blob blob2 = new Blob();
        blob2.add(0, 0);
        blob2.add(2, 2);
        blob2.add(0, 2);
        blob2.add(2, 0);
        StdOut.println("blob 2: " + blob2.toString());
        StdOut.println("blob 2 mass (4): " + blob2.mass());

        StdOut.println("distance (1): " + blob1.distanceTo(blob2));
    }
}
