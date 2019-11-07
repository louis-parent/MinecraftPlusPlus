/*
 * Modified for simplicity. From : http://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html
 * in apache.commons.math3.distribution
 */

package fr.minecraftpp.util.normalDistribution;

import java.util.Random;

public class NormalDistribution
{
	public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1e-9;
	private static final long serialVersionUID = 8589540077390120676L;
	private static final double SQRT2 = Math.sqrt(2.0);
	private final double mean;
	private final double standardDeviation;
	private final double logStandardDeviationPlusHalfLog2Pi;
	private final double solverAbsoluteAccuracy;

	public NormalDistribution() throws Exception
	{
		this(0, 1);
	}

	public NormalDistribution(double mean, double sd) throws Exception
	{
		this(mean, sd, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
	}

	public NormalDistribution(double mean, double sd, double inverseCumAccuracy) throws Exception
	{
		this(new Random(), mean, sd, inverseCumAccuracy);
	}

	public NormalDistribution(Random rng, double mean, double sd) throws Exception
	{
		this(rng, mean, sd, DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
	}

	public NormalDistribution(Random rng, double mean, double sd, double inverseCumAccuracy) throws Exception
	{
		if (sd <= 0)
		{
			throw new Exception("sd must be greater than 0");
		}

		this.mean = mean;
		standardDeviation = sd;
		logStandardDeviationPlusHalfLog2Pi = Math.log(sd) + 0.5 * Math.log(2 * Math.PI);
		solverAbsoluteAccuracy = inverseCumAccuracy;
	}

	public double getMean()
	{
		return mean;
	}

	public double getStandardDeviation()
	{
		return standardDeviation;
	}

	public double density(double x)
	{
		return Math.exp(logDensity(x));
	}

	public double logDensity(double x)
	{
		final double x0 = x - mean;
		final double x1 = x0 / standardDeviation;
		return -0.5 * x1 * x1 - logStandardDeviationPlusHalfLog2Pi;
	}

	public double cumulativeProbability(double x) throws Exception
	{
		final double dev = x - mean;
		if (Math.abs(dev) > 40 * standardDeviation)
		{
			return dev < 0 ? 0.0d : 1.0d;
		}
		return 0.5 * Erf.erfc(-dev / (standardDeviation * SQRT2));
	}

	public double inverseCumulativeProbability(final double p) throws Exception
	{
		if (p < 0.0 || p > 1.0)
		{
			throw new Exception("Out of range");
		}
		return mean + standardDeviation * SQRT2 * Erf.erfInv(2 * p - 1);
	}

	public double probability(double x0, double x1) throws Exception
	{
		if (x0 > x1)
		{
			throw new Exception("Number is too large");
		}
		final double denom = standardDeviation * SQRT2;
		final double v0 = (x0 - mean) / denom;
		final double v1 = (x1 - mean) / denom;
		return 0.5 * Erf.erf(v0, v1);
	}
}
