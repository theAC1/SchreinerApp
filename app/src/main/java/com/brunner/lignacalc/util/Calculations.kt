package com.brunner.lignacalc.util

import kotlin.math.*

object Calculations {

    /**
     * Falsche Gehrung
     * Berechnet Alpha und Beta aus Gamma, Seite A und Seite B.
     * d = sqrt(x² + y² - 2·x·y·cos(180-γ))
     * x = A / cos(30°)
     * y = B / cos(30°)
     * α = arccos((d² + y² - x²) / (2·d·y))
     * β = γ - α
     */
    data class GehrungResult(val alpha: Double, val beta: Double)

    fun computeGehrung(gamma: Double, sideA: Double, sideB: Double): GehrungResult? {
        if (gamma <= 0 || sideA <= 0 || sideB <= 0) return null

        val x = sideA / cos(30.0 / 180.0 * PI)
        val y = sideB / cos(30.0 / 180.0 * PI)
        val d = sqrt(x.pow(2) + y.pow(2) - (2 * x * y * cos((180 - gamma) / 180.0 * PI)))

        if (d == 0.0) return null

        val alphaRad = acos((d.pow(2) + y.pow(2) - x.pow(2)) / (2 * d * y))
        val alpha = alphaRad * (180.0 / PI)
        val beta = gamma - alpha

        return GehrungResult(
            alpha = roundTo(alpha, 3),
            beta = roundTo(beta, 3)
        )
    }

    /**
     * Zahnvorschub
     * fz = (v × 1000) / (n × z)
     * v = Vorschubgeschwindigkeit in m/min
     * n = Drehzahl in U/min
     * z = Anzahl Zähne
     */
    fun computeZahnvorschub(v: Double, n: Double, z: Double): Double? {
        if (v <= 0 || n <= 0 || z <= 0) return null
        return roundTo((v * 1000) / (n * z), 3)
    }

    /**
     * Kantenmaterial Restlänge
     * Restlänge = π · (A² - B²) / (4 · S · 1000)
     * S = Materialstärke in mm
     * A = Aussendurchmesser in mm
     * B = Innendurchmesser (Kern) in mm
     */
    fun computeKantenmaterialRestlaenge(s: Double, outerDiameter: Double, innerDiameter: Double): Double? {
        if (s <= 0 || outerDiameter <= 0 || innerDiameter <= 0) return null
        if (outerDiameter <= innerDiameter) return null
        return roundTo(PI * (outerDiameter.pow(2) - innerDiameter.pow(2)) / (4 * s * 1000), 3)
    }

    /**
     * Goldener Schnitt
     * (a + b) : a = 1.618
     * Aus Major → berechne Minor und Gesamtlänge
     * Aus Minor → berechne Major und Gesamtlänge
     * Aus Gesamtlänge → berechne Major und Minor
     */
    data class GoldenRatioResult(val major: Double, val minor: Double, val total: Double)

    private const val PHI = 1.618

    fun goldenRatioFromMajor(major: Double): GoldenRatioResult? {
        if (major <= 0) return null
        val total = major * PHI
        val minor = total - major
        return GoldenRatioResult(roundTo(major, 3), roundTo(minor, 3), roundTo(total, 3))
    }

    fun goldenRatioFromMinor(minor: Double): GoldenRatioResult? {
        if (minor <= 0) return null
        val major = minor * PHI
        val total = major + minor
        return GoldenRatioResult(roundTo(major, 3), roundTo(minor, 3), roundTo(total, 3))
    }

    fun goldenRatioFromTotal(total: Double): GoldenRatioResult? {
        if (total <= 0) return null
        val major = total / PHI
        val minor = total - major
        return GoldenRatioResult(roundTo(major, 3), roundTo(minor, 3), roundTo(total, 3))
    }

    /**
     * Schnittgeschwindigkeit
     * Vc = π · D · n / (1000 · 60)
     * D = Werkzeugdurchmesser in mm
     * n = Drehzahl in U/min
     * Ergebnis in m/s
     */
    fun computeSchnittgeschwindigkeit(diameter: Double, rpm: Double): Double? {
        if (diameter <= 0 || rpm <= 0) return null
        return roundTo((PI * diameter * rpm) / (1000 * 60), 3)
    }

    /**
     * Prüft ob Schnittgeschwindigkeit im erlaubten Bereich liegt
     */
    fun isInRange(value: Double, min: Double, max: Double): Boolean {
        return value in min..max
    }

    /**
     * Holzschwund
     * Ergebnis = Länge × (Koeffizient × Feuchtigkeitsänderung%)
     */
    fun computeHolzschwund(length: Double, coefficient: Double, moistureChange: Double): Double? {
        if (length <= 0 || moistureChange <= 0) return null
        return roundTo(length * (coefficient * moistureChange), 3)
    }

    /**
     * Plattenmaterial Gewicht
     * Gewicht = Länge × Breite × Dicke × Faktor
     * Alle Masse in mm, Ergebnis in kg
     */
    fun computePlattenmaterialGewicht(length: Double, width: Double, thickness: Double, weightFactor: Double): Double? {
        if (length <= 0 || width <= 0 || thickness <= 0) return null
        return roundTo(length * width * thickness * weightFactor, 3)
    }

    /**
     * Einheiten umrechnen
     */
    fun convertUnit(value: Double, factor: Double): Double {
        return roundTo(value * factor, 3)
    }

    private fun roundTo(value: Double, decimals: Int): Double {
        val factor = 10.0.pow(decimals)
        return (value * factor).roundToLong() / factor
    }
}
