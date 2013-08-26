package Time_Log_Inserter.src.profiler;

import java.util.HashSet;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyMethodVisitor extends MethodVisitor
{
    private int currentLineNumber;

    public MyMethodVisitor( int i )
    {
        super( i );
    }

    public MyMethodVisitor( int i, MethodVisitor mv )
    {
        super( i, mv );
    }

    public void visitInsn( int opcode )
    {
        if ( ( opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN ) || opcode == Opcodes.ATHROW )
        {
            mv.visitMethodInsn( Opcodes.INVOKESTATIC, CustomClassFileTransformer.currentClass, "timer", "()V" );
        }
        
        mv.visitInsn( opcode );
    }

}
