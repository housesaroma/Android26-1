package resolver

import model.Player
import model.Position
import model.Team

class ResolverImpl(
    private val players: List<Player>,
    private val teams: List<Team>
) : IResolver {

    override fun getCountWithoutAgency(): Int =
        players.count { it.agency == null }

    override fun getBestScorerDefender(): Pair<String, Int> =
        players.filter { it.position == "DEFENDER" }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("Нет защитников" to 0)

    override fun getTheExpensiveGermanPlayerPosition(): String =
        players.filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }
            ?.position
            ?.let { Position.getRussianName(it) }
            ?: "Нет немецких игроков"

    override fun getTheRudestTeam(): Team =
        players.groupBy { it.team }
            .maxByOrNull { (_, teamPlayers) ->
                teamPlayers.map { it.redCards }.average()
            }?.let { (teamName, _) ->
                teams.find { it.name == teamName }
            } ?: error("Нет команд")

    override fun getTop10TeamsByTotalValue(): List<Pair<String, Double>> =
        players.groupBy { it.team }
            .map { (teamName, teamPlayers) ->
                teamName to teamPlayers.sumOf { it.transferCost }.toDouble()
            }
            .sortedByDescending { it.second }
            .take(10)
}