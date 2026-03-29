package com.brunner.lignacalc.data

import com.brunner.lignacalc.util.Calculations

data class UnitDef(
    val id: String,
    val displayName: String
)

data class ConversionResult(
    val unit: UnitDef,
    val value: Double
)

object UnitConversionData {

    enum class Category { LENGTH, AREA }

    private val lengthUnits = listOf(
        UnitDef("mm", "Millimeter"),
        UnitDef("cm", "Zentimeter"),
        UnitDef("meter", "Meter"),
        UnitDef("inch", "Zoll"),
        UnitDef("feet", "Fuss"),
        UnitDef("yd", "Yard"),
    )

    private val areaUnits = listOf(
        UnitDef("cm2", "cm\u00B2"),
        UnitDef("m2", "m\u00B2"),
        UnitDef("sqin", "sq in"),
        UnitDef("sqft", "sq ft"),
        UnitDef("sqyd", "sq yd"),
    )

    // Conversion factors: source → target
    private val lengthFactors = mapOf(
        "mm" to mapOf("cm" to 0.1, "meter" to 0.001, "inch" to 0.0393701, "feet" to 0.00328084, "yd" to 0.00109361),
        "cm" to mapOf("mm" to 10.0, "meter" to 0.01, "inch" to 0.393701, "feet" to 0.0328084, "yd" to 0.0109361),
        "meter" to mapOf("mm" to 1000.0, "cm" to 100.0, "inch" to 39.3701, "feet" to 3.28084, "yd" to 1.09361),
        "inch" to mapOf("mm" to 25.4, "cm" to 2.54, "meter" to 0.0254, "feet" to 0.0833333, "yd" to 0.0277778),
        "feet" to mapOf("mm" to 304.8, "cm" to 30.48, "meter" to 0.3048, "inch" to 12.0, "yd" to 0.333333),
        "yd" to mapOf("mm" to 914.4, "cm" to 91.44, "meter" to 0.9144, "inch" to 36.0, "feet" to 3.0),
    )

    private val areaFactors = mapOf(
        "cm2" to mapOf("m2" to 0.0001, "sqin" to 0.155, "sqft" to 0.00107639, "sqyd" to 0.000119599),
        "m2" to mapOf("cm2" to 10000.0, "sqin" to 1550.0, "sqft" to 10.7639, "sqyd" to 1.19599),
        "sqin" to mapOf("cm2" to 6.4516, "m2" to 0.00064516, "sqft" to 0.00694444, "sqyd" to 0.000771605),
        "sqft" to mapOf("cm2" to 929.03, "m2" to 0.092903, "sqin" to 144.0, "sqyd" to 0.111111),
        "sqyd" to mapOf("cm2" to 8361.27, "m2" to 0.836127, "sqin" to 1296.0, "sqft" to 9.0),
    )

    fun getUnits(category: Category): List<UnitDef> = when (category) {
        Category.LENGTH -> lengthUnits
        Category.AREA -> areaUnits
    }

    fun convertAll(category: Category, sourceId: String, value: Double): List<ConversionResult> {
        val factors = when (category) {
            Category.LENGTH -> lengthFactors
            Category.AREA -> areaFactors
        }
        val units = getUnits(category)
        val sourceFactors = factors[sourceId] ?: return emptyList()

        return units
            .filter { it.id != sourceId }
            .map { targetUnit ->
                val factor = sourceFactors[targetUnit.id] ?: 1.0
                ConversionResult(targetUnit, Calculations.convertUnit(value, factor))
            }
    }
}
