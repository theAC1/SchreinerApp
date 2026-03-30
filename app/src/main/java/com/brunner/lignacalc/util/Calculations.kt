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
     * Lamello P-System — Verbindungsrechner
     *
     * Universelle Formel (verifiziert gegen offiziellen Lamello-Rechner):
     *
     *   Gehrung (G):
     *     X1 = (M/2 + s·cos(α/2)) / sin(α/2)
     *     X2 = (M/2 - s·cos(α/2)) / sin(α/2)
     *     Y1 = Y4 = M/2 + s·cos(α/2)
     *     Y2 = Y3 = M/2 - s·cos(α/2)
     *
     *   Stoss (S) / T-Verbindung (T):
     *     X1 = (M/2 + s·cos(α)) / sin(α)
     *     X2 = (M/2 - s·cos(α)) / sin(α)
     *     Y1 = Y4 = M/2 + s·cos(α)
     *     Y2 = Y3 = M/2 - s·cos(α)
     *
     *   Mittelwand (M):
     *     wie Stoss für α ≤ 90°
     *     X1/X2 getauscht für α > 90°
     *
     * @param m1 Materialstärke Werkstück 1 in mm
     * @param m2 Materialstärke Werkstück 2 in mm
     * @param angleDeg Verbindungswinkel in Grad
     * @param slotOffset s-Wert (Slot-Offset) des Connectors in mm
     * @param situation Verbindungssituation (S, G, M, T)
     * @param tolerance Toleranz ± in mm (Standard: 1.1)
     */
    data class LamelloResult(
        val x1: Double,
        val x2: Double,
        val x1Min: Double,
        val x1Max: Double,
        val x2Min: Double,
        val x2Max: Double,
        val y1: Double,
        val y2: Double,
        val y3: Double,
        val y4: Double
    )

    fun computeLamello(
        m1: Double,
        m2: Double,
        angleDeg: Double,
        slotOffset: Double,
        situation: String = "G",
        tolerance: Double = 1.1
    ): LamelloResult? {
        if (m1 <= 0 || m2 <= 0 || angleDeg <= 0 || angleDeg >= 360) return null
        if (slotOffset < 0) return null

        val halfM = m1 / 2.0

        // Winkel für die Berechnung bestimmen
        // Gehrung: α/2 (jedes Werkstück wird auf halben Winkel geschnitten)
        // Stoss/T/Mittelwand: α direkt (Stirnfläche trifft auf Oberfläche)
        val calcAngleRad = when (situation) {
            "G" -> (angleDeg / 2.0) * PI / 180.0
            else -> angleDeg * PI / 180.0
        }

        val sinA = sin(calcAngleRad)
        val cosA = cos(calcAngleRad)

        if (sinA <= 0.0) return null

        var x1 = roundTo((halfM + slotOffset * cosA) / sinA, 1)
        var x2 = roundTo((halfM - slotOffset * cosA) / sinA, 1)

        // Mittelwand: X1/X2 tauschen bei α > 90°
        if (situation == "M" && angleDeg > 90) {
            val tmp = x1
            x1 = x2
            x2 = tmp
        }

        // Y-Werte: Nuttiefe in die Werkstücke
        val y1 = roundTo(halfM + slotOffset * cosA, 1)
        val y2 = roundTo(halfM - slotOffset * cosA, 1)
        val y3 = roundTo(m1 / 2.0 - slotOffset * cosA, 1)
        val y4 = roundTo(m1 / 2.0 + slotOffset * cosA, 1)

        return LamelloResult(
            x1 = x1,
            x2 = x2,
            x1Min = roundTo(x1 - tolerance, 1),
            x1Max = roundTo(x1 + tolerance, 1),
            x2Min = roundTo(x2 - tolerance, 1),
            x2Max = roundTo(x2 + tolerance, 1),
            y1 = y1,
            y2 = y2,
            y3 = y3,
            y4 = y4
        )
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
