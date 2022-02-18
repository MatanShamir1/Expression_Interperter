import java.util.List;
import java.util.Map;

/**
 * class not: the class extends UnaryExpression. it is an expression holding
 * an expression, within a "not" sign. the class supports
 * all the methods from the expression interface, such as assign and evaluation,
 * and more methods relevant only to binary expressions, some of the methods are
 * written in the abstract class UnaryExpression, and this class inherits them.
 */
public class Not extends UnaryExpression {
    /**
     * class constructor.
     *
     * @param expression the expression that is placed within the "not".
     */
    public Not(Expression expression) {
        //call father "unary expression" with the expression.
        super(expression);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        /*
        first check for each variable in the expression that it exists in the map. if it does not,
        an exception is thrown, because no evaluation is allowed without knowing the values.
         */
        List<String> listVars = super.getVariables();
        for (String var : listVars) {
            if (!assignment.containsKey(var)) {
                throw new Exception("value not in map");
            }
        }
        //recursively return the negation of the evaluation of the inner expression.
        return !super.getExp().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        /*
        first check that the list of variables in the expression is empty before sending for
        evaluation without a map, because no evaluation is allowed without specific values.
         */
        if (!super.getExp().getVariables().isEmpty()) {
            throw new Exception("no map");
        }
        //recursively return the negation of the evaluation of the inner expression.
        return !super.getExp().evaluate();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        /*
        recursively call "assign" on the inner expression, because assigning is possible on
        atomic expressions solely.
         */
        return new Not(super.getExp().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        //create a new nand expression with the expression in both parts.
        return new Nand(super.getExp().nandify(), super.getExp().nandify());
    }

    @Override
    public Expression norify() {
        //create a new nand expression with the expression on both parts.
        return new Nor(super.getExp().norify(), super.getExp().norify());
    }

    @Override
    public String toString() {
        /*
        the "toString" method simply puts "~" before the expression, and recursively
        calls to string on its inner expression.
         */
        return "~(" + super.getExp().toString() + ")";
    }

    @Override
    public Expression simplify() {
        //first, simplify the inner expression.
        Expression simplified = super.getExp().simplify();
        //if the simplified expression is true, return false.
        if (simplified.equals(new Val(true))) {
            return new Val(false);
            //otherwise, if the inner expression is true, return false.
        } else if (simplified.equals(new Val(false))) {
            return new Val(true);
        }
        //if no simplification is possible, return the inner simplified expression with a "not".
        return new Not(simplified);
    }
}
