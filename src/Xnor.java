import java.util.List;
import java.util.Map;

/**
 * class Xnor: the class extends BinaryExpression. it is an expression holding two
 * different expressions together, linked by the logical sign "exclusive nor". the class supports
 * all the methods from the expression interface, such as assign and evaluation,
 * and more methods relevant only to binary expressions, some of the methods are
 * written in the abstract class BinaryExpression, and this class inherits them.
 */
public class Xnor extends BinaryExpression {

    /**
     * class constructor.
     *
     * @param e1 the first expression.
     * @param e2 the second expression.
     */
    public Xnor(Expression e1, Expression e2) {
        //call father's constructor with the logical sign for further to string conversion.
        super(e1, e2, " # ");
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
        //in case all is valid, recursively call evaluation on both expressions with logical "exclusive nor".
        return ((super.getExp1().evaluate(assignment) && super.getExp2().evaluate(assignment))
                || (!super.getExp1().evaluate(assignment) && !super.getExp2().evaluate(assignment)));
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
        Xor delegator = new Xor(super.getExp1(), super.getExp2());
        return !delegator.evaluate();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        //recursively call 'assign' on both sides because assigning is only allowed on basic var expressions.
        return new Xnor(super.getExp1().assign(var, expression), super.getExp2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        //using delegation: nor does nand twice as in or.
        Or delegator = new Or(super.getExp1(), super.getExp2());
        //return new nand and recursively nandify both expressions within.
        return new Nand(delegator.nandify(), new Nand(super.getExp1().nandify(), super.getExp2().nandify()));
    }

    @Override
    public Expression norify() {
        //recursively norify the expressions within using the translation to a nore expression.
        return new Nor(new Nor(super.getExp1().norify(), new Nor(super.getExp1().norify(), super.getExp2().norify())),
                new Nor(super.getExp2().norify(), new Nor(super.getExp1().norify(), super.getExp2().norify())));
    }

    @Override
    public Expression simplify() {
        //first, simplify the expressions held within the Xnor expression.
        Expression simplified1 = super.getExp1().simplify();
        Expression simplified2 = super.getExp2().simplify();
        //if they are equal, simply return true.
        if (simplified1.equals(simplified2)) {
            return new Val(true);
            /*
            otherwise, if there are no variables within the expression, note that the simplified
            expression can only hold true or false in each side. so, using the basic logic:
             */
        } else if (this.getVariables().isEmpty()) {
            //if both of them are true, return true.
            if ((simplified1.equals(new Val(true))) && (simplified2.equals(new Val(true)))) {
                return new Val(true);
                //in any other case, only return false.
            } else {
                return new Val(false);
            }
            //if no simplification is possible, return Xnor with the simplified inner expressions.
        } else {
            return new Xnor(simplified1, simplified2);
        }
    }
}
