import dpll_tree.DpllTree
import dpll_tree.FormulaNode
import model.Formula
import model.Literal

fun main(args: Array<String>) {

    println("--- START ---")
    println("CNF formula: //Example: p,-q;-p,q,r;q,-r")
    val formula = Formula(readLine()!!)
    println("The given formula: " + formula)

    val solutions: MutableList<String> = mutableListOf()

    var currentNode = FormulaNode(formula)
    val dpllTree = DpllTree(currentNode)
    val unProcessedNodes = mutableListOf<FormulaNode>()

    while(true) {
        if(currentNode.formula.isEmpty()) {
            println("BRANCH LITERALS: " + currentNode)
            solutions.add(dpllTree.getLogicalValues(currentNode))
            if(unProcessedNodes.isEmpty()) {
                break
            } else {
                currentNode = unProcessedNodes.removeAt(0)
                if(currentNode.formula.isEmpty()) {
                    continue
                }
            }
        }

        if(currentNode.formula.haveEmptyClause()) {
            if(unProcessedNodes.isEmpty()) {
                break
            } else {
                currentNode = unProcessedNodes.removeAt(0)
                if(currentNode.formula.haveEmptyClause()) {
                    continue
                }
            }
        }

        val unitClause = currentNode.formula.getUnitClause()
        val uniqueLiteral = currentNode.formula.getUniqueLiteral()
        if(unitClause != null) {
            println("UNIT CLAUSE")
            currentNode = DpllTree.generateNewNode(
                currentNode = currentNode,
                literal = unitClause.getLiteralFromUnitClause()
            )
        } else if(uniqueLiteral != null) {
            println("UNIQUE LITERAL")
            currentNode = DpllTree.generateNewNode(
                currentNode = currentNode,
                literal = uniqueLiteral
            )
        } else {
            println("NATIVE")
            val randLiteral = currentNode.formula.getDistinctLiterals().first()
            DpllTree.generateNewNode(
                currentNode = currentNode,
                literal = randLiteral
            )
            unProcessedNodes.add(
                DpllTree.generateNewNode(
                    currentNode = currentNode,
                    literal = randLiteral.getNegatedLiteral()
                )
            )
            currentNode = currentNode.getLeftChild() ?: break

        }
    }

    if(solutions.isNotEmpty()) {
        println("SATISFIABLE")
        println("SOLUTIONS: $solutions")
    } else {
        println("UNSATISFIABLE")
    }
}