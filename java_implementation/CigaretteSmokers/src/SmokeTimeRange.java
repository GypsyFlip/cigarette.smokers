public class SmokeTimeRange {
    final int min, max;

    public SmokeTimeRange(int min, int max) {
	if (min < 0 || max < 0 || max < min) throw new IllegalArgumentException("Both min and max must be >=0 and max must be > min");
	this.min = min;
	this.max = max;
    }
}