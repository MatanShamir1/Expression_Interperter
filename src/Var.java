import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class Var: the class extends AtomicExpression. it is an atomic expression that
 * cannot be simplified, norified or nandified. it has a string representation
 * and can be evaluated using a map of var valuations, created in the main function.
 */
public class Var extends AtomicExpression {
    /**
     * class constructor.
     *
     * @param name the name of the variable.
     */
    public Var(String name) {
        //call super to place the name value within the father field.
        super(name);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        //if the map has no value for this variable, no evaluatio is possible, throw exception.
        if (!assignment.containsKey(super.getName())) {
            throw new Exception("no value for" + super.getName());
        } else {
            //otherwise, the value exists, return the evaluation using the map.
            return assignment.get(super.getName());
        }
    }

    @Override
    public Boolean evaluate() throws Exception {
        /*
        if someone called evaluate without a map, no evaluation is possible because a var
        can only be evaluated using map of values, so throw an exception nonetheless.
         */
        throw new Exception("no map");
    }

    @Override
    public List<String> getVariables() {
        //create a new list and add this value to it, then return it.
        List<String> newList = new ArrayList<>();
        newList.add(super.getName());
        return newList;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        //if this value is the one to be assigned in, return the expression assigned.
        if (super.getName().equals(var)) {
            return expression;
            //otherwise, if this is not the variable to assign within, return itself.
        } else {
            return this;
        }
    }
}


