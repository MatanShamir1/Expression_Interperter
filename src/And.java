import java.util.List;
import java.util.Map;

/**
 * class And: the class extends BinaryExpression. it is an expression holding two
 * different expressions together, linked by the logical sign '&&'. the class supports
 * all the methods from the expression interface, such as assign and evaluation,
 * and more methods relevant only to binary expressions, some of the methods are
 * written in the abstract class BinaryExpression, and this class inherits them.
 */
public class And extends BinaryExpression {
    /**
     * class constructor.
     *
     * @param e1 the first expression.
     * @param e2 the second expression.
     */
    public And(Expression e1, Expression e2) {
        //call father's constructor with the logical sign for further to string conversion.
        super(e1, e2, " & ");
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
        //in case all is valid, recursively call evaluation on both expressions with logical '&&'.
        return super.getExp1().evaluate(assignment) && super.getExp2().evaluate(assignment);
    }

    @Override
    public Boolean evaluate() throws Exception {
        /*
        first check that the list of variables in the expression is empty before sending for
        evaluation without a map, because no evaluation is allowed without specific values.
         */
        if (!super.getVariables().isEmpty()) {
            throw new Exception("no map");
        }
        // in case there are actually no vars, continue evaluation recursively.
        return super.getExp1().evaluate() && super.getExp2().evaluate();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        //recursively call 'assign' on both sides because assigning is only allowed on basic var expressions.
        return new And(super.getExp1().assign(var, expression), super.getExp2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        //the nand translation from an "and" expression is this:
        return new Nand(new Nand(super.getExp1().nandify(), super.getExp2().nandify()),
                new Nand(super.getExp1().nandify(), super.getExp2().nandify()));
    }

    @Override
    public Expression norify() {
        //the nor translation from an "and" expression is this:
        return new Nor(new Nor(super.getExp1().norify(), super.getExp1().norify()),
                new Nor(super.getExp2().norify(), super.getExp2().norify()));
    }

    @Override
    public Expression simplify() {
        //first, recursively simplify both expressions combining the "and" expression.
        Expression simplified1 = super.getExp1().simplify();
        Expression simplified2 = super.getExp2().simplify();
        //if one of the expression is the value false, return false nonetheless.
        if ((simplified1.equals(new Val(false)))
                || (simplified2.equals(new Val(false)))) {
            return new Val(false);
            //otherwise, if one of the values is true, simply return the other.
        } else if (simplified2.equals(new Val(true))) {
            return simplified1;
        } else if (simplified1.equals(new Val(true))) {
            return simplified2;
            //otherwise, if they are equal, return one of them, in this case the first one.
        } else if (simplified1.equals(simplified2)) {
            return simplified1;
            //if none of the above happens, return "and" with both simplified expressions.
        } else {
            return new And(simplified1, simplified2);
        }
    }
}
