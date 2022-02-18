import java.util.Map;
import java.util.TreeMap;

/**
 * this is the minimal required test by the assignment: adding a map and an expression,
 * and printing all of the different versions of it.
 */
public class ExpressionsTest {

    /**
     * the main method.
     * @param args no arguments should be either used or given.
     */
    public static void main(String[] args) {
        Expression expression = new Xor(new And(new Var("x"), new Var("y")), new Var("z"));
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", true);
        System.out.println(expression.toString());
        try {
            System.out.println(expression.evaluate(assignment));
            System.out.println(expression.nandify().toString());
            System.out.println(expression.norify().toString());
            System.out.println(expression.simplify().toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
