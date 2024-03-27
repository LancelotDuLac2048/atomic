public class Avogadro {

    // reads the radial displacements from standard input
    // and estimates Boltzmann’s constant and Avogadro’s number
    public static void main(String[] args) {
        double variance = 0.0;
        int num = 0; // number of distances
        double METER_PER_PIXEL = 0.175E-6;

        // read from StdIn all distances and add distance squared to variance
        while (!StdIn.isEmpty()) {
            double r = StdIn.readDouble() * METER_PER_PIXEL;
            variance += Math.pow(r, 2);
            num++;
        }

        // compute d
        double d = variance / (2 * num);

        // define constants
        double ABS_TEMP = 297.0;
        double ETA = 9.135E-4;
        double RHO = 0.5E-6;
        double UNIV_GAS_CONST = 8.31446;

        // calculate boltzmann const
        double k = 6 * Math.PI * ETA * RHO * d / ABS_TEMP;

        // calculate avogadro const
        double avogadro = UNIV_GAS_CONST / k;

        // format and print
        StdOut.printf("Boltzmann = %.4e\nAvogadro = %.4e", k, avogadro);
    }
}
