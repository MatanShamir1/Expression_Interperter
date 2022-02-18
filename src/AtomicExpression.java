/**
 * class AtomicExpression:
 * this is an abstract class holding several mutual implementations for
 * classes val and var. both of them are atomic expressions, meaning they
 * are the leaves of the recursive tree, and thus may hold the same implementations
 * for some methods. the class extends BaseExpression and thus implements
 * a part of "Expression"'s methods, giving its children the other ones to implement.
 */
public abstract class AtomicExpression extends BaseExpression {
    //the mutual field between val and var is string name, so its held here.
    private String name;

    /**
     * class constructor.
     * @param name the name of the atomic expression.
     */
    public AtomicExpression(String name) {
        //if the name is equal to true or false, its a val object, change its name.
        if (name.equals("true")) {
            this.name = "T";
        } else if (name.equals("false")) {
            this.name = "F";
            //otherwise, its a var, keep its name.
        } else {
            this.name = name;
        }
    }

    /**
     * nothing can be nandified on an atomic expression: return itself.
     * @return this object.
     */
    public Expression nandify() {
        return this;
    }

    /**
     * nothing can be norified on an atomic expression: return itself.
     * @return this object.
     */
    public Expression norify() {
        return this;
    }

    /**
     * nothing can be simplified on an atomic expression: return itself.
     * @return this object.
     */
    public Expression simplify() {
        return this;
    }

    /**
     * class getter.
     * @return the name of the atomic expression.
     */
    public String getName() {
        return this.name;
    }

    /**
     * implementation of the "toString" method: convert to string.
     * @return the name of the atomic expression held in a field.
     */
    public String toString() {
        return this.getName();
    }
}
