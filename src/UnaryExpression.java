import java.util.List;

/**
 * class UnaryExpression is an abstract class holding partial method implementations
 * for unary expressions. it extends BaseExpression so it inherits its method
 * implementations as well. among the methods implemented in this class, which are
 * relevant to all unary expressions nevertheless is the "getVariables"
 * method given from the expression interface.
 */
public abstract class UnaryExpression extends BaseExpression {
    //the expression held within the unary expression.
    private Expression exp;

    /**
     * class constructor.
     * @param e the expression to assign into the unary expression.
     */
    public UnaryExpression(Expression e) {
        this.exp = e;
    }

    @Override
    public List<String> getVariables() {
        //return the list held within this expression recursively.
        return this.exp.getVariables();
    }

    /**
     * class getter.
     * @return the expression held within the unary expression.
     */
    protected Expression getExp() {
        return this.exp;
    }
}