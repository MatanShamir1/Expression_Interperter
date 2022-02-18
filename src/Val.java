import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class Val: the class extends AtomicExpression. it is an atomic expression that
 * cannot be simplified, norified or nandified. it has a value and a string representation
 * and no variables in it. it can only have true or false values.
 */
public class Val extends AtomicExpression {
    //the boolean value placed in a field.
    private boolean value;

    /**
     * class constructor.
     * @param val the value to be placed within the field.
     */
    public Val(boolean val) {
        //call super with the string of the value, for the name field held within the father.
        super("" + val);
        //place the given value within this class's field.
        this.value = val;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        //no evaluation is possible beyond returning this very value.
        return this.value;
    }

    @Override
    public Boolean evaluate() throws Exception {
        //no evaluation is possible beyond returning this very value.
        return this.value;
    }

    @Override
    public List<String> getVariables() {
        //if "get variables" reaches here, no vars exist in this expression, simply return an empty list.
        return new ArrayList<>();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        //no assigning is possible on values but only on vars, return the expression unchanged.
        return this;
    }
}
