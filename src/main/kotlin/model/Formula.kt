package model

import util.Utils

class Formula(private var listOfClause: List<Clause>) {

    private var literalList: List<Literal> = listOf()

    constructor(rawFormula: String): this(
        Utils.buildClauseList(rawFormula)
    )

    fun getListOfClause(): List<Clause> {
        val list = mutableListOf<Clause>()
        for(clause in listOfClause) {
            list.add(
                Clause(listOfLiteral = clause.listOfLiteral)
            )
        }
        return list
    }

    fun isEmpty(): Boolean =
        listOfClause.isEmpty()

    fun getUnitClause(): Clause? =
        listOfClause.find { it.isUnitClause() }

    fun getUniqueLiteral(): Literal? {
        val possibleUniqueLiterals = mutableListOf<Literal>()
        val literals = mutableListOf<Literal>()
        for(clause in listOfClause) {
            for(literal in clause.listOfLiteral) {
                if(
                    (possibleUniqueLiterals.isEmpty() || !possibleUniqueLiterals.contains(literal))
                    && !literals.contains(literal)
                    && !possibleUniqueLiterals.contains(literal.getNegatedLiteral())
                ) {
                    possibleUniqueLiterals.add(literal)
                } else if(possibleUniqueLiterals.contains(literal.getNegatedLiteral())) {
                    possibleUniqueLiterals.remove(literal.getNegatedLiteral())
                }
                literals.add(literal)
            }
        }
        return possibleUniqueLiterals.firstOrNull()
    }

    fun haveEmptyClause(): Boolean {
        listOfClause.find { it.isEmpty() }?.let {
            return true
        }
        return false
    }

    fun modifyWithLiteral(literal: Literal) {
        val newClauseList = listOfClause.toMutableList()
        for(clause in listOfClause) {
            if(clause.listOfLiteral.contains(literal)) {
                newClauseList.remove(clause)
            } else if(clause.listOfLiteral.contains(literal.getNegatedLiteral())) {
                clause.removeLiteral(literal.getNegatedLiteral())
            }
        }
        listOfClause = newClauseList
    }

    fun getDistinctLiterals(): List<Literal> {
        return if(this.literalList.isEmpty()) {
            val list = mutableListOf<Literal>()
            for(clause in listOfClause) {
                for(literal in clause.listOfLiteral) {
                    val tempLiteral = Literal(literal.getRawToken())
                    if(!list.contains(tempLiteral)) {
                        list.add(tempLiteral)
                    }
                }
            }
            list
        } else {
            this.literalList
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        if(this.isEmpty()) {
            return "emptyFormula"
        }
        for((index, clause) in listOfClause.withIndex()) {
            if(index != listOfClause.lastIndex) {
                sb.append("$clause & ")
            } else {
                sb.append(clause.toString())
            }
        }
        return sb.toString()
    }
}