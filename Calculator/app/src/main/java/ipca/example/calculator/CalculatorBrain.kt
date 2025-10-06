package ipca.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class CalculatorBrain {

    var operand = 0.0

    enum class Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE;

        companion object {

            fun parse(value: String): Operation {
                return when (value) {
                    "+" -> ADD
                    "-" -> SUBTRACT
                    "×" -> MULTIPLY
                    "÷" -> DIVIDE
                    else -> throw IllegalArgumentException("Invalid operation")

                }
            }
        }
    }

    fun doOperation(newOperand: Double, newOperation: Operation){
        var result = newOperand

        when(newOperation){
            Operation.ADD -> result = operand + newOperand
            Operation.SUBTRACT ->result = operand - newOperand
            Operation.MULTIPLY ->result = operand * newOperand
            Operation.DIVIDE -> result = operand / newOperand
        }

        operand = result
    }

}