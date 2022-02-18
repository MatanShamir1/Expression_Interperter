import java.util.List;
import java.util.Map;

/**
 * class nor: the class extends BinaryExpression. it is an expression holding two
 * different expressions together, linked by the logical sign "nor or". the class supports
 * all the methods from the expression interface, such as assign and evaluation,
 * and more methods relevant only to binary expressions, some of the methods are
 * written in the abstract class BinaryExpression, and this class inherits them.
 */
public class Nor extends BinaryExpression {
    /**
     * class constructor.
     *
     * @param e1 the first expression.
     * @param e2 the second expression.
     */
    public Nor(Expression e1, Expression e2) {
        //call father's constructor with the logical sign for further to string conversion.
        super(e1, e2, " V ");
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
        //in case all is valid, recursively call evaluation on both expressions with logic "not and".
        return !(super.getExp1().evaluate(assignment) || super.getExp2().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {
        /*
        first check that the list of variables in the expression is empty before sending for
        evaluation without a map, because no evaluation is allowed without specific values.
         */
        if (!super.getExp1().getVariables().isEmpty() || !super.getExp2().getVariables().isEmpty()) {
            throw new Exception("no map");
        }
        // in case there are actually no vars, continue evaluation recursively.
        return !(super.getExp1().evaluate() || super.getExp2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {
        //recursively call 'assign' on both sides because assigning is only allowed on basic var expressions.
        return new Nor(super.getExp1().assign(var, expression), super.getExp2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        //using delegation: nor does nand twice as in or.
        Or delegator = new Or(super.getExp1(), super.getExp2());
        //recursively nandify both parts.
        return new Nand(delegator.nandify(), delegator.nandify());
    }

    @Override
    public Expression norify() {
        //this expression is already nor, so recursively norify both expressions.
        return new Nor(super.getExp1().norify(), super.getExp2().norify());
    }

    @Override
    public Expression simplify() {
        //first, recursively simplify both expressions combining the "nor" expression.
        Expression simplified1 = super.getExp1().simplify();
        Expression simplified2 = super.getExp2().simplify();
        //if one of the expressions is val true, return false nonetheless.
        if ((simplified2.equals(new Val(true)))
                || (simplified1.equals(new Val(true)))) {
            return new Val(false);
            //otherwise, if one of them is false, return the negation of the other.
        } else if (simplified2.equals(new Val(false))) {
            return new Not(simplified1).simplify();
        } else if (simplified1.equals(new Val(false))) {
            return new Not(simplified2).simplify();
            //if the inner expressions are equal, return the negation of one of them.
        } else if (simplified1.equals(simplified2)) {
            return new Not(simplified1).simplify();
            //if no simplification is possible, return this nor with simplified inner expressions.
        } else {
            return new Nor(simplified1, simplified2);
        }
    }
}

