package resolver

import model.Team

interface IResolver {

    // Выведите количество игроков, интересы которых не представляет агенство.
    fun getCountWithoutAgency(): Int

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    fun getBestScorerDefender(): Pair<String, Int>

    // Выведите русское название позиции самого дорогого немецкого игрока.
    fun getTheExpensiveGermanPlayerPosition(): String

    // Выберите команду с наибольшим числом удалений на одного игрока.
    fun getTheRudestTeam(): Team

    // Возвращает топ-10 команд по суммарной трансферной стоимости
    fun getTop10TeamsByTotalValue(): List<Pair<String, Double>>
}