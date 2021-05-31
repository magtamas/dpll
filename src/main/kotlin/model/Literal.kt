package model

class Literal(var token: String) {

    fun isNegated(): Boolean {
        return token.contains("-")
    }

    fun getRawToken(): String {
        return if(this.isNegated()) {
            token.split("-")[1]
        } else {
            token
        }
    }

    fun getNegatedLiteral(): Literal {
        return if(this.isNegated()) {
            Literal(this.getRawToken())
        } else {
            Literal("-" + this.getRawToken())
        }
    }

    override fun equals(other: Any?): Boolean {
        return this.token == (other as? Literal)?.token
    }

    override fun hashCode(): Int {
        return token.hashCode()
    }

    override fun toString(): String {
        return if(isNegated()) {
            "\u00AC${token.split("-")[1]}"
        } else {
            token
        }
    }
}