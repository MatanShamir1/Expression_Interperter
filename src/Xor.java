import java.util.List;
import java.util.Map;

/**
 * class Xor: the class extends BinaryExpression. it is an expression holding two
 * different expressions together, linked by the logical sign "exclusive Or". the class supports
 * all the methods from the expression interface, such as assign and evaluation,
 * and more methods relevant only to binary expressions, some of the methods are
 * written in the abstract class BinaryExpression, and this class inherits them.
 */
public class Xor extends BinaryExpression {

    /**
     * class constructor.
     *
     * @param e1 the first expression.
     * @param e2 the second expression.
     */
    public Xor(Expression e1, Expression e2) {
        //call father's constructor with the logical sign for further to string conversion.
        super(e1, e2, " ^ ");
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
        //in case all is valid, recursively call evaluation on both expressions with logical "exclusive or".
        return ((!super.getExp1().evaluate(assignment) && super.getExp2().evaluate(assignment))
                || (super.getExp1().evaluate(assignment) && !super.getExp2().evaluate(assignment)));
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
        //use delegation of Xor to evaluate this expression, because Xnor is not Xor apparently.
        return ((!super.getExp1().evaluate() && super.getExp2().evaluate())
                || (super.getExp1().evaluate() && !super.getExp2().evaluate()));
    }

    @Override
    public Expression assign(String var, Expression expression) {
        //recursively call 'assign' on both sides because assigning is only allowed on basic var expressions.
        return new Xor(super.getExp1().assign(var, expression), super.getExp2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        //use the logic to tranlate a Xor expression into a nand expression:
        return new Nand(new Nand(super.getExp1().nandify(),
                new Nand(super.getExp1().nandify(), super.getExp2().nandify())),
                new Nand(super.getExp2().nandify(), new Nand(super.getExp1().nandify(),
                        super.getExp2().nandify())));
    }

    @Override
    public Expression norify() {
        //using delegation: xor does nor twice as in and.
        And delegator = new And(super.getExp1(), super.getExp2());
        //recursively call norify on both expressions using the translation logic.
        return new Nor(delegator.norify(), new Nor(super.getExp1().norify(), super.getExp2().norify()));
    }

    @Override
    public Expression simplify() {
        //first, simplify the expressions held within the Xor expression.
        Expression simplified1 = super.getExp1().simplify();
        Expression simplified2 = super.getExp2().simplify();
        //if one of the expression is value true, return the negation of the other.
        if (simplified2.equals(new Val(true))) {
            return new Not(simplified1).simplify();
        } else if (simplified1.equals(new Val(true))) {
            return new Not(simplified2).simplify();
            //otherwise, if one of them is false, return the other, simplified.
        } else if (simplified2.equals(new Val(false))) {
            return simplified1;
        } else if (simplified1.equals(new Val(false))) {
            return simplified2;
            //otherwise, if the inner expressions are equal, return false nonetheless.
        } else if (simplified1.equals(simplified2)) {
            return new Val(false);
            //if no simplification is possible, return Xor with the inner simplified expressions.
        } else {
            return new Xor(simplified1, simplified2);
        }
    }
}
