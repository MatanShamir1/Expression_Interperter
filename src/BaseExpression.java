import java.util.ArrayList;
import java.util.List;

/**
 * class BaseExpression is an abstract class holding partial method implementations
 * for binary expressions. it implements the "Expression" interface and thus must
 * implement some of its methods, but its abstract so it can leave its children other
 * methods to implement.
 */
public abstract class BaseExpression implements Expression {
    /**
     * this method removes duplicates from a list of strings.
     * @param varList the list of vars found in the expression.
     * @return a new list after the duplicates had been removed.
     */
    public List<String> removeDuplicates(List<String> varList) {
        //create a new list to be returned:
        List<String> newVarList = new ArrayList<>();
        //iterate through the old list and only copy values that dont already exist in the new.
        for (String var : varList) {
            if (!newVarList.contains(var)) {
                newVarList.add(var);
            }
        }
        //return the new list.
        return newVarList;
    }

    /**
     * an implementation of the "equals" method: only on expressions.
     * @param expression the expression to be checked equal to.
     * @return true for equal expressions, false otherwise.
     */
    public boolean equals(Expression expression) {
        return this.toString().equals(expression.toString());
    }
}
