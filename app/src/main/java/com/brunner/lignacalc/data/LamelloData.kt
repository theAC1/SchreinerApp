package com.brunner.lignacalc.data

/**
 * Lamello P-System Verbindungsrechner — Datenmodell
 *
 * Jeder Connector hat einen festen Slot-Offset s (mm) pro Bearbeitungstechnologie.
 * s beschreibt die Verschiebung des Connector-Zentrums von der Materialmitte.
 *
 * Die universelle Formel (verifiziert gegen den offiziellen Lamello-Rechner):
 *
 *   Gehrung:        X1 = (M/2 + s·cos(α/2)) / sin(α/2)
 *                   X2 = (M/2 - s·cos(α/2)) / sin(α/2)
 *
 *   Stoss/T-Verb:   X1 = (M/2 + s·cos(α)) / sin(α)
 *                   X2 = (M/2 - s·cos(α)) / sin(α)
 *
 *   Mittelwand:     wie Stoss für α≤90°, gespiegelt (X1↔X2) für α>90°
 *
 * Verfügbare Technologien:
 * - CNC: Direktfräsung per CNC-Maschine
 * - ZETA-0: Zeta P2 ohne Spacer
 * - ZETA-2: Zeta P2 mit 2mm Spacer
 * - ZETA-4: Zeta P2 mit 4mm Spacer
 */

enum class JoiningSituation(val code: String, val labelDe: String, val labelEn: String) {
    S("S", "Stoss", "Butt joint"),
    G("G", "Gehrung", "Miter joint"),
    M("M", "Mittelwand", "Middle panel"),
    T("T", "T-Verbindung", "T-joint")
}

enum class LamelloTechnology(
    val labelDe: String,
    val labelEn: String,
    val shortLabel: String,
    val urlCode: String
) {
    CNC("CNC", "CNC", "CNC", "CNC"),
    ZETA_0("Zeta P2", "Zeta P2", "Zeta", "ZETA-0"),
    ZETA_2("Zeta P2 + 2mm", "Zeta P2 + 2mm", "+2mm", "ZETA-2"),
    ZETA_4("Zeta P2 + 4mm", "Zeta P2 + 4mm", "+4mm", "ZETA-4")
}

/**
 * Profil eines Connectors für eine bestimmte Technologie.
 * @param technology Bearbeitungstechnologie
 * @param slotOffset s-Wert in mm (Verschiebung des Connector-Zentrums von der Materialmitte)
 * @param tolerance Toleranz in mm (±)
 */
data class TechProfile(
    val technology: LamelloTechnology,
    val slotOffset: Double,
    val tolerance: Double = 1.1
)

/**
 * Spezifikation eines Lamello-Connectors.
 * @param name Anzeigename (z.B. "Clamex P-14")
 * @param connectorCode URL-Parameter für den Lamello-API-Endpoint (z.B. "P14/P14")
 * @param profiles Verfügbare Technologie-Profile mit ihren Slot-Offsets
 */
data class ConnectorSpec(
    val name: String,
    val connectorCode: String,
    val profiles: List<TechProfile>
)

/**
 * Alle verfügbaren Lamello-Connectors mit ihren Slot-Offsets.
 *
 * Slot-Offset s reverse-engineered aus dem offiziellen Lamello-Rechner:
 * - P-14 Klasse (CNC): s = 6.46mm
 * - P-10 Klasse (CNC): s = 4.47mm
 * - ZETA-0: s ≈ 5.0mm (geschätzt)
 * - ZETA-2: s ≈ 3.5mm (geschätzt)
 * - ZETA-4: s ≈ 6.76mm (geschätzt)
 *
 * Verifiziert bei Gehrung 45°/90°/120°/150°/180° und Stoss 45°/70°/90°/120°.
 */
object LamelloData {

    private const val BASE_URL = "https://configurator.lamello.com/lamellocalculator/Lamello/SituationImage"

