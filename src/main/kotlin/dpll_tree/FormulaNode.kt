package dpll_tree

import model.Formula
import model.Literal

class FormulaNode(var formula: Formula) {
    private var leftChild: FormulaNode? = null
    private var rightChild: FormulaNode? = null
    private var parent: FormulaNode? = null
    private var literal: Literal? = null

    constructor(
        formula: Formula,
        literal: Literal,
        parent: FormulaNode
    ): this(formula) {
        this.literal = literal
        this.parent = parent
    }

    fun getLeftChild(): FormulaNode? {
        return this.leftChild
    }

    fun getRightChild(): FormulaNode? {
        return this.rightChild
    }


    fun addChild(formulaNode: FormulaNode) {
        when {
            leftChild == null -> {
                leftChild = formulaNode
            }
            rightChild == null -> {
                rightChild = formulaNode
            }
            else -> {
                throw UnsupportedOperationException(
                    "The node cannot have more children"
                )
            }
        }
    }

    fun getParent(): FormulaNode? {
        return this.parent
    }

    fun getLiteral(): Literal? {
        return this.literal
    }

    override fun toString(): String {
        //return "\nNode:\nisProcessed: $isProcessed \nFormula: $formula\nLiteral: $literal\nParent: $parent"
        return "----Literal: $literal $parent"
    }
}