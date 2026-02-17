package model

enum class Position(val englishName: String, val russianName: String) {
    GOALKEEPER("GOALKEEPER", "Вратарь"),
    DEFENDER("DEFENDER", "Защитник"),
    MIDFIELD("MIDFIELD", "Полузащитник"),
    FORWARD("FORWARD", "Нападающий");

    companion object {
        fun fromString(name: String): Position? =
            Position.entries.find { it.englishName == name.uppercase() }

        fun getRussianName(englishName: String): String =
            fromString(englishName)?.russianName ?: englishName
    }
}