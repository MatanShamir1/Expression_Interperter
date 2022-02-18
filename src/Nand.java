import java.util.List;
import java.util.Map;

/**
 * class Nand: the class extends BinaryExpression. it is an expression holding two
 * different expressions together, linked by the logical sign 'A'. the class supports
 * all the methods from the expression interface, such as assign and evaluation,
 * and more methods relevant only to binary expressions, some of the methods are
 * written in the abstract class BinaryExpression, and this class inherits them.
 */
public class Nand extends BinaryExpression {
    /**
     * class constructor.
     *
     * @param e1 the first expression.
     * @param e2 the second expression.
     */
    public Nand(Expression e1, Expression e2) {
        //call father's constructor with the logical sign for further to string conversion.
        super(e1, e2, " A ");
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
        return !(super.getExp1().evaluate(assignment) && super.getExp2().evaluate(assignment));
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
        return !(super.getExp1().evaluate() && super.getExp2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {
        //recursively call 'assign' on both sides because assigning is only allowed on basic var expressions.
        return new Nand(super.getExp1().assign(var, expression), super.getExp2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        //this value is nand, so only recursively nandify both inner expressions.
        return new Nand(super.getExp1().nandify(), super.getExp2().nandify());
    }

    @Override
    public Expression norify() {
        //using delegation: nand does nor twice as in and.
        And delegator = new And(super.getExp1(), super.getExp2());
        //recursively norify both inner expressions.
        return new Nor(delegator.norify(), delegator.norify());
    }

    @Override
    public Expression simplify() {
        //first, recursively simplify both expressions combining the "nand" expression.
        Expression simplified1 = super.getExp1().simplify();
        Expression simplified2 = super.getExp2().simplify();
        //in case that one of the expressions is val true, return the negation of the other.
        if (simplified2.equals(new Val(true))) {
            return new Not(simplified1).simplify();
        } else if (simplified1.equals(new Val(true))) {
            return new Not(simplified2).simplify();
            //otherwise, if one of them is false, return true nonetheless.
        } else if ((simplified2.equals(new Val(false)))
                || (simplified1.equals(new Val(false)))) {
            return new Val(true);
            //if inner expressions are equal, return the negation of one of them.
        } else if (simplified1.equals(simplified2)) {
            return new Not(simplified1).simplify();
            //if no simplification is possible, return nand with the two inner simplified expressions.
        } else {
            return new Nand(simplified1, simplified2);
        }
    }
}
