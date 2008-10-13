package org.mvel2.ast;

import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.compiler.ExecutableStatement;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.util.CompilerTools;
import static org.mvel2.util.ParseTools.subCompileExpression;

public class Invert extends ASTNode {
    private ExecutableStatement stmt;

    public Invert(char[] name, int fields) {
        this.name = name;

        if ((fields & COMPILE_IMMEDIATE) != 0) {
            CompilerTools.expectType(this.stmt = (ExecutableStatement) subCompileExpression(name), Integer.class, true);
        }
    }

    public Object getReducedValueAccelerated(Object ctx, Object thisValue, VariableResolverFactory factory) {
        return ~((Integer) stmt.getValue(ctx, thisValue, factory));
    }

    public Object getReducedValue(Object ctx, Object thisValue, VariableResolverFactory factory) {
        Object o = MVEL.eval(name, ctx, factory);
        if (o instanceof Integer) {
            return ~((Integer) o);
        }
        else {
            throw new CompileException("was expecting type: Integer; but found type: " + (o == null ? "null" : o.getClass().getName()));
        }
    }
}