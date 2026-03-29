package com.brunner.lignacalc.data

data class CuttingSpeedMaterial(
    val nameKey: String,
    val nameDe: String,
    val fraesenMin: Double,
    val fraesenMax: Double,
    val saegenMin: Double,
    val saegenMax: Double
)

object CuttingSpeedData {
    val materials = listOf(
        CuttingSpeedMaterial("weichhoelzer", "Weichhölzer", 45.0, 70.0, 60.0, 100.0),
        CuttingSpeedMaterial("harthoelzer", "Harthölzer", 45.0, 70.0, 60.0, 100.0),
        CuttingSpeedMaterial("spanplatten", "Spanplatten", 50.0, 70.0, 50.0, 80.0),
        CuttingSpeedMaterial("tischlerplatten", "Tischlerplatten", 50.0, 70.0, 50.0, 80.0),
        CuttingSpeedMaterial("hartfaserplatten", "Hartfaserplatten", 40.0, 60.0, 50.0, 80.0),
        CuttingSpeedMaterial("beschichtete_platten", "Beschichtete Platten", 50.0, 70.0, 60.0, 80.0),
        CuttingSpeedMaterial("mineralwerkstoffe", "Mineralwerkstoffe", 50.0, 70.0, 40.0, 55.0),
        CuttingSpeedMaterial("kompaktschichtstoff", "Kompaktschichtstoffplatten", 30.0, 50.0, 50.0, 80.0),
        CuttingSpeedMaterial("gipsfaser", "Gipsfaser, Gipskarton", 25.0, 40.0, 40.0, 65.0),
        CuttingSpeedMaterial("zementfaserplatten", "Zementfaserplatten", 35.0, 35.0, 35.0, 35.0),
        CuttingSpeedMaterial("alu_profile", "Aluminiumprofile", 25.0, 50.0, 55.0, 70.0),
        CuttingSpeedMaterial("alu_vollmaterial", "Aluminium Vollmaterial", 30.0, 50.0, 50.0, 70.0),
        CuttingSpeedMaterial("alu_guss", "Aluminium Guss", 10.0, 25.0, 20.0, 30.0),
    )
}
