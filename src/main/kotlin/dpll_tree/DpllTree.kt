package dpll_tree

import model.Formula
import model.Literal
import java.lang.StringBuilder

data class DpllTree(var rootNode: FormulaNode) {


    fun getLogicalValues(leafNode: FormulaNode): String {
        val sb = StringBuilder()

        val variables = this.rootNode.formula.getDistinctLiterals().map {
            it.token
        }.toMutableList()

        var currentNode: FormulaNode = leafNode
        var currentLiteral = currentNode.getLiteral()

        while(true) {
            if(currentNode.getParent() == null) {

                for(variable in variables) {
                    sb.append("$variable = true/false ")
                }

                return sb.toString()
            }
            if(currentLiteral?.isNegated() == true) {
                currentLiteral = currentLiteral.getNegatedLiteral()
                sb.append("${currentLiteral.token} = false ")
            } else if(currentLiteral?.isNegated() == false) {
                sb.append("${currentLiteral.token} = true ")
            }
            variables.remove(currentLiteral?.token)
            currentNode = currentNode.getParent()!!
            currentLiteral = currentNode.getLiteral()
        }
    }

    override fun toString(): String {
        return rootNode.toString()
    }

    companion object {
        fun generateNewNode(
            currentNode: FormulaNode,
            literal: Literal
        ): FormulaNode {
            val newFormula = Formula(currentNode.formula.getListOfClause())
            newFormula.modifyWithLiteral(literal)

            val newNode = FormulaNode(
                formula = newFormula,
                literal = literal,
                parent = currentNode
            )

            currentNode.addChild(newNode)

            return newNode
        }
    }
}