package com.onemangrove.xstriker

import org.apache.commons.math3.distribution.BetaDistribution
import java.util.Random

class Randomizer(seed: Long) {
    
    private var seed: Long = 0

    private var random: Random? = null

    init {
        this.seed = seed
        this.random = Random(seed)
    }

    fun nextInt(): Int {
        return this.random!!.nextInt()
    }

    fun nextInt(low: Int, high: Int): Int {
        if (high - low == 0)
            return high
        return Math.abs(this.random!!.nextInt() % (high - low) + low)
    }

    fun nextDouble(): Double {
        return this.random!!.nextDouble()
    }

    fun nextFloat(): Float {
        return this.random!!.nextFloat()
    }

    @JvmOverloads
    fun betaVariant(low: Int, high: Int, alpha: Int = 2, beta: Int = 2): Int {
        if (high - low == 0)
            return high
        val betaDistribution = BetaDistribution(alpha.toDouble(), beta.toDouble())
        val sample = betaDistribution.sample()
        val sampleInt = (sample * 100).toInt()
        return sampleInt % (high - low) + low
    }
    
}
