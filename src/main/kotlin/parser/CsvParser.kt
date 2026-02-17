package parser

import model.Player
import model.Team
import java.io.File

private fun Int?.orZero(): Int = this ?: 0

object CsvParser {

    fun parsePlayers(filePath: String): List<Player> {
        return File(filePath)
            .readLines()
            .drop(1)
            .filter { it.isNotBlank() }
            .map { line ->
                val parts = line.split(";").map { it.trim() }
                Player(
                    name = parts[0],
                    team = parts[1],
                    city = parts[2],
                    position = parts[3],
                    nationality = parts[4],
                    agency = parts[5].takeIf { it.isNotBlank() },
                    transferCost = parts[6].toInt(),
                    participations = parts[7].toInt(),
                    goals = parts[8].toInt(),
                    assists = parts[9].toIntOrNull().orZero(),
                    yellowCards = parts[10].toIntOrNull().orZero(),
                    redCards = parts[11].toIntOrNull().orZero(),
                )
            }
    }

    fun parseTeams(players: List<Player>): List<Team> {
        return players
            .groupBy { it.team to it.city }
            .map { (key, _) ->
                val (teamName, city) = key
                Team(
                    name = teamName,
                    city = city
                )
            }
            .distinctBy { it.name }
    }
}