import java.util.List;
import java.util.Map;

/**
 * the Expression interface is the most basic interface, holding the must-have
 * methods for every sub-expression in this program. every other expression
 * implements this interface.
 */
public interface Expression {

    /**
     * evaluate method provides evaluation to the starting expression by recursively
     * using different evaluate methods on different sub-expressions combining
     * the whole expression.
     *
     * @param assignment a map containing all values for vars in the expression.
     * @return true for a true expression, false otherwise.
     * @throws Exception in case that a certain var does not appear in the map,
     *                   thus not able to know accurate evaluation.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * evaluate method provides evaluation to the starting expression by recursively
     * using different evaluate methods on different sub-expressions combining
     * the whole expression. this method is meant for expressions without vars.
     *
     * @return true for a true expression, false otherwise.
     * @throws Exception in case that the expression includes any vars, an exception
     *                   is to be thrown for no map was sent to know how to evaluate that var.
     */
    Boolean evaluate() throws Exception;

    /**
     * this method returns the variables included in the main expression.
     *
     * @return a list of the variables that the expression contains.
     */
    List<String> getVariables();

    /**
     * @return a readable string of the expression.
     */
    String toString();

    /**
     * @param var        the variable with which the expression should be replaced.
     * @param expression the expression to replace the required variable.
     * @return a new expression after the expression replaced the var.
     */
    Expression assign(String var, Expression expression);

    /**
     * replace every expression logic connection with nand expression.
     *
     * @return a new expression combined only from nand expressions.
     */
    Expression nandify();

    /**
     * replace every expression logic connection with nand expression.
     *
     * @return a new expression combined only from nand expressions.
     */
    Expression norify();

    /**
     * simplify the expression tree recursively by the basic rules of discrete mathematics.
     *
     * @return a new simplified expression.
     */
    Expression simplify();

    /**
     * equals is a new implementation for equals in the expression program.
     *
     * @param expression the expression to check if this is equal to.
     * @return true for equal expressions, false otherwise.
     */
    boolean equals(Expression expression);
}