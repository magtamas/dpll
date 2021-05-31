package model

import util.Utils

class Clause(var listOfLiteral: List<Literal>) {

    constructor(rawClause: String) : this(
        Utils.buildLiteralList(rawClause)
    )

    fun isEmpty(): Boolean {
        return listOfLiteral.isEmpty()
    }

    fun isUnitClause(): Boolean {
        return listOfLiteral.size == 1
    }

    fun getLiteralFromUnitClause(): Literal {
        return this.listOfLiteral.first()
    }

    fun removeLiteral(literal: Literal) {
        val newLiteralList = listOfLiteral.toMutableList()
        newLiteralList.remove(literal)
        this.listOfLiteral = newLiteralList
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("(")
        for((index, literal) in listOfLiteral.withIndex()) {
            if(index != listOfLiteral.lastIndex) {
                sb.append("$literal v ")
            } else {
                sb.append(literal.toString())
            }
        }
        sb.append(")")
        return sb.toString()
    }
}