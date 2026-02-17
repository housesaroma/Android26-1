
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import parser.CsvParser
import resolver.ResolverImpl
import java.io.PrintStream

fun main() {
    System.setOut(PrintStream(System.out, true, "UTF-8"))
    val players = CsvParser.parsePlayers("src/main/resources/fakePlayers.csv")
    val teams = CsvParser.parseTeams(players)
    val resolver = ResolverImpl(players, teams)

    println("\n\n=== Результаты анализа ===")
    println("1. Количество игроков, интересы которых не представляет агенство: ${resolver.getCountWithoutAgency()}")

    val (bestScorer, goals) = resolver.getBestScorerDefender()
    println("2. Автора наибольшего числа голов из числа защитников: $bestScorer с $goals голами")

    println("3. Русское название позиции самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")

    val rudestTeam = resolver.getTheRudestTeam()
    println("4. Команда с наибольшим числом удалений на одного игрока: ${rudestTeam.name} (${rudestTeam.city})")

    val top10 = resolver.getTop10TeamsByTotalValue()
    println("\n5. Топ-10 команд по суммарной трансферной стоимости:")
    top10.forEachIndexed { index, (team, value) ->
        println("   ${index + 1}. $team — %,.0f".format(value))
    }

    val dataset = dataFrameOf(
        "team" to top10.map { it.first },
        "cost" to top10.map { it.second / 1_000_000.0 }
    )

    dataset.plot {
        layout.title = "Топ-10 команд по суммарной трансферной стоимости"
        bars {
            x("team") { axis.name = "Команда" }
            y("cost") { axis.name = "Суммарная стоимость (млн)" }
        }
    }.save("top10_teams_transfer_cost.png")
}