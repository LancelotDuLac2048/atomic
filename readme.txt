Final Programming Project: Atomic Nature of Matter
This is the final project for my f2023 intro programming class.

/**********************************************************************
 * Approximate number of hours to complete this assignment            *
 **********************************************************************/

Number of hours: 5 hrs

/**************************************************************************
 *  The input size n for BeadTracker is the product of the number of      *
 *  pixels per frame and the number of frames. What is the estimated      *
 *  running time (in seconds) of BeadTracker as a function of n?          *
 *  Justify your answer with empirical data and explain how you used it.  *
 *  Your answer should be of the form a*n^b where b is an integer.        *
 **************************************************************************/

Since the number of beads in a picture is negligible compared to total number of
pixels in a picture, the nested for loop doesn't contribute significantly to the
run-time, while the creation of the BeadFinder objects (one per each picture) takes
significantly more time because it needs to traverse through all pixels in the picture.

We create one BeadFinder object per picture; assuming that the operations take
constant time per each pixel, we hypothesize the run-time to be linear to the input
size n (number of total pixels read).

number of pixels in a frame = picture.height() * picture.width()
= 640 * 480 = 307200 pixels

    number of frames    n/pixels    t/sec
    10                  3072000     4.097
    20                  6144000     7.905
    40                  12288000    15.123
    80                  24576000    31.44
    160                 49152000    60.193

ratio 60.193 / 31.44 = 1.914
Ratio rounded to closest integer = 2
log_2(2) = 1
b = 1

Pick one data point in order to compute a
T(N) = a * N
60.193 = a * 49152000
a = 60.193 / 49152000
a = 1.225e-06

T(N) = 1.225e-06 * N, where N is the total number of pixels read in across all
frames being processed

/**********************************************************************
 *  Did you encounter any serious problems? If so, please describe.
 **********************************************************************/

    No.

/**************************************************************************
 *  List any other comments here.                                         *
 **************************************************************************/

    It's overrrrrrr
