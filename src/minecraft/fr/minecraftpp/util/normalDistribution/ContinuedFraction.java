/*
 * Modified for simplicity. From : http://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html
 * in apache.commons.math3.util
 */

package fr.minecraftpp.util.normalDistribution;

public abstract class ContinuedFraction
{

	private static final double DEFAULT_EPSILON = 10e-9;

	protected ContinuedFraction()
	{
		super();
	}

	protected abstract double getA(int n, double x);

	protected abstract double getB(int n, double x);

	public double evaluate(double x) throws Exception
	{
		return evaluate(x, DEFAULT_EPSILON, Integer.MAX_VALUE);
	}

	public double evaluate(double x, double epsilon) throws Exception
	{
		return evaluate(x, epsilon, Integer.MAX_VALUE);
	}

	public double evaluate(double x, int maxIterations) throws Exception
	{
		return evaluate(x, DEFAULT_EPSILON, maxIterations);
	}

	public double evaluate(double x, double epsilon, int maxIterations) throws Exception
	{
		final double small = 1e-50;
		double hPrev = getA(0, x);

		if (equals(hPrev, 0.0, small))
		{
			hPrev = small;
		}

		int n = 1;
		double dPrev = 0.0;
		double cPrev = hPrev;
		double hN = hPrev;

		while (n < maxIterations)
		{
			final double a = getA(n, x);
			final double b = getB(n, x);

			double dN = a + b * dPrev;
			if (equals(dN, 0.0, small))
			{
				dN = small;
			}
			double cN = a + b / cPrev;
			if (equals(cN, 0.0, small))
			{
				cN = small;
			}

			dN = 1 / dN;
			final double deltaN = cN * dN;
			hN = hPrev * deltaN;

			if (Double.isInfinite(hN))
			{
				throw new Exception("Convergence exception : " + x);
			}
			if (Double.isNaN(hN))
			{
				throw new Exception("Convergence exception : " + x);
			}

			if (Math.abs(deltaN - 1.0) < epsilon)
			{
				break;
			}

			dPrev = dN;
			cPrev = cN;
			hPrev = hN;
			n++;
		}

		if (n >= maxIterations)
		{
			throw new Exception("Max count exceeded: " + maxIterations);
		}

		return hN;
	}

	/*
	 * From apache.commons.math3.util.precision
	 */

	private static final int POSITIVE_ZERO_FLOAT_BITS = Float.floatToRawIntBits(+0.0f);
	private static final int NEGATIVE_ZERO_FLOAT_BITS = Float.floatToRawIntBits(-0.0f);
	private static final int SGN_MASK_FLOAT = 0x80000000;

	public static boolean equals(double x, double y, double eps)
	{
		return equals(x, y, 1) || Math.abs(y - x) <= eps;
	}

	public static boolean equals(final float x, final float y, final int maxUlps)
	{

		final int xInt = Float.floatToRawIntBits(x);
		final int yInt = Float.floatToRawIntBits(y);

		final boolean isEqual;
		if (((xInt ^ yInt) & SGN_MASK_FLOAT) == 0)
		{

			isEqual = Math.abs(xInt - yInt) <= maxUlps;
		}
		else
		{

			final int deltaPlus;
			final int deltaMinus;
			if (xInt < yInt)
			{
				deltaPlus = yInt - POSITIVE_ZERO_FLOAT_BITS;
				deltaMinus = xInt - NEGATIVE_ZERO_FLOAT_BITS;
			}
			else
			{
				deltaPlus = xInt - POSITIVE_ZERO_FLOAT_BITS;
				deltaMinus = yInt - NEGATIVE_ZERO_FLOAT_BITS;
			}

			if (deltaPlus > maxUlps)
			{
				isEqual = false;
			}
			else
			{
				isEqual = deltaMinus <= (maxUlps - deltaPlus);
			}

		}

		return isEqual && !Float.isNaN(x) && !Float.isNaN(y);
	}
}