    // Slot-Offset Konstanten (verifiziert)
    private const val S_P14_CNC = 6.46
    private const val S_P10_CNC = 4.47
    // Zeta-Varianten (aus 90°-Daten abgeleitet: s = (X1·sin(45°) - M/2) / cos(45°))
    private const val S_P14_ZETA_0 = 5.0   // Zeta ohne Spacer
    private const val S_P10_ZETA_0 = 5.0
    private const val S_P10_ZETA_2 = 3.96  // aus X1=17.4 bei G90: (17.4·0.707-9.5)/0.707
    private const val S_P14_ZETA_4 = 6.76  // aus X1=20.2 bei G90: (20.2·0.707-9.5)/0.707
    private const val S_P10_ZETA_4 = 6.76

    val connectors = listOf(
        ConnectorSpec(
            name = "Clamex P-14",
            connectorCode = "P14/P14",
            profiles = listOf(
                TechProfile(LamelloTechnology.CNC, S_P14_CNC),
                TechProfile(LamelloTechnology.ZETA_4, S_P14_ZETA_4)
            )
        ),
        ConnectorSpec(
            name = "Clamex P-14 CNC",
            connectorCode = "P14-CNC",
            profiles = listOf(
                TechProfile(LamelloTechnology.CNC, S_P14_CNC)
            )
        ),
        ConnectorSpec(
            name = "Clamex P-14 Flexus",
            connectorCode = "P14-FLEXUS",
            profiles = listOf(
                TechProfile(LamelloTechnology.CNC, S_P14_CNC),
                TechProfile(LamelloTechnology.ZETA_4, S_P14_ZETA_4)
            )
        ),
        ConnectorSpec(
            name = "Clamex P Medius 14/10",
            connectorCode = "P14/M10",
            profiles = listOf(
                TechProfile(LamelloTechnology.CNC, S_P14_CNC),
                TechProfile(LamelloTechnology.ZETA_4, S_P14_ZETA_4)
            )
        ),
        ConnectorSpec(
            name = "Clamex P-10",
            connectorCode = "P10/P10",
            profiles = listOf(
                TechProfile(LamelloTechnology.CNC, S_P10_CNC),
                TechProfile(LamelloTechnology.ZETA_2, S_P10_ZETA_2),
                TechProfile(LamelloTechnology.ZETA_4, S_P10_ZETA_4)
            )
        ),
        ConnectorSpec(
            name = "Tenso P-14",
            connectorCode = "T14/T14",
            profiles = listOf(
                TechProfile(LamelloTechnology.CNC, S_P14_CNC),
                TechProfile(LamelloTechnology.ZETA_4, S_P14_ZETA_4)
            )
        ),
        ConnectorSpec(
            name = "Tenso P-10",
            connectorCode = "T10/T10",
            profiles = listOf(
                TechProfile(LamelloTechnology.CNC, S_P10_CNC),
                TechProfile(LamelloTechnology.ZETA_2, S_P10_ZETA_2),
                TechProfile(LamelloTechnology.ZETA_4, S_P10_ZETA_4)
            )
        )
    )

    /** Alle verwendeten Technologien (für Tabellen-Header) */
    val allTechnologies = listOf(
        LamelloTechnology.CNC,
        LamelloTechnology.ZETA_0,
        LamelloTechnology.ZETA_2,
        LamelloTechnology.ZETA_4
    )

    /**
     * Baut die URL für das Lamello SituationImage zusammen.
     */
    fun buildImageUrl(
        m1: Double,
        m2: Double,
        angle: Double,
        situationCode: String,
        technologyCode: String,
        connectorCode: String
    ): String {
        return "$BASE_URL?Material1=${m1.toInt()}&Material2=${m2.toInt()}" +
                "&Angle=${angle.toInt()}&Unit=mm" +
                "&SituationCode=$situationCode" +
                "&LanguageCode=de" +
                "&Technologie=$technologyCode" +
                "&Connector=$connectorCode"
    }
}
