import java.util.List;

/**
 * class BinaryExpression is an abstract class holding partial method implementations
 * for binary expressions. it extends BaseExpression so it inherits its method
 * implementations as well. among the methods implemented in this class, which are
 * relevant to all binary expressions nevertheless, are the "toString" and "getVariables"
 * methods given from the expression interface.
 */
public abstract class BinaryExpression extends BaseExpression {
    //the logical sign of the binary expression for further string translation.
    private String sign;
    //the two expression held together by the binary expression.
    private Expression exp1;
    private Expression exp2;

    /**
     * class constructor.
     * @param e1 the first(left) expression of the binary expression.
     * @param e2 the second(right) expression of the binary expression.
     * @param sign the logical sign of the the expression.
     */
    public BinaryExpression(Expression e1, Expression e2, String sign) {
        this.exp1 = e1;
        this.exp2 = e2;
        this.sign = sign;
    }

    @Override
    public List<String> getVariables() {
        //recursively get lists of variables from left and right expressions.
        List<String> leftList = this.exp1.getVariables();
        List<String> rightList = this.exp2.getVariables();
        //combine the lists by adding all of the right to the left.
        leftList.addAll(rightList);
        //remove duplicates from the list.
        leftList = super.removeDuplicates(leftList);
        //return the left.
        return leftList;
    }

    /**
     * class getter.
     * @return the first expression.
     */
    protected Expression getExp1() {
        return exp1;
    }

    /**
     * class getter.
     * @return the second expression.
     */
    protected Expression getExp2() {
        return exp2;
    }

    @Override
    public String toString() {
        //combine a new string combined by the two expressions and the sign holding them together.
        return "(" + this.exp1.toString() + this.sign + this.exp2.toString() + ")";
    }
}
