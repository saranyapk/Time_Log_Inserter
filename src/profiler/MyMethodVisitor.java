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

    public void visitCode()
    {
        mv.visitFieldInsn( Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;" );
        mv.visitLdcInsn( "Started at:" );
        mv.visitMethodInsn( Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V" );
        mv.visitMethodInsn( Opcodes.INVOKESTATIC, CustomClassFileTransformer.currentClass, "timer", "()V" );
        mv.visitCode();
    }

    public void visitInsn( int opcode )
    {
        if ( ( opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN ) || opcode == Opcodes.ATHROW )
        {
            mv.visitFieldInsn( Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;" );
            mv.visitLdcInsn( "Ended at:" );
            mv.visitMethodInsn( Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V" );

            mv.visitMethodInsn( Opcodes.INVOKESTATIC, CustomClassFileTransformer.currentClass, "timer", "()V" );
        }

        mv.visitInsn( opcode );
    }

}
