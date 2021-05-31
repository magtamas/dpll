package util

import model.Clause
import model.Literal

object Utils {

    fun buildClauseList(rawFormula: String): List<Clause> {
        val tempList = mutableListOf<Clause>()
        for(rawClause in rawFormula.split(";")) {
            tempList.add(Clause(rawClause))
        }

        return tempList
    }

    fun buildLiteralList(rawClause: String): List<Literal> {
        val tempList = mutableListOf<Literal>()
        if(rawClause.isEmpty) {
            return listOf()
        }
        for(rawLiteral in rawClause.split(",")) {
            tempList.add(Literal(rawLiteral))
        }
        return tempList
    }
}